package com.jointt.generator.database.model;

import java.util.List;

import com.jointt.generator.database.DbUtils;
import com.jointt.generator.utils.ListHashtable;

/**
 * 外键实体类
 * 
 * @author yongan.quan
 */
public class ForeignKey {

	protected String relationShip = null;
	protected String firstRelation = null;
	protected String secondRelation = null;
	protected Table parentTable;
	protected String tableName;
	protected ListHashtable columns;
	protected ListHashtable parentColumns;

	public ForeignKey(Table aTable, String tblName) {
		super();
		parentTable = aTable;
		tableName = tblName;
		columns = new ListHashtable();
		parentColumns = new ListHashtable();
	}

	public String getTableName() {
		return tableName;
	}

	public String getParentTableName() {
		return parentTable.getTableName();
	}

	public void addColumn(String col, String parentCol, Integer seq) {
		columns.put(seq, col);
		parentColumns.put(seq, parentCol);
	}

	public String getColumn(String parentCol) {
		Object key = parentColumns.getKeyForValue(parentCol);
		String col = (String) columns.get(key);
		return col;
	}

	public ListHashtable getColumns() {
		return columns;
	}

	/**
	 * 
	 */
	private void initRelationship() {
		firstRelation = "";
		secondRelation = "";
		Table foreignTable = null;
		try {
			foreignTable = (Table) DbUtils.getInstance().getTable(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Column> parentPrimaryKeys = parentTable.getPrimaryKeyColumns();
		List<Column> foreignPrimaryKeys = foreignTable.getPrimaryKeyColumns();

		if (hasAllPrimaryKeys(parentPrimaryKeys, parentColumns))
			firstRelation = "one";
		else
			firstRelation = "many";

		if (hasAllPrimaryKeys(foreignPrimaryKeys, columns))
			secondRelation = "one";
		else
			secondRelation = "many";

		relationShip = firstRelation + "-to-" + secondRelation;

	}

	private boolean hasAllPrimaryKeys(List<Column> pkeys, ListHashtable cols) {
		boolean hasAll = true;
		int numKeys = pkeys.size();
		if (numKeys != cols.size())
			return false;

		for (int i = 0; i < numKeys; i++) {
			Column col = (Column) pkeys.get(i);
			String colname = col.getColumnName();
			if (!cols.contains(colname))
				return false;
		}

		return hasAll;
	}

	public boolean isParentColumnsFromPrimaryKey() {
		boolean isFrom = true;
		int numKeys = getParentColumns().size();
		for (int i = 0; i < numKeys; i++) {
			String pcol = (String) getParentColumns().getOrderedValue(i);
			if (!primaryKeyHasColumn(pcol)) {
				isFrom = false;
				break;
			}
		}
		return isFrom;
	}

	private boolean primaryKeyHasColumn(String aColumn) {
		boolean isFound = false;
		int numKeys = parentTable.getPrimaryKeyColumns().size();
		for (int i = 0; i < numKeys; i++) {
			Column sqlCol = (Column) parentTable.getPrimaryKeyColumns().get(i);
			String colname = sqlCol.getColumnName();
			if (colname.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}

	public boolean getHasImportedKeyColumn(String aColumn) {
		boolean isFound = false;
		List<Object> cols = getColumns().getOrderedValues();
		int numCols = cols.size();
		for (int i = 0; i < numCols; i++) {
			String col = (String) cols.get(i);
			if (col.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}

	public String getFirstRelation() {
		if (firstRelation == null)
			initRelationship();
		return firstRelation;
	}

	public Table getSqlTable() {
		Table table = null;
		try {
			table = (Table) DbUtils.getInstance().getTable(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	public Table getParentTable() {
		return parentTable;
	}

	public String getRelationShip() {
		if (relationShip == null)
			initRelationship();
		return relationShip;
	}

	public String getSecondRelation() {
		if (secondRelation == null)
			initRelationship();
		return secondRelation;
	}

	public ListHashtable getParentColumns() {
		return parentColumns;
	}

	public boolean getHasImportedKeyParentColumn(String aColumn) {

		boolean isFound = false;
		List<Object> cols = getParentColumns().getOrderedValues();
		int numCols = cols.size();
		for (int i = 0; i < numCols; i++) {
			String col = (String) cols.get(i);
			if (col.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}
}
