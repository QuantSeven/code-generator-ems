<#include "common.ftl"> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<form id="${classNameLowerCase}Form" class="pageForm"  title='<spring:message code="${classNameAllLowerCase}.txt.title.info"/>' action="<#noparse>${</#noparse>action<#noparse>}</#noparse>" method="post"  modelAttribute="${classNameLowerCase}">
    <div class="page-content">
		<div class="pageFormContent form-area" title='<spring:message code="${classNameAllLowerCase}.txt.title.info"/>'>
			<ul>
				<#list table.columns as column>
				<#if !column.pk>
				<li>
					<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameLowerCase}"/>:</label>
					<#if (column.javaType == "java.util.Date") || (column.javaType == "java.sql.Timestamp")>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly"  />
					<#else>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>" />
					</#if>
				</li>
				<#else>
				<li>
					<label class="red"><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameLowerCase}"/>:</label>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>"  validate="{required:true<c:if test="<#noparse>${</#noparse>empty ${classNameLowerCase}<#noparse>}</#noparse>"> ,remote:'${classNameLowerCase}/validatePk',messages:{remote:'必须唯一'}</c:if>}" <c:if test="<#noparse>${</#noparse>not empty ${classNameLowerCase}<#noparse>}</#noparse>"> readonly="readonly" class="readonly"</c:if>  />
				</li>
				</#if>
				</#list>
				
			</ul>
		</div>
	</div>
</form>