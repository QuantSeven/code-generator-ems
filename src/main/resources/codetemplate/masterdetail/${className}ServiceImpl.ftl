<#include "common.ftl">  
package ${serviceimpl_package};

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
import framework.generic.utils.string.StringUtil;

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
		<#list table.childrens as child>
		if(!StringUtil.isNullOrEmpty(${classNameLowerCase}.get${child.className}())) {
			${child.className} ${child.classNameLowerCase} = ${classNameLowerCase}.get${child.className}();
			${child.classNameLowerCase}.set${child.relationColumn.columnName}(${classNameLowerCase}.get${child.parentRelationColumn.columnName}());
			${child.classNameLowerCase}Dao.insert(${classNameLowerCase}.get${child.className}());
		}
		</#list>
		return ${classNameLowerCase}Dao.insert(${classNameLowerCase});
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#modify(${model_package}.${className})
	 */
	@Override
	public Integer modify(${className} ${classNameLowerCase}) {
		<#list table.childrens as child>
		if(!StringUtil.isNullOrEmpty(${classNameLowerCase}.get${child.className}())) {
			${child.className} ${child.classNameLowerCase} = ${classNameLowerCase}.get${child.className}();
			${child.classNameLowerCase}.set${child.relationColumn.columnName}(${classNameLowerCase}.get${child.parentRelationColumn.columnName}());
			${child.classNameLowerCase}Dao.update(${classNameLowerCase}.get${child.className}());
		}
		</#list>
		return ${classNameLowerCase}Dao.update(${classNameLowerCase});
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType}<#if pkColumn_has_next>, </#if></#list>)
	 */
	@Override
	public Integer remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>) {
		<#list table.childrens as child>
		${child.classNameLowerCase}Dao.deleteBy${child.relationColumn.columnName}(${child.relationColumn.columnNameLowerCase});
		</#list>
		return ${classNameLowerCase}Dao.deleteByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#removeAll(${pk.javaType}[])
	 */
	@Override
	public Integer removeAll(${pk.javaType}... ${classNameLowerCase}Ids) {
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
}
