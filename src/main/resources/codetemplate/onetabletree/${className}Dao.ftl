<#include "common.ftl"> 
package ${dao_package};

import org.apache.ibatis.annotations.Param;

import ${model_package}.${className};

import framework.generic.dao.GenericDao;

public interface ${className}Dao extends GenericDao<${className}, ${pk.javaType}> {

	/**
	 * 根据主键查询一条记录
	 */
	${className} findByPk(<#list table.primaryKeyColumns as pkColumn>@Param("${pkColumn.columnNameLowerCase}") ${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);

	/**
	 * 根据主键删除一条记录
	 */
	Integer deleteByPk(<#list table.primaryKeyColumns as pkColumn>@Param("${pkColumn.columnNameLowerCase}") ${pkColumn.javaType} ${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
}
