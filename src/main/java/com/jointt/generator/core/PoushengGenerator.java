package com.jointt.generator.core;

import com.jointt.generator.constant.TemplateType;
import com.jointt.generator.core.model.TableVO;

/**
 * 代码生成器的工具类
 * 
 * @author yongan.quan
 */
public class PoushengGenerator {

	/**
	 * 单表（内嵌页）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorOneTableInner(TableVO tableVO) {
		GeneratorOneTable generatorOneTable = new GeneratorOneTable();
		tableVO.setTemplateType(TemplateType.INNER);
		generatorOneTable.generatorOneTable(tableVO);
	}

	/**
	 * 单表（弹出框）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorOneTableModel(TableVO tableVO) {
		GeneratorOneTable generatorOneTable = new GeneratorOneTable();
		tableVO.setTemplateType(TemplateType.MODEL);
		generatorOneTable.generatorOneTable(tableVO);
	}

	/**
	 * 单表（树形）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorOneTableTree(TableVO tableVO) {
		GeneratorOneTableTree generatorOneTableTree = new GeneratorOneTableTree();
		generatorOneTableTree.generatorOneTableTreeModel(tableVO);
	}

	/**
	 * 主从（内嵌）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorMasterDetailInner(TableVO tableVO) {
		GeneratorMasterDetail GeneratorMasterDetail = new GeneratorMasterDetail();
		tableVO.setTemplateType(TemplateType.INNER);
		GeneratorMasterDetail.generatorMasterDetail(tableVO);
	}
	/**
	 * 主从（弹窗）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorMasterDetailModel(TableVO tableVO) {
		GeneratorMasterDetail GeneratorMasterDetail = new GeneratorMasterDetail();
		tableVO.setTemplateType(TemplateType.MODEL);
		GeneratorMasterDetail.generatorMasterDetail(tableVO);
	}
	/**
	 * 一主多从
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorOneToMany(TableVO tableVO) {
		GeneratorOneToMany generatorOneToMany = new GeneratorOneToMany();
		generatorOneToMany.generatorOneToMany(tableVO);
	}

	/**
	 * 一主多从（树形）
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorOneToManyTree(TableVO tableVO) {
		GeneratorOneToManyTree generatorOneToManyTree = new GeneratorOneToManyTree();
		generatorOneToManyTree.generatorOneToManyTree(tableVO);
	}
	
	/**
	 * 多对多 (只生成主表和中间表)
	 * 
	 * @param tableVO
	 *            表实体转换类
	 */
	public static void generatorManyToMany(TableVO tableVO) {
		GeneratorManyToMany generatorManyToMany = new GeneratorManyToMany();
		generatorManyToMany.generatorManyToMany(tableVO);
	}
	
}
