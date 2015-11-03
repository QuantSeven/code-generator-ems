package com.jointt.generator.constant;

public class TemplateFiles {

	//主表
	public static final String[] TEMLPATE_FILE = {"${className}Dao.ftl","${className}Dao.xml","${className}Service.ftl","${className}ServiceImpl.ftl","${className}Controller.ftl","${className}.ftl","${classNameLowerCase}_list.jsp","${classNameLowerCase}_edit.jsp","${classNameLowerCase}_list.js","zh_CN.properties","en_US.properties","zh_HK.properties","zh_TW.properties"};
	
	//字表
	public static final String[] SUB_TEMLPATE_FILE ={"${className}Dao.ftl","${className}Dao.xml","${className}.ftl","${classNameLowerCase}_edit.jsp","en_US.properties","zh_HK.properties","zh_TW.properties"};

	//主从表
	public static final String[] MASTER_DETAIL_TEMLPATE_FILE ={"${className}Dao.ftl","${className}Dao.xml","${className}.ftl"};
	
	//中间表
	public static final String[] MIDDLETABLE_TEMLPATE_FILE ={"${className}Dao.ftl","${className}Dao.xml","${className}.ftl"};
}
