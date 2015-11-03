package com.jointt.generator.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jointt.generator.constant.TemplateFiles;
import com.jointt.generator.core.model.SubTableVO;
import com.jointt.generator.core.model.TableVO;
import com.jointt.generator.database.DbUtils;
import com.jointt.generator.database.model.Table;
import com.jointt.generator.utils.TemplateModelUtil;

import freemarker.template.Configuration;

public class GeneratorManyToMany extends Generator {

	public GeneratorManyToMany() {
		TEMPLATE_ROOT_DIR = "/codetemplate/manytomany";
		IS_FORM_JAR_PACKAGE = true;
	}

	/**
	 * 只生成主表和中间表的代码（关联的表代码自己写,只需要传入数据的URL地址即可）
	 */
	public void generatorManyToMany(TableVO tableVO) {
		Configuration config = buildConfiguration();
		try {
			Table table = DbUtils.getInstance().getTable(tableVO.getTableName());
			table.setClassName(tableVO.getClassName());
			table.setRelationTableDataUrl(tableVO.getRelationTableDataUrl());
			table.setMiddleTableUrlSetting(tableVO.getMiddleTableUrlSetting());
			table.setTemplateModel(TemplateModelUtil.getTemplateModel(tableVO));
			List<Table> subTables = new ArrayList<Table>();
			Map<String, Object> subMap = new HashMap<String, Object>();
			for (SubTableVO sub : tableVO.getChildrens()) {
				Table subTable = DbUtils.getInstance().getTable(sub.getTableName());
				subTable.setClassName(sub.getClassName());
				subTable.setRelationKeys(sub.getRelationKeys());
				subTable.setParent(table);
				subTable.setTemplateModel(TemplateModelUtil.getTemplateModel(sub));
				subMap.put(sub.getTableName(), subTable);
				subTables.add(subTable);
			}
			table.setChildrens(subTables);
			Table relationTable = DbUtils.getInstance().getTable(tableVO.getRelationTableName());
			table.setRelationTable(relationTable);
			List<File> templateFiles = getTemplateFiles(TemplateFiles.TEMLPATE_FILE);
			generateFile(config, table, templateFiles); // 生成主表
			// 生成中间表
			templateFiles = getTemplateFiles(TemplateFiles.MIDDLETABLE_TEMLPATE_FILE);
			for (SubTableVO subTableVo : tableVO.getChildrens()) {
				Table t = (Table) subMap.get(subTableVo.getTableName());
				t.setClassName(subTableVo.getClassName());
				t.setRelationKeys(subTableVo.getRelationKeys());
				t.isSubTable = true;
				t.setParent(table);
				t.setTemplateModel(TemplateModelUtil.getTemplateModel(subTableVo));
				generateFile(config, t, templateFiles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同时生成主表，中间表，关联表的代码（关联表设置，只是单表操作，弹窗模式）
	 * 
	 * @param mainTable
	 * @param relationTable
	 */
	public void generatorManyToMany(TableVO mainTable, TableVO relationTable) {
		Configuration config = buildConfiguration();
		try {
			Table table = DbUtils.getInstance().getTable(mainTable.getTableName());
			table.setClassName(mainTable.getClassName());
			table.setTemplateModel(TemplateModelUtil.getTemplateModel(mainTable));
			List<Table> subTables = new ArrayList<Table>();
			Map<String, Object> subMap = new HashMap<String, Object>();
			for (SubTableVO sub : mainTable.getChildrens()) {
				Table subTable = DbUtils.getInstance().getTable(sub.getTableName());
				subTable.setClassName(sub.getClassName());
				subTable.setRelationKeys(sub.getRelationKeys());
				subTable.setParent(table);
				subTable.setTemplateModel(TemplateModelUtil.getTemplateModel(sub));
				subMap.put(sub.getTableName(), subTable);
				subTables.add(subTable);
			}
			table.setChildrens(subTables);
			List<File> templateFiles = getTemplateFiles(TemplateFiles.TEMLPATE_FILE);
			generateFile(config, table, templateFiles); // 生成主表

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
