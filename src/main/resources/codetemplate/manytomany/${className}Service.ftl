<#include "common.ftl"> 
package ${service_package};

import java.util.List;

import ${model_package}.${className};
<#list table.childrens as child>
import ${child.templateModel.modelPackage}.${child.className};
</#list>
import ${base_package}.web.ui.DataGrid;
import ${base_package}.web.ui.PageRequest;

public interface ${className}Service {
	
	/**
	 * 获取DataGrid列表数据和总数
	 * @param pageRequest datagrid页面请求数据
	 * @return DataGrid
	 */
	DataGrid getDatagrid(PageRequest pageRequest);
	
	/**
	 * 创建一条数据库记录
	 * @param ${classNameLowerCase}
	 * @return 受影响的行数
	 */
	Integer create(${className} ${classNameLowerCase});

	/**
	 * 修改一条数据库记录
	 * @param ${classNameLowerCase}
	 * @return 受影响的行数
	 */
	Integer modify(${className} ${classNameLowerCase});

	/**
	 * 根据主键删除一条数据库记录
	 * @return 受影响的行数
	 */
	Integer remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	
	/**
	 * 批量删除数据库记录
	 * @return 受影响的行数
	 */
	Integer removeAll(${pk.javaType}... ${classNameLowerCase}Ids);
	
	/**
	 * 根据主键查询一条数据库记录
	 * @return 实体类
	 */
	${className} getByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	
	/**
	 * 查询一条数据库记录
	 * @return 实体类
	 */
	${className} get(${pk.javaType} ${pk.columnNameLowerCase});
	
	<#list table.childrens as child>
	/************************${child.className}******************************/
	DataGrid get${child.className}Datagrid(PageRequest pageRequest);
	
	Integer create${child.className}(${child.className} ${child.classNameLowerCase});

	Integer modify${child.className}(${child.className} ${child.classNameLowerCase});

	${child.className} get${child.className}ByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);

	Integer remove${child.className}(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	
	Integer removeAll${child.className}(List<${child.className}> list);
	</#list>
	
}
