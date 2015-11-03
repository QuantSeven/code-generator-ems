package com.jointt.generator.core.model;

import java.util.ArrayList;
import java.util.List;

import com.jointt.generator.constant.TemplateType;
import com.jointt.generator.utils.StringUtil;

/**
 * 生成表格的实体转换类
 * 
 * @author yongan.quan
 */
public class TableVO implements ModelVO {

	private static final long serialVersionUID = -4631397551699720526L;
	private String tableName; // 表名
	private String className; // 类名 （首字母大写）
	private String packageName; // 包名（小写）
	// 页面显示类型(弹出框模式：TemplateType.MODEL ； 内嵌页模式：TemplateType.INNER)
	private String templateType;

	private String[] treeSetting; // 树形结构设置

	private List<SubTableVO> childrens = new ArrayList<SubTableVO>(0); // 字表

	// manytomany
	private String relationTableName; // 关联的表名
	private String relationTableDataUrl; // 多对多，关联表的数据URL地址
	private String[] middleTableUrlSetting;  //中间表的列表，增加，删除

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
			templateType = TemplateType.MODEL; // 默认是弹出框模式
		}
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public List<SubTableVO> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SubTableVO> childrens) {
		this.childrens = childrens;
	}

	public void addSubTable(SubTableVO subTableVo) {
		this.childrens.add(subTableVo);
	}

	public String[] getTreeSetting() {
		return treeSetting;
	}

	/**
	 * 
	 * @param idKey
	 *            zTree的idKey
	 * @param parentId
	 *            zTree的pIdKey
	 * @param searchNodeName
	 *            查询的节点名称
	 */
	public void setTreeSetting(String idKey, String parentId, String searchNodeName) {
		this.treeSetting = new String[] { idKey, parentId, searchNodeName };
	}

	public String getRelationTableDataUrl() {
		return relationTableDataUrl;
	}

	public void setRelationTableDataUrl(String relationTableDataUrl) {
		this.relationTableDataUrl = relationTableDataUrl;
	}


	public String getRelationTableName() {
		return relationTableName;
	}

	public void setRelationTableName(String relationTableName) {
		this.relationTableName = relationTableName;
	}


	public String[] getMiddleTableUrlSetting() {
		return middleTableUrlSetting;
	}

	public void setMiddleTableUrlSetting(String[] middleTableUrlSetting) {
		this.middleTableUrlSetting = middleTableUrlSetting;
	}

	/**
	 * 多对多中间表的URL设置
	 * @param dataUrl 数据显示页面的URL地址
	 * @param insertUrl 添加记录URL地址
	 * @param deleteUrl 删除记录URL地址
	 */
	public void setMiddleTableUrl(String dataUrl, String insertUrl, String deleteUrl) {
		this.middleTableUrlSetting = new String[]{dataUrl,insertUrl,deleteUrl};
	}
}
