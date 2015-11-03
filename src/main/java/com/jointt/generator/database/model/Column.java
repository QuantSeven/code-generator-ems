package com.jointt.generator.database.model;

import com.jointt.generator.utils.DatabaseDataTypesUtils;
import com.jointt.generator.utils.StringUtil;

/**
 * 字段列 实体类
 * 
 * @author yongan.quan
 */
public class Column {
	/**
	 * 表名
	 */
	private final Table table;

	/**
	 * SQl的类型
	 */
	private final int sqlType;

	/**
	 * JDBC的类型
	 */
	private final String sqlTypeName;

	/**
	 * 列名
	 */
	private final String sqlName;

	/**
	 * 是否是主键
	 */
	private boolean isPk;

	/**
	 * 是否是外键
	 */
	private boolean isFk;

	/**
	 * 列长度大小
	 */
	private final int size;

	private final int decimalDigits;

	/**
	 * 是否允许为空
	 */
	private final boolean isNullable;

	/**
	 * 是否索引列
	 */
	private final boolean isIndexed;

	/**
	 * 是否唯一
	 */
	private final boolean isUnique;

	/**
	 * 默认值
	 */
	private final String defaultValue;

	/**
	 * 注释
	 */
	private String comment;

	public Column(Table table, int sqlType, String sqlTypeName, String sqlName, int size, int decimalDigits, boolean isPk, boolean isFk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String comment) {
		this.table = table;
		this.sqlType = sqlType;
		this.sqlName = sqlName;
		this.sqlTypeName = sqlTypeName;
		this.size = size;
		this.decimalDigits = decimalDigits;
		this.isPk = isPk;
		this.isFk = isFk;
		this.isNullable = isNullable;
		this.isIndexed = isIndexed;
		this.isUnique = isUnique;
		this.defaultValue = defaultValue;
		this.comment = comment;
	}

	public int getSqlType() {
		return sqlType;
	}

	public Table getTable() {
		return table;
	}

	public int getSize() {
		return size;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public String getSqlTypeName() {
		return sqlTypeName;
	}

	public String getSqlName() {
		return sqlName;
	}

	public boolean isPk() {
		return isPk;
	}

	public boolean isFk() {
		return isFk;
	}

	public final boolean isNullable() {
		return isNullable;
	}

	public final boolean isIndexed() {
		return isIndexed;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public final String getDefaultValue() {
		return defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public int hashCode() {
		return (getTable().getTableName() + "#" + getSqlName()).hashCode();
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public String toString() {
		return getSqlName();
	}

	protected final String prefsPrefix() {
		return "tables/" + getTable().getTableName() + "/columns/" + getSqlName();
	}

	void setFk(boolean flag) {
		isFk = flag;
	}

	// 此处获取Java类的列名如 USER_ID 转换为 UserId
	public String getColumnName() {
		return StringUtil.makeAllWordFirstLetterUpperCase(getSqlName());
	}

	// 此处获取Java类的列名如 USER_ID 转换为 userId
	public String getColumnNameLowerCase() {
		return StringUtil.uncapitalize(getColumnName());
	}

	// 全部字母小写
	public String getColumnNameAllLowerCase() {
		return StringUtil.lowerCase(getColumnName());
	}

	public boolean getIsNotIdOrVersionField() {
		return !isPk();
	}

	public String getValidateString() {
		String result = getNoRequiredValidateString();
		if (!isNullable()) {
			result = "required " + result;
		}
		return result;
	}

	public String getNoRequiredValidateString() {
		String result = "";
		if (getSqlName().indexOf("mail") >= 0) {
			result += "validate-email ";
		}
		if (DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits())) {
			result += "validate-number ";
		}
		if (DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits())) {
			result += "validate-integer ";
		}
		return result;
	}

	public boolean getIsDateTimeColumn() {
		return DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits());
	}

	public boolean isHtmlHidden() {
		return isPk() && table.isSingleId();
	}

	public String getJavaType() {
		return DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
	}
}
