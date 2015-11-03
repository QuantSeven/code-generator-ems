package com.jointt.generator.core.model;

import com.jointt.generator.constant.TemplateType;
import com.jointt.generator.utils.StringUtil;

/**
 * 从表的实体转换类
 * 
 * @author yongan.quan
 */
public class SubTableVO implements ModelVO {

	private static final long serialVersionUID = -4631397551699720526L;
	private String tableName; // 子表名
	private String className; // 子类名 （首字母大写）
	private String packageName; // 子包名（小写）
	// 页面显示类型(弹出框模式：TemplateType.MODEL ； 内嵌页模式：TemplateType.INNER)
	private String templateType;

	private String[] relationKeys; // 与主表的关联字段

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTemplateType() {
		if (StringUtil.isNullOrEmpty(templateType)) {
			templateType = TemplateType.MODEL;
		}
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String[] getRelationKeys() {
		return relationKeys;
	}

	/**
	 * 与主表的关联字段
	 * 
	 * @param parentTableKey
	 *            主表的字段
	 * @param subTableKey
	 *            从表的字段
	 */
	public void setRelationKeys(String parentTableKey, String subTableKey) {
		this.relationKeys = new String[] { parentTableKey, subTableKey };
	}

}
