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
					<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<#if (column.javaType == "java.util.Date") || (column.javaType == "java.sql.Timestamp")>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly" validate="{required:true}" />
					<#else>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>" validate="{required:true}" />
					</#if>
				</li>
				<#else>
				<li>
					<label class="red"><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<input type="text" name="${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${column.columnNameLowerCase}<#noparse>}</#noparse>"  validate="{required:true<c:if test="<#noparse>${</#noparse>empty ${classNameLowerCase}<#noparse>}</#noparse>"> ,remote:'${classNameLowerCase}/validatePk',messages:{remote:'必须唯一'}</c:if>}" <c:if test="<#noparse>${</#noparse>not empty ${classNameLowerCase}<#noparse>}</#noparse>"> readonly="readonly" class="readonly"</c:if>  />
				</li>
				</#if>
				</#list>
			</ul>
		</div>
		<#list table.childrens as child>
		<div class="pageFormContent form-area" title='<spring:message code="${classNameAllLowerCase}.txt.title.${child.classNameAllLowerCase }info"/>'>
			<ul>
				<#list child.columns as column>
				<#if child.relationColumn.sqlName != column>
				<li>
					<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>:</label>
					<#if (column.javaType == "java.util.Date") || (column.javaType == "java.sql.Timestamp")>
					<input type="text" name="${child.classNameLowerCase }.${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<fmt:formatDate value="<#noparse>${</#noparse>${classNameLowerCase}.${child.classNameLowerCase }.${column.columnNameLowerCase}<#noparse>}</#noparse>" pattern="yyyy-MM-dd" />" class="date" readonly="readonly" />
					<#else>
					<input type="text" name="${child.classNameLowerCase }.${column.columnNameLowerCase}" id="${column.columnNameLowerCase}" value="<#noparse>${</#noparse>${classNameLowerCase}.${child.classNameLowerCase }.${column.columnNameLowerCase}<#noparse>}</#noparse>"  />
					</#if>
				</li>
				</#if>
				</#list>
			</ul>
		</div>
		</#list>
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