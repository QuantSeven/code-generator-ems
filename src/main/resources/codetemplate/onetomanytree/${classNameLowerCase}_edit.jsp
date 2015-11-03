<#include "common.ftl">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<form id="${classNameLowerCase}Form" class="pageForm"  action="<#noparse>${</#noparse>action<#noparse>}</#noparse>" method="post"  modelAttribute="${classNameLowerCase}">
    <div class="page-content">
		<div class="pageFormContent form-area" title='<spring:message code="common.txt.title.info"/>'>
			<ul>
				<#list table.columns as column>
				<#if column.pk>
				<li>
					<label class="red"><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>"  validate="{required:true}" />
				</li>
				<#elseif table.isSubTable && column==table.relationColumn>
				<li>
					<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<c:choose>
						<c:when test="<#noparse>${</#noparse>not empty ${classNameLowerCase}<#noparse>}</#noparse>">
						<#if column.javaType == "java.util.Date">
						<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${classNameLowerCase}.${table.relationColumn.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly" />
						<#else>
						<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${table.relationColumn.columnNameLowerCase}<#noparse>}</#noparse>" readonly="readonly"  />
						</#if>
						</c:when>
						<c:otherwise>
						<#if column.javaType == "java.util.Date">
						<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${table.relationColumn.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly" />
						<#else>
						<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${table.relationColumn.columnNameLowerCase}<#noparse>}</#noparse>" readonly="readonly"  />
						</#if>
						</c:otherwise>
					</c:choose>
				</li>
				<#else>
				<li>
					<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<#if (column.javaType == "java.util.Date") || (column.javaType == "java.sql.Timestamp")>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly"  />
					<#else>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>"/>
					</#if>
				</li>
				</#if>
				</#list>
				
			</ul>
		</div>
	</div>
	<#if template_type == 'inner'> 
	<div class="formBar">
		<ul>
			<c:choose>
				<c:when test="<#noparse>${</#noparse>not empty hideBtnSave <#noparse>}</#noparse>">
					<li><a href="${classNameLowerCase}/index" data-rel="ajax" class="btn btn-primary" > <spring:message code="common.btn.cancel"/> </a></li>
				</c:when>
				<c:otherwise>
					<li><button class="btn btn-primary" type="submit"><spring:message code="common.btn.save"/></button></li> 
					<li><a href="${classNameLowerCase}/index" data-rel="ajax" class="btn" > <spring:message code="common.btn.cancel"/> </a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	</#if>
</form>