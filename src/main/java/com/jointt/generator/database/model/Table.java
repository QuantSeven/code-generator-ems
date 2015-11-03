package com.jointt.generator.database.model;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jointt.generator.database.DbUtils;
import com.jointt.generator.database.Model;
import com.jointt.generator.template.model.TemplateModel;
import com.jointt.generator.utils.StringUtil;

/**
 * 每一张数据库表
 * 
 * @author yongan.quan
 */
public class Table implements Model {

	private static final long serialVersionUID = -611962623912150947L;
	public static final String PKTABLE_NAME = "PKTABLE_NAME";
	public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
	public static final String FKTABLE_NAME = "FKTABLE_NAME";
	public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public static final String KEY_SEQ = "KEY_SEQ";

	private String tableName; // 表名
	private String className; // 对于实体类
	private String tableRemark;
	private List<Column> columns = new ArrayList<Column>();
	public Map<String, Column> columnMap = new HashMap<String, Column>();
	private List<Column> primaryKeyColumns = new ArrayList<Column>(0);
	private String[] relationKeys;
	private String relationTableDataUrl;
	private String[] middleTableUrlSetting;
	private String[] treeSetting;
	private List<Table> childrens = new ArrayList<Table>(0);
	private Table parent;
	private Table relationTable;
	private String ownerSynonymName;
	private String catalog = DbUtils.getInstance().catalog;
	private String schema = DbUtils.getInstance().schema;
	private ForeignKeys exportedKeys;
	private ForeignKeys importedKeys;

	public boolean isSubTable = false;

	private TemplateModel templateModel;

	public static String getFkTableName() {
		return FKTABLE_NAME;
	}

	public static String getKeySeq() {
		return KEY_SEQ;
	}

