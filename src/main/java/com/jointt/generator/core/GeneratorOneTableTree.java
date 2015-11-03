package com.jointt.generator.core;

import java.io.File;
import java.util.List;

import com.jointt.generator.constant.TemplateFiles;
import com.jointt.generator.core.model.TableVO;
import com.jointt.generator.database.DbUtils;
import com.jointt.generator.database.model.Table;
import com.jointt.generator.utils.TemplateModelUtil;

import freemarker.template.Configuration;

public class GeneratorOneTableTree extends Generator {

	public GeneratorOneTableTree() {
		TEMPLATE_ROOT_DIR = "/codetemplate/onetabletree";
		IS_FORM_JAR_PACKAGE = true;
	}

	public void generatorOneTableTreeModel(TableVO tableVO) {
		Configuration config = buildConfiguration();
		try {
			Table table = DbUtils.getInstance().getTable(tableVO.getTableName());
			table.setClassName(tableVO.getClassName());
			table.setTreeSetting(tableVO.getTreeSetting());
			table.setTemplateModel(TemplateModelUtil.getTemplateModel(tableVO));
			List<File> templateFiles = getTemplateFiles(TemplateFiles.TEMLPATE_FILE);
			generateFile(config, table, templateFiles); // 生成单表
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
