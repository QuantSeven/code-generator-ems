package com.jointt.generator.template.model;

import java.io.Serializable;

public class TemplateModel implements Serializable {

	private static final long serialVersionUID = 6825627833324412857L;
	private String tableName;// 表名称
	private String className;
	private String basePackage; // 父集包名
	private String subPackage; // 子包
	private String daoPackage; // dao
	private String xmlPackage; // mapper文件包
	private String servicePackage;
	private String serviceImplPackage;
	private String modelPackage;
	private String controllerPackage;
	private String jspPath;
	private String javaScriptPath;
	private String templateType;
	private String subPackagePath;
	

	public TemplateModel() { 
	}

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

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getSubPackage() {
		return subPackage;
	}

	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getXmlPackage() {
		return xmlPackage;
	}

	public void setXmlPackage(String xmlPackage) {
		this.xmlPackage = xmlPackage;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public String getJavaScriptPath() {
		return javaScriptPath;
	}

	public void setJavaScriptPath(String javaScriptPath) {
		this.javaScriptPath = javaScriptPath;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getSubPackagePath() {
		return subPackagePath;
	}

	public void setSubPackagePath(String subPackagePath) {
		this.subPackagePath = subPackagePath;
	}

}
