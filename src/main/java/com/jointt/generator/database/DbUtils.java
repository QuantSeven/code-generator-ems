package com.jointt.generator.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jointt.generator.database.model.Column;
import com.jointt.generator.database.model.Table;
import com.jointt.generator.utils.PropertiesUtil;
import com.jointt.generator.utils.StringUtil;

/**
 * 数据库操作服务类
 * 
 * @author yongan.quan
 */
public class DbUtils {

	private static final Log log = LogFactory.getLog(DbUtils.class);

	public String catalog = null;
	public String schema = "";
	private Connection connection;

	private static DbUtils instance;

	private DbUtils() {
	}

	static {
		try {
			Class.forName(PropertiesUtil.DIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DbUtils getInstance() {
		if (instance == null) {
			instance = new DbUtils();
		}
		return instance;
	}

	private Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(PropertiesUtil.URL, PropertiesUtil.USERNAME, PropertiesUtil.PASSWORD);
		}
		catalog = connection.getCatalog();
		return connection;
	}

	public List<Table> getAllTables() throws Exception {
		Connection conn = getConnection();
		return getAllTables(conn);
	}

	public Table getTable(String tableName) throws Exception {
		Connection conn = getConnection();
		DatabaseMetaData dbMetaData = conn.getMetaData();
		ResultSet rs = dbMetaData.getTables(catalog, schema, tableName, new String[] { "TABLE" });
		while (rs.next()) {
			Table table = createTable(conn, rs);
			conn.close();
			return table;
		}
		throw new RuntimeException("找不到表:" + tableName);
	}

	private Table createTable(Connection conn, ResultSet rs) throws SQLException {
		String tableName = rs.getString("TABLE_NAME");
		String tableType = rs.getString("TABLE_TYPE");
		String remarks = rs.getString("REMARKS");
		if (StringUtil.isNullOrEmpty(remarks)) {
			String sql = "select TABLE_COMMENT from information_schema.tables where table_name='" + tableName + "'";// add
			// MySQL数据库获取表的注释,ORACLE不需要
			try {
				PreparedStatement stm = conn.prepareStatement(sql);
				ResultSet res = stm.executeQuery();
				while (res.next()) {
					remarks = res.getString("TABLE_COMMENT");
					if (!"".equals(remarks))
						break;
				}
			} catch (Exception e) {
			}
		}
		Table table = new Table();
		table.setTableName(tableName);
		table.setTableRemark(remarks);
		if ("SYNONYM".equals(tableType) && isOracleDataBase()) {
			table.setOwnerSynonymName(getSynonymOwner(tableName));
		}

		table.initExportedKeys(conn.getMetaData());
		table.initImportedKeys(conn.getMetaData());

		retriveTableColumns(table);

		return table;
	}

	public List<Table> getAllTables(Connection conn) throws SQLException {
		DatabaseMetaData dbMetaData = conn.getMetaData();
		ResultSet rs = dbMetaData.getTables(catalog, schema, null, new String[] { "TABLE" });
		List<Table> tables = new ArrayList<Table>();
		while (rs.next()) {
			Table table = createTable(conn, rs);
			tables.add(table);
		}
		return tables;
	}

	// 是否是Oracle数据库
	private boolean isOracleDataBase() {
		boolean ret = false;
		try {
			ret = (getMetaData().getDatabaseProductName().toLowerCase().indexOf("oracle") != -1);
		} catch (Exception ignore) {
		}
		return ret;
	}

	// 当前Oracle数据库的同义词
	private String getSynonymOwner(String synonymName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ret = null;
		try {
			ps = getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
			ps.setString(1, synonymName);
			ps.setString(2, schema);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			} else {
				String databaseStructure = getDatabaseStructureInfo();
				throw new RuntimeException("Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
			}
		} catch (SQLException e) {
			String databaseStructure = getDatabaseStructureInfo();
			log.error(e.getMessage(), e);
			throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
		}
		return ret;
	}

	// 当前Oracle数据库的结构信息
	private String getDatabaseStructureInfo() {
		ResultSet schemaRs = null;
		ResultSet catalogRs = null;
		String nl = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer(nl);
		sb.append("Configured schema:").append(schema).append(nl);
		sb.append("Configured catalog:").append(catalog).append(nl);

		try {
			schemaRs = getMetaData().getSchemas();
			sb.append("Available schemas:").append(nl);
			while (schemaRs.next()) {
				sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
			}
		} catch (SQLException e2) {
			log.warn("Couldn't get schemas", e2);
			sb.append("  ?? Couldn't get schemas ??").append(nl);
		} finally {
			try {
				schemaRs.close();
			} catch (Exception ignore) {
			}
		}

		try {
			catalogRs = getMetaData().getCatalogs();
			sb.append("Available catalogs:").append(nl);
			while (catalogRs.next()) {
				sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
			}
		} catch (SQLException e2) {
			log.warn("Couldn't get catalogs", e2);
			sb.append("  ?? Couldn't get catalogs ??").append(nl);
		} finally {
			try {
				catalogRs.close();
			} catch (Exception ignore) {
			}
		}
		return sb.toString();
	}

	private DatabaseMetaData getMetaData() throws SQLException {
		return getConnection().getMetaData();
	}

	@SuppressWarnings("unchecked")
	private void retriveTableColumns(Table table) throws SQLException {
		log.debug("-------设置列：(" + table.getTableName() + ")");

		List<String> primaryKeys = getTablePrimaryKeys(table);

		List<String> indices = new LinkedList<String>();

		Map<Object, Object> uniqueIndices = new HashMap<Object, Object>();

		Map<Object, Object> uniqueColumns = new HashMap<Object, Object>();
		ResultSet indexRs = null;
		try {

			if (table.getOwnerSynonymName() != null) {
				indexRs = getMetaData().getIndexInfo(catalog, table.getOwnerSynonymName(), table.getTableName(), false, true);
			} else {
				indexRs = getMetaData().getIndexInfo(catalog, schema, table.getTableName(), false, true);
			}
			while (indexRs.next()) {
				String columnName = indexRs.getString("COLUMN_NAME");
				if (columnName != null) {
					log.debug("index:" + columnName);
					indices.add(columnName);
				}

				String indexName = indexRs.getString("INDEX_NAME");
				boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");

				if (!nonUnique && columnName != null && indexName != null) {
					List<String> l = (List<String>) uniqueColumns.get(indexName);
					if (l == null) {
						l = new ArrayList<String>();
						uniqueColumns.put(indexName, l);
					}
					l.add(columnName);
					uniqueIndices.put(columnName, indexName);
					log.debug("unique:" + columnName + " (" + indexName + ")");
				}
			}
		} catch (Throwable t) {
		} finally {
			if (indexRs != null) {
				indexRs.close();
			}
		}

		List<Column> columns = getTableColumns(table, primaryKeys, indices, uniqueIndices, uniqueColumns);
		table.setColumns(columns); // 设置表的列

		if (primaryKeys.size() == 0) {
			log.warn("警告:在表中没有找到主键," + table.getTableName());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Column> getTableColumns(Table table, List<String> primaryKeys, List<String> indices, Map<Object, Object> uniqueIndices, Map<Object, Object> uniqueColumns) throws SQLException {

		List<Column> pkList = new ArrayList<Column>();
		List<Column> columns = new LinkedList<Column>();
		ResultSet columnRs = getColumnsResultSet(table);

		while (columnRs.next()) {
			int sqlType = columnRs.getInt("DATA_TYPE");
			String sqlTypeName = columnRs.getString("TYPE_NAME");
			if ("INT".equals(sqlTypeName)) {
				sqlTypeName = "INTEGER";
			}
			if ("TEXT".equals(sqlTypeName)) {
				sqlTypeName = "VARCHAR";
			}
			if ("DATETIME".equals(sqlTypeName)) {
				sqlTypeName = "TIMESTAMP";
			}
			if ("ENUM".equals(sqlTypeName)) {
				sqlTypeName = "VARCHAR";
			}
			String columnName = columnRs.getString("COLUMN_NAME");
			String columnDefaultValue = columnRs.getString("COLUMN_DEF");
			boolean isNullable = (DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE"));
			int size = columnRs.getInt("COLUMN_SIZE");
			int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

			String comment = columnRs.getString("REMARKS");// 列字段注释

			boolean isPk = primaryKeys.contains(columnName);
			boolean ifFk = table.getImportedKeys().getHasImportedKeyColumn(columnName);
			boolean isIndexed = indices.contains(columnName);
			String uniqueIndex = (String) uniqueIndices.get(columnName);
			List<String> columnsInUniqueIndex = null;
			if (uniqueIndex != null) {
				columnsInUniqueIndex = (List<String>) uniqueColumns.get(uniqueIndex);
			}

			boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
			if (isUnique) {
				log.debug("unique column:" + columnName);
			}
			Column column = new Column(table, sqlType, sqlTypeName, columnName, size, decimalDigits, isPk, ifFk, isNullable, isIndexed, isUnique, columnDefaultValue, comment);
			table.addColumn(columnName, column);
			columns.add(column);
			if (isPk) {
				pkList.add(column);
			}
		}
		table.setPrimaryKeyColumns(pkList);
		columnRs.close();
		return columns;
	}

	// 获取列名的结果集
	private ResultSet getColumnsResultSet(Table table) throws SQLException {
		ResultSet columnRs = null;
		if (table.getOwnerSynonymName() != null) {
			columnRs = getMetaData().getColumns(catalog, table.getOwnerSynonymName(), table.getTableName(), null);
		} else {
			columnRs = getMetaData().getColumns(catalog, schema, table.getTableName(), null);
		}
		return columnRs;
	}

	// 获取主键列字段名
	private List<String> getTablePrimaryKeys(Table table) throws SQLException {
		List<String> primaryKeys = new LinkedList<String>();
		ResultSet primaryKeyRs = null;
		if (table.getOwnerSynonymName() != null) {
			primaryKeyRs = getMetaData().getPrimaryKeys(catalog, table.getOwnerSynonymName(), table.getTableName());
		} else {
			primaryKeyRs = getMetaData().getPrimaryKeys(catalog, schema, table.getTableName());
		}
		while (primaryKeyRs.next()) {

			String columnName = primaryKeyRs.getString("COLUMN_NAME");
			log.debug("primary key:" + columnName);
			primaryKeys.add(columnName);

		}
		primaryKeyRs.close();
		return primaryKeys;
	}

}