	public String getTableRemark() {
		if (tableRemark == null) {
			tableRemark = "";
		}
		return tableRemark;
	}

	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}

	public boolean isSingleId() {
		int pkCount = 0;
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
			if (c.isPk()) {
				pkCount++;
			}
		}
		return pkCount > 1 ? false : true;
	}

	public boolean isCompositeId() {
		return !isSingleId();
	}

	public List<Column> getCompositeIdColumns() {
		List<Column> results = new ArrayList<Column>();
		List<Column> columns = getColumns();
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
			if (c.isPk())
				results.add(c);
		}
		return results;
	}

	public Column getIdColumn() {
		List<Column> columns = getColumns();
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
			if (c.isPk())
				return c;
		}
		return null;
	}

	// 获取主键
	public void initImportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {

		ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, this.tableName);

		while (fkeys.next()) {
			String pktable = fkeys.getString(PKTABLE_NAME);
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
		}
		fkeys.close();
	}

	public void initExportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {

		ResultSet fkeys = dbmd.getExportedKeys(catalog, schema, this.tableName);

		while (fkeys.next()) {
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fktable = fkeys.getString(FKTABLE_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			getExportedKeys().addForeignKey(fktable, fkcol, pkcol, iseq);
		}
		fkeys.close();
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	// get set方法

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<Column> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}

	public List<Table> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Table> childrens) {
		this.childrens = childrens;
	}

	public String getOwnerSynonymName() {
		return ownerSynonymName;
	}

	public void setOwnerSynonymName(String ownerSynonymName) {
		this.ownerSynonymName = ownerSynonymName;
	}

	public ForeignKeys getExportedKeys() {
		if (exportedKeys == null) {
			exportedKeys = new ForeignKeys(this);
		}
		return exportedKeys;
	}

	public void setExportedKeys(ForeignKeys exportedKeys) {
		this.exportedKeys = exportedKeys;
	}

	public ForeignKeys getImportedKeys() {
		if (importedKeys == null) {
			importedKeys = new ForeignKeys(this);
		}
		return importedKeys;
	}

	public void setImportedKeys(ForeignKeys importedKeys) {
		this.importedKeys = importedKeys;
	}

	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(TemplateModel templateModel) {
		this.templateModel = templateModel;
	}

	public String[] getRelationKeys() {
		return relationKeys;
	}

	public void setRelationKeys(String[] relationKeys) {
		this.relationKeys = relationKeys;
	}

	public Column getParentRelationColumn() throws Exception {
		Column parentRelationColumn = null;
		if (getRelationKeys() != null) {
			for (Column column : getParent().getColumns()) {
				if (column.getSqlName().equals(getRelationKeys()[0])) {
					parentRelationColumn = column;
					break;
				}
			}
		}
		if (parentRelationColumn == null) {
			throw new Exception("设置的主表的关联外键不存在");
		}
		return parentRelationColumn;
	}

	public Column getRelationColumn() throws Exception {
		Column relationColumn = null;
		if (getRelationKeys() != null) {
			for (Column column : columns) {
				if (column.getSqlName().equals(getRelationKeys()[1])) {
					relationColumn = column;
					break;
				}
			}
		}
		if (relationColumn == null) {
			throw new Exception("关联的外键不存在");
		}
		return relationColumn;
	}

	/*-------------------树形结构设置 start--------------------*/
	public Column getParentColumn() throws Exception {
		Column parentColumn = null;
		if (getTreeSetting() != null) {
			parentColumn = getColumnMap().get(getTreeSetting()[1]);
		}
		if (parentColumn == null) {
			throw new Exception("树结构父级字段不存在");
		}
		return parentColumn;
	}

	public Column getIdKeyColumn() throws Exception {
		Column idKeyColumn = null;
		if (getTreeSetting() != null) {
			idKeyColumn = getColumnMap().get(getTreeSetting()[0]);
		}
		if (idKeyColumn == null) {
			throw new Exception("树结构主字段不存在");
		}
		return idKeyColumn;
	}

	public Column getSearchNodeColumn() throws Exception {
		Column idKeyColumn = null;
		if (getTreeSetting() != null) {
			idKeyColumn = getColumnMap().get(getTreeSetting()[2]);
		}
		if (idKeyColumn == null) {
			throw new Exception("树结构节点字段不存在");
		}
		return idKeyColumn;
	}

	/*-------------------树形结构设置 end--------------------*/
	// 首字母小写
	public String getClassNameLowerCase() {
		return StringUtil.uncapitalize(getClassName());
	}

	// 全部字母小写
	public String getClassNameAllLowerCase() {
		return StringUtil.lowerCase(getClassName());
	}

	public boolean getIsSubTable() {
		return isSubTable;
	}

	public void setSubTable(boolean isSubTable) {
		this.isSubTable = isSubTable;
	}

	public Table getParent() {
		return parent;
	}

	public void setParent(Table parent) {
		this.parent = parent;
	}

	public Map<String, Column> getColumnMap() {
		return columnMap;
	}

	public void addColumn(String key, Column paramColumn) {
		this.columnMap.put(key, paramColumn);
	}

	public String[] getTreeSetting() {
		return treeSetting;
	}

	public void setTreeSetting(String[] treeSetting) {
		this.treeSetting = treeSetting;
	}

	public String getRelationTableDataUrl() {
		return relationTableDataUrl;
	}

	public void setRelationTableDataUrl(String relationTableDataUrl) {
		this.relationTableDataUrl = relationTableDataUrl;
	}

	public String[] getMiddleTableUrlSetting() {
		return middleTableUrlSetting;
	}

	//多对多manytomany
	public String getMiddleTableDataUrl() {
		return getMiddleTableUrlSetting()[0];
	}
	public String getMiddleTableInsertUrl() {
		return getMiddleTableUrlSetting()[1];
	}
	public String getMiddleTableDeleteUrl() {
		return getMiddleTableUrlSetting()[2];
	}
	
	
	public void setMiddleTableUrlSetting(String[] middleTableUrlSetting) {
		this.middleTableUrlSetting = middleTableUrlSetting;
	}

	public Table getRelationTable() {
		return relationTable;
	}

	public void setRelationTable(Table relationTable) {
		this.relationTable = relationTable;
	}
}
