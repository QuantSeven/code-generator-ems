<#include "common.ftl"> 
package ${serviceimpl_package};

import java.util.List;

import javax.annotation.Resource;

import ${dao_package}.${className}Dao;
<#list table.childrens as child>
import ${child.templateModel.daoPackage}.${child.className}Dao;
</#list>
import ${model_package}.${className};
<#list table.childrens as child>
import ${child.templateModel.modelPackage}.${child.className};
</#list>
import ${service_package}.${className}Service;
import org.springframework.stereotype.Service;

import ${base_package}.web.ui.DataGrid;
import ${base_package}.web.ui.PageRequest;

import framework.generic.paginator.domain.PageList;

@Service("${classNameLowerCase}Service")
public class ${className}ServiceImpl implements ${className}Service {

	private ${className}Dao ${classNameLowerCase}Dao;
	
	<#list table.childrens as child>
	private ${child.className}Dao ${child.classNameLowerCase}Dao;
	</#list>
	@Resource
	public void set${className}Dao(${className}Dao ${classNameLowerCase}Dao) {
		this.${classNameLowerCase}Dao = ${classNameLowerCase}Dao;
	}
	<#list table.childrens as child>
	@Resource
	public void set${child.className}Dao(${child.className}Dao ${child.classNameLowerCase}Dao) {
		this.${child.classNameLowerCase}Dao = ${child.classNameLowerCase}Dao;
	}
	</#list>
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#getDatagrid(${base_package}.web.ui.PageRequest)
	 */
	@Override
	public DataGrid getDatagrid(PageRequest pageRequest) {
		PageList<${className}> ${classNameLowerCase}s = ${classNameLowerCase}Dao.findByPage(pageRequest.getParameter(), pageRequest.getPageBounds());
		return new DataGrid(${classNameLowerCase}s.getPaginator().getTotalCount(), ${classNameLowerCase}s);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#create(${model_package}.${className})
	 */
	@Override
	public Integer create(${className} ${classNameLowerCase}) {
		return ${classNameLowerCase}Dao.insert(${classNameLowerCase});
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#modify(${model_package}.${className})
	 */
	@Override
	public Integer modify(${className} ${classNameLowerCase}) {
		return ${classNameLowerCase}Dao.update(${classNameLowerCase});
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType}<#if pkColumn_has_next>, </#if></#list>)
	 */
	@Override
	public Integer remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>) {
		<#list table.childrens as child>
		${child.classNameLowerCase}Dao.deleteBy${child.relationColumn.columnName}(${child.parentRelationColumn.columnNameLowerCase});
		</#list>
		return ${classNameLowerCase}Dao.deleteByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#removeAll(${pk.javaType}[])
	 */
	@Override
	public Integer removeAll(${pk.javaType}... ${classNameLowerCase}Ids) {
		for(${pk.javaType} str : ${classNameLowerCase}Ids) {
		<#list table.childrens as child>
			${child.classNameLowerCase}Dao.deleteBy${child.relationColumn.columnName}(str);
		</#list>
		}
		return ${classNameLowerCase}Dao.delete(${classNameLowerCase}Ids);
	}

	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#getByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType}<#if pkColumn_has_next>, </#if></#list>)
	 */
	@Override
	public ${className} getByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>) {
		return ${classNameLowerCase}Dao.findByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#get(${pk.javaType})
	 */
	@Override
	public ${className} get(${pk.javaType} ${pk.columnNameLowerCase}) {
		return ${classNameLowerCase}Dao.find(${pk.columnNameLowerCase});
	}
	
	<#list table.childrens as child>
	/************************${child.className}******************************/
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#get${child.className}Datagrid(${base_package}.web.ui.PageRequest)
	 */
	public DataGrid get${child.className}Datagrid(PageRequest pageRequest) {
		PageList<${child.className}> ${child.classNameLowerCase}s = ${child.classNameLowerCase}Dao.findByPage(pageRequest.getParameter(), pageRequest.getPageBounds());
		return new DataGrid(${child.classNameLowerCase}s.getPaginator().getTotalCount(), ${child.classNameLowerCase}s);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#create${child.className}(${child.className})
	 */
	public Integer create${child.className}(${child.className} ${child.classNameLowerCase}) {
		return ${child.classNameLowerCase}Dao.insert(${child.classNameLowerCase});
	}
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#modify${child.className}(${child.className})
	 */
	public Integer modify${child.className}(${child.className} ${child.classNameLowerCase}) {
		return ${child.classNameLowerCase}Dao.update(${child.classNameLowerCase});
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#get${child.className}ByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType}<#if pkColumn_has_next>, </#if></#list>)
	 */
	public ${child.className} get${child.className}ByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>) {
		return ${child.classNameLowerCase}Dao.findByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	}

	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#remove${child.className}(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType}<#if pkColumn_has_next>, </#if></#list>)
	 */
	public Integer remove${child.className}(<#list child.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>) {
		return ${child.classNameLowerCase}Dao.deleteByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#removeAll${child.className}(java.util.List)
	 */
	public Integer removeAll${child.className}(List<${child.className}> list) {
		return ${child.classNameLowerCase}Dao.deleteAll(list);
	}
	
	</#list>
}
