<#include "common.ftl">  
package ${model_package};

import com.fasterxml.jackson.annotation.JsonFormat;

import framework.generic.annotation.Column;
import framework.generic.annotation.Table;
import framework.generic.model.PersistentModel;

<#if table.tableRemark!="">
/**
 * ${table.tableRemark}
 */
</#if>
@Table(name = "${table.tableName}")
public class ${className} implements PersistentModel {
	<@generateFields/>
}
<#macro generateFields>
	
	<#list table.columns as column>
	<#if column.pk>
	<#if column.comment!="">
	/**
	 * ${column.comment}
	 */
	</#if>
	@Column(name = "${column}", pk = true, order = ${column_index})
	private ${column.javaType} ${column.columnNameLowerCase};
	<#else>
	<#if column.comment!="">
	/**
	 * ${column.comment}
	 */
	</#if>
	@Column(name = "${column}")
	private ${column.javaType} ${column.columnNameLowerCase};
	</#if>
	</#list>
	
	<#list table.columns as column>
	<#if column.javaType == "java.util.Date">
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	</#if>
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLowerCase};
	}
	
	public void set${column.columnName}(${column.javaType} ${column.columnNameLowerCase}) {
		this.${column.columnNameLowerCase} = ${column.columnNameLowerCase};
	}
	
	</#list>
</#macro>