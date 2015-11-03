<#include "common.ftl">   
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="<#noparse>${</#noparse>jsPath<#noparse>}</#noparse>/module/${sub_package_path}/${classNameLowerCase}_list.js"></script> 

<div data-layout fit="true" style="height: 100%; width: 100%;">
	<div region="west" border="false" fit="true" style="width: 250px" title="菜单树">
		<div class="datagrid-search">
		   <form class="form-search" onkeydown="if(event.keyCode==13){return false;}" >
			   	<ul>
			   		<li style="width:245px">
						<label style="width:60px"><spring:message code="${classNameAllLowerCase}.txt.keyword"/>:</label>
						<input  type="text" class="search-query" placeholder='<spring:message code="${classNameAllLowerCase}.txt.keyword"/>'  id="keyword" style="width:170px"/>
					</li>
			   	</ul>
			</form>
		</div>
		<ul id="${classNameLowerCase}Tree" class="ztree"></ul>
	</div>
	<div region="center">
		<div id="${classNameLowerCase}ListGrid" data-datagrid="{singleSelect:false}" url="${classNameLowerCase}/dataGrid" >
			<div class="datagrid-toolbar" id="${classNameLowerCase}-toolbar">
				<div class="btn-group toolButton">
					<a class="btn" id="add" name="add">
						<i class="icon-plus"></i><spring:message code="common.btn.add"/>
					</a>
					<a class="btn" id="edit" name="edit">
						<i class="icon-edit"></i><spring:message code="common.btn.edit"/>
					</a> 			
					<a class="btn" id="delete" name="delete">
						<i class="icon-trash"></i><spring:message code="common.btn.delete"/>
					</a> 
					<a class="btn" id="view" name="view">
						<i class="icon-zoom-in"></i><spring:message code="common.btn.view"/>
					</a> 
					<a class="btn" id="search" name="search"> 
						<i class="icon-search"></i><spring:message code="common.btn.search"/>
					</a>
				</div>
			</div>
			<div class="datagrid-search">
			   <form class="form-search" style="width:100%"  >
				   	<ul>
				   		<li>
							<label><spring:message code="${classNameAllLowerCase}.txt.${table.columns[0].columnNameLowerCase}" />：</label>
							<input type="text" placeholder='<spring:message code="${classNameAllLowerCase}.txt.${table.columns[0].columnNameLowerCase}" />' name="filter_${table.columns[0].columnNameLowerCase}" /></li>
						</li>
						<li >
							<label><spring:message code="${classNameAllLowerCase}.txt.${table.columns[1].columnNameLowerCase}" />：</label>
							<input type="text" placeholder='<spring:message code="${classNameAllLowerCase}.txt.${table.columns[1].columnNameLowerCase}" />' name="filter_${table.columns[1].columnNameLowerCase}" /> 
				   		</li>
				  
				   	</ul>
				</form>
			</div>
			<table class="table">
				<thead>
				    <tr>
						<th width="25"><spring:message code="common.txt.seq"/></th>
						<th width="13"><input id="checkbox" type="checkbox" class="datagrid-header-check"/></th>
						<#list table.columns as column>
						<th width="100" class="sort-header" data-code="${column}"><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameLowerCase}" /></th> 
						</#list>
					</tr>
				</thead>
				<tbody style="display:none" >
			   		<tr>
		           		<td>{{:<#noparse>#</#noparse>index+1}}</td>
		           		<td><input type="checkbox" class="datagrid-cell-check" value="{{:${pk.columnNameLowerCase}}}"/></td>
						<#list table.columns as column>
						<td>{{:${column.columnNameLowerCase} }}</td>
						</#list>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
