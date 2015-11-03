<#include "common.ftl">  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="<#noparse>${</#noparse>jsPath<#noparse>}</#noparse>/module/${sub_package_path}/${classNameLowerCase}_list.js">
</script>
<div data-layout >
	<div region="center" title="<spring:message code="${classNameAllLowerCase}.txt.gridtitle"/>">
		<div id="${classNameLowerCase}ListGrid" data-datagrid="datagrid" url="${classNameLowerCase}/dataGrid">
		<div class="datagrid-toolbar" id="${classNameLowerCase}-toolbar">
			<div class="btn-group toolButton">
				<a class="btn" name="add" id="add" href="#">
					<i class="icon-plus"></i><spring:message code="common.btn.add"/>
				</a>
				<a class="btn" name="edit" id="edit" href="#">
					<i class="icon-edit"></i><spring:message code="common.btn.edit"/>
				</a> 			
				<a class="btn" name="delete" id="delete" href="#">
					<i class="icon-trash"></i><spring:message code="common.btn.delete"/>
				</a> 
				<a class="btn" name="view" id="view" href="#">
					<i class="icon-zoom-in"></i><spring:message code="common.btn.view"/>
				</a> 
				<a class="btn" name="search" id="search"> 
					<i class="icon-search"></i><spring:message code="common.btn.search"/>
				</a>
			</div>
		</div>
		<div class="datagrid-search">
		   <form class="form-search" style="width:100%" id="${classNameLowerCase}Form">
			   	<ul>
			   		<li>
			   			<#list table.columns as column>
			   			<#if column_index <= 1 >
						<label><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}"/>：</label>
						<input type="text" placeholder='<spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}" />' name="filter_${column.columnNameLowerCase}" /></li>
			   			</#if>
						</#list>
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
						<th width="100" class="sort-header" data-code="${column}"><spring:message code="${classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}" /></th> 
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
	<div region="east" title="  " split="true" style="width:450px;" >
		<#if table.childrens?size gt 1>
		<div id="${classNameLowerCase}Tab" data-tabs>
			<#list table.childrens as child>
			<div title='<spring:message code="${classNameAllLowerCase}.txt.${child.classNameAllLowerCase}"/>'>
				<div id="${child.classNameLowerCase}ListGrid" data-datagrid="{pagination:false,singleSelect:false}" url="${table.middleTableDataUrl }">
					<div class="datagrid-toolbar" id="${child.classNameLowerCase}-toolbar">
						<div class="btn-group toolButton">
							<a class="btn" name="add${child.className }" id="add${child.className }" href="#">
								<i class="icon-plus"></i><spring:message code="common.btn.add"/>
							</a>
							<a class="btn" name="delete${child.className }" id="delete${child.className }" href="#">
								<i class="icon-trash"></i><spring:message code="common.btn.delete"/>
							</a> 
						</div>
					</div>
					<table class="table">
						<thead>
						    <tr>
								<th width="25"><spring:message code="common.txt.seq"/></th>
								<th width="13"><input id="checkbox" type="checkbox" class="datagrid-header-check"/></th>
								<#list child.columns as column>
								<th width="100" class="sort-header" data-code="${column}"><spring:message code="${child.classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}" /></th> 
								</#list>
							</tr>
						</thead>
						<tbody style="display:none" >
					   		<tr>
				           		<td>{{:<#noparse>#</#noparse>index+1}}</td>
				           		<td><input type="checkbox" class="datagrid-cell-check" value="{{:${child.primaryKeyColumns[0].columnNameLowerCase}}}"/></td>
								<#list child.columns as column>
								<td>{{:${column.columnNameLowerCase} }}</td>
								</#list>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</#list>
		</div>
		<#else>
		<#list table.childrens as child>
		<div id="${child.classNameLowerCase}ListGrid" data-datagrid="{pagination:false}" url="${classNameLowerCase}/${child.classNameLowerCase}DataGrid">
			<div class="datagrid-toolbar" id="${child.classNameLowerCase}-toolbar">
				<div class="btn-group toolButton">
					<a class="btn" name="add${child.className }" id="add${child.className }" href="#">
						<i class="icon-plus"></i><spring:message code="common.btn.add"/>
					</a>
					<a class="btn" name="delete${child.className }" id="delete${child.className }" href="#">
						<i class="icon-trash"></i><spring:message code="common.btn.delete"/>
					</a> 
				</div>
			</div>
			<table class="table">
				<thead>
				    <tr>
						<th width="25"><spring:message code="common.txt.seq"/></th>
						<th width="13"><input id="checkbox" type="checkbox" class="datagrid-header-check"/></th>
						<#list child.columns as column>
						<th width="100" class="sort-header" data-code="${column}"><spring:message code="${child.classNameAllLowerCase}.txt.${column.columnNameAllLowerCase}" /></th> 
						</#list>
					</tr>
				</thead>
				<tbody style="display:none" >
			   		<tr>
		           		<td>{{:<#noparse>#</#noparse>index+1}}</td>
		           		<td><input type="checkbox" class="datagrid-cell-check" value="{{:${child.primaryKeyColumns[0].columnNameLowerCase}}}"/></td>
						<#list child.columns as column>
						<td>{{:${column.columnNameLowerCase} }}</td>
						</#list>
					</tr>
				</tbody>
			</table>
		</div>
		</#list>
		</#if>
	</div>
</div>
