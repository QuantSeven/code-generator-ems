<#include "common.ftl">  
package ${serviceimpl_package};

import javax.annotation.Resource;

import ${dao_package}.${className}Dao;
import ${model_package}.${className};
import ${service_package}.${className}Service;
import org.springframework.stereotype.Service;

import ${base_package}.web.ui.DataGrid;
import ${base_package}.web.ui.PageRequest;

import framework.generic.paginator.domain.PageList;

@Service("${classNameLowerCase}Service")
public class ${className}ServiceImpl implements ${className}Service {

	private ${className}Dao ${classNameLowerCase}Dao;
	
	@Resource
	public void set${className}Dao(${className}Dao ${classNameLowerCase}Dao) {
		this.${classNameLowerCase}Dao = ${classNameLowerCase}Dao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${service_package}.${className}Service#getDatagrid(${base_package}.web.ui.PageRequest)
	 */
	@Override
	public DataGrid<${className}> getDatagrid(PageRequest pageRequest) {
		PageList<${className}> ${classNameLowerCase}s = ${classNameLowerCase}Dao.findByPage(pageRequest.getParameter(), pageRequest.getPageBounds());
		return new DataGrid<${className}>(${classNameLowerCase}s.getPaginator().getTotalCount(), ${classNameLowerCase}s);
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
