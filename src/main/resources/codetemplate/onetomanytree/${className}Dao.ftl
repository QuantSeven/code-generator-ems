<#include "common.ftl"> 
package ${table.templateModel.daoPackage};

import java.util.List;

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

	<#if table.isSubTable>
	List<${className}> findBy${table.relationColumn.columnName}(@Param("${table.relationColumn.columnNameLowerCase}") ${table.relationColumn.javaType} ${table.relationColumn.columnNameLowerCase});
	
	Integer deleteBy${table.relationColumn.columnName}(@Param("${table.relationColumn.columnNameLowerCase}") ${table.relationColumn.javaType} ${table.relationColumn.columnNameLowerCase});
	</#if>
	
}
