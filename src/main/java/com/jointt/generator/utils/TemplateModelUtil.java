package com.jointt.generator.utils;

import java.util.HashMap;
import java.util.Map;

import com.jointt.generator.core.model.ModelVO;
import com.jointt.generator.database.model.Table;
import com.jointt.generator.template.model.TemplateModel;

public class TemplateModelUtil {

	public static TemplateModel getTemplateModel(ModelVO model) {
		TemplateModel templateModel = new TemplateModel();
		PropertiesUtil.refreshData();
		String subPackageName = model.getPackageName();
		if (!StringUtil.isNullOrEmpty(subPackageName)) {
			PropertiesUtil.DAO_PACKAGE = PropertiesUtil.DAO_PACKAGE + "." + subPackageName;
			PropertiesUtil.XML_PACKAGE = PropertiesUtil.XML_PACKAGE + "." + subPackageName;
			PropertiesUtil.SERVICE_PACKAGE = PropertiesUtil.SERVICE_PACKAGE + "." + subPackageName;
			PropertiesUtil.SERVICEIMPL_PACKAGE = PropertiesUtil.SERVICEIMPL_PACKAGE + "." + subPackageName;
			PropertiesUtil.CONTROLLER_PACKAGE = PropertiesUtil.CONTROLLER_PACKAGE + "." + subPackageName;
			PropertiesUtil.MODEL_PACKAGE = PropertiesUtil.MODEL_PACKAGE + "." + subPackageName;
			PropertiesUtil.JSP_PATH = PropertiesUtil.JSP_PATH + "/" + subPackageName;
			PropertiesUtil.JAVASCRIPT_PATH = PropertiesUtil.JAVASCRIPT_PATH + "/" + subPackageName;
		}
		templateModel.setClassName(model.getClassName());
		templateModel.setTemplateType(model.getTemplateType());

		templateModel.setBasePackage(PropertiesUtil.BASE_PACKAGE);
		templateModel.setSubPackage(subPackageName);
		templateModel.setDaoPackage(PropertiesUtil.DAO_PACKAGE);
		templateModel.setXmlPackage(PropertiesUtil.XML_PACKAGE);
		templateModel.setServicePackage(PropertiesUtil.SERVICE_PACKAGE);
		templateModel.setServiceImplPackage(PropertiesUtil.SERVICEIMPL_PACKAGE);
		templateModel.setControllerPackage(PropertiesUtil.CONTROLLER_PACKAGE);
		templateModel.setModelPackage(PropertiesUtil.MODEL_PACKAGE);
		templateModel.setJspPath(PropertiesUtil.JSP_PATH);
		templateModel.setJavaScriptPath(PropertiesUtil.JAVASCRIPT_PATH);
		templateModel.setSubPackagePath(StringUtil.replace(subPackageName, ".", "/"));
		return templateModel;
	}

	public static Map<String, Object> getTemplateModelMap(Table table) {
		Map<String, Object> templateModelMap = new HashMap<String, Object>();
		templateModelMap.put("table", table);

		// 还可以设置其他的值
		// ...
		templateModelMap.put("className", table.getClassName());
		templateModelMap.put("classNameLowerCase", table.getClassNameLowerCase());
		return templateModelMap;
	}

	public static String renderFileName(Map<String, Object> paramMap, String fileName) {
		String result = fileName;
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			String strValue = value == null ? "" : value.toString();
			result = StringUtil.replace(result, "${" + key + "}", strValue);
		}
		return result;
	}

}
