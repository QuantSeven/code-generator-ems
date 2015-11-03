<#include "common.ftl"> 
$("document").readyfn(function() {
	var context = this, selectItem = null, selectItems = null;
	var i18n${className} = pousheng.getI18N('${classNameAllLowerCase}'); // 初始化的时候获取国际化的文字
	var ${classNameLowerCase}ListGrid = $("#${classNameLowerCase}ListGrid").datagrid();
	
	<#list table.childrens as child>
	var select${child.className}Item = null;
	var ${child.classNameLowerCase}ListGrid = $(context).find("#${child.classNameLowerCase}ListGrid").datagrid({pagination:false,singleSelect:false});
	</#list>
	/****************************--双击查看记录--******************************/
	${classNameLowerCase}ListGrid.datagrid("option", "onDblClickRow", function(index, item) {
		selectItem = item;
		showView();
	});
	
	${classNameLowerCase}ListGrid.datagrid("option", "onClickRow", function(index, data) {
		selectItem = data;
		<#list table.childrens as child>
		${child.classNameLowerCase}ListGrid.datagrid("refresh", null, {
			filter_${child.relationColumn.columnNameLowerCase} : data.${child.parentRelationColumn.columnNameLowerCase}
		});
		</#list>
	});
	
	<#list table.childrens as child>
	${child.classNameLowerCase}ListGrid.datagrid("option", "onClickRow", function(index, data) {
		select${child.className}Item = data;
	});
	${child.classNameLowerCase}ListGrid.datagrid("option", "onDblClickRow", function(index, data) {
		select${child.className}Item = data;
		var options = {
			title: i18n${className}.txt.view${child.classNameAllLowerCase},
			url:'${classNameLowerCase}/view${child.className}Form',
			requestParam : {
				<#list child.primaryKeyColumns as pkColumn>
				${pkColumn.columnNameLowerCase} : select${child.className}Item.${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>,</#if>
				</#list> 
			}
		};
		subModelView(options);
	});
	</#list>
	
	/****************************--添加记录--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#add",function(){
		var options = {
			title : i18n${className}.txt.add,
			url : '${classNameLowerCase}/addForm'
		};
		modelDialog(options);
	});
	
	/****************************--编辑记录--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#edit",function(){
		selectItems = ${classNameLowerCase}ListGrid.datagrid("getSelectedRows");
		if ($.isEmptyObject(selectItems)) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecordedit);
			return;
		}
		if($.isArray(selectItems) && selectItems.length > 1) {
			pousheng.warnMsg(i18n${className}.msg.onerecordedit);
			return;
		}
		selectItem = selectItems[0];
		var options = {
			title : i18n${className}.txt.add,
			url : '${classNameLowerCase}/editForm',
			requestParam : {
				${pk.columnNameLowerCase} : selectItem.${pk.columnNameLowerCase}
			}
		};
		modelDialog(options);
	});
	
	/****************************--删除记录--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#delete",function(){
		selectItems = ${classNameLowerCase}ListGrid.datagrid("getSelectedRowsIdKey");
		if (!selectItems) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecorddelete);
			return;
		}
		pousheng.confirm(i18n${className}.msg.confirmdelete, function(r) {
			if (r) {
				pousheng.ajaxData("${classNameLowerCase}/deleteAll", {
					data : {
						${classNameLowerCase}Ids : selectItems
					}
					
				}).done(function() {
					${classNameLowerCase}ListGrid.datagrid("refresh", null, null);
					<#list table.childrens as child>
					${child.classNameLowerCase}ListGrid.datagrid("refresh", null, null);
					</#list>
				});
			}
		});
	});
	/****************************--查看记录信息--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#view",function(){
		selectItems = ${classNameLowerCase}ListGrid.datagrid("getSelectedRows");
		if ($.isEmptyObject(selectItems)) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecordview);
			return;
		}
		if($.isArray(selectItems) && selectItems.length > 1) {
			pousheng.warnMsg(i18n${className}.msg.onerecordview);
			return;
		}
		selectItem = selectItems[0];
		showView();
	});
	/****************************--查询--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#search",function(){
         ${classNameLowerCase}ListGrid.datagrid("refresh",null,$(context).find("#${classNameLowerCase}Form").getFieldValues());
	});
	
	
	/*************************--查看记录信息公共弹出窗口--*************************/
	var showView = function view() {
		$.modal({
			title : i18n${className}.txt.view,
			width : 600,
			height : 400,
			remote : "${classNameLowerCase}/view",
			requestParam : {
				${pk.columnNameLowerCase} : selectItem.${pk.columnNameLowerCase}
			},
			ready : function(event, context) {
				// 弹出框口页面的初始化操作
				$(context).viewform(); // 先替换基本的标签
			},
			buttons : [ {
				text : btn.close,
				cls : "btn-primary",
				click : function() {
					$(this).modal("close");
				}
			} ]
		});
	};
	
	/*************************--添加、编辑的公共弹出窗口--*************************/
	var modelDialog = function(options) {
		var settings = {
			title : '',
			url : '',
			requestParam : ''
		};
		$.extend(true, settings, options); // true深度拷贝
		$.modal({
			title : settings.title,
			width : 600,
			height : 400,
			remote : settings.url,
			requestParam : settings.requestParam,
			ready : function(event, context) {
				// 弹出框口页面的初始化操作
			},
			buttons : [ {
				text : btn.save,
				cls : "btn-primary",
				click : function() {
					var $this = $(this);
					$this.find("form").trigger("submit", {
						success : function(data) {
							${classNameLowerCase}ListGrid.datagrid("refresh", null, null);
							$this.modal("close");
						}
					});
				}
			}, {
				text : btn.cancel,
				click : function() {
					$(this).modal("close");
				}
			} ]
		});
	};
	
	
	//#####------------------------------从表关系维护--------------------------------#####
	<#if (table.childrens?size > 0)>
	//子表公共查看
	var subModelView = function (options) { //查看
		var settings = {
			title : '',
			url : '',
			requestParam : ''
		};
		$.extend(true, settings, options); // true深度拷贝
		$.modal({
			title : settings.title,
			width : 615,
			height : 440,
			remote : settings.url,
			requestParam : settings.requestParam,
			ready : function(event, context) {
				$(context).viewform(); // 先替换基本的标签
			},
			buttons : [ {
				text : btn.ok,
				cls : "btn-primary", 
				click : function() {
					$(this).modal("close");
				}
			}]
		});
	};
	//子表公共添加么，编辑页面
	var subModelDialog = function (options) {
		var settings = {
			paramDataGrid : null,
			title : '',
			url : '',
			requestParam : ''
		};
		$.extend(true, settings, options); // true深度拷贝
		$.modal({
			title : settings.title,
			width : 615,
			height : 440,
			remote : settings.url,
			requestParam : settings.requestParam,
			ready : function(event, context) {
				// 弹出框口页面的初始化操作
			},
			buttons : [ {
				text : btn.save,
				cls : "btn-primary",
				click : function() {
					var $this = $(this);
					$this.find("form").trigger("submit", {
						success : function(data) {
							settings.paramDataGrid.datagrid("refresh", null, null);
							$this.modal("close");
						}
					});
				}
			}, {
				text : btn.cancel,
				click : function() {
					$(this).modal("close");
				}
			} ]
		});
	};
	<#list table.childrens as child>
	$("#${child.classNameLowerCase}-toolbar").on("click","#add${child.className}",function(){
		selectItem = ${classNameLowerCase}ListGrid.datagrid("getSelect");
		if(!selectItem.${child.parentRelationColumn.columnNameLowerCase}){
			pousheng.warnMsg(i18n${className}.msg.noselect);
			return;
		}
		var columns = [ 
		               	{title : '',checkbox : true, field : '${table.relationTable.primaryKeyColumns[0].columnNameLowerCase}',width : 150}, 
		               	<#list table.relationTable.columns as column>
		               	<#if column_index <=1>
		               	{title : 'i18n${className}.txt.${column.columnNameAllLowerCase}', field : '${column.columnNameLowerCase}',sortName : '${column.sqlName}',search : true, width : 150}<#if column_has_next>,</#if>
		               	<#else>
		               	{title : 'i18n${className}.txt.${column.columnNameAllLowerCase}', field : '${column.columnNameLowerCase}',sortName : '${column.sqlName}', width : 150}<#if column_has_next>,</#if>
		               	</#if>
						</#list>
					 ];
		common.select({
			requestParam : {
				columnJson : JSON.stringify(columns)
			},
			title : i18n${className}.txt.add${child.classNameAllLowerCase},
			dataUrl : '${table.relationTableDataUrl}'
		}, function(data) {
			pousheng.ajaxData("${table.middleTableInsertUrl}",{
				data:{
					<#list table.relationTable.columns as relationTableColumn>
					<#list table.childrens as child>
					<#list child.columns as column>
					<#if relationTableColumn == column>
					${column.columnNameLowerCase} : data.${relationTableColumn.columnNameLowerCase},
					</#if>
					</#list>
					</#list>
					</#list>
					${child.parentRelationColumn.columnNameLowerCase}:selectItem.${child.parentRelationColumn.columnNameLowerCase}
				}
			});
			${child.classNameLowerCase}ListGrid.datagrid("refresh", null, null);
		});
	});
	
	$("#${child.classNameLowerCase}-toolbar").on("click","#delete${child.className}",function(){
		select${child.className}Item = ${child.classNameLowerCase}ListGrid.datagrid("getSelectedRows");
		if(select${child.className}Item && select${child.className}Item.length <= 0){
			pousheng.warnMsg(i18n${className}.msg.noselect${child.classNameAllLowerCase});
			return;
		}
		pousheng.confirm(i18n${className}.msg.confirmdelete${child.classNameAllLowerCase}, function(r) {
			if (r) {
				pousheng.ajaxData("${table.middleTableDeleteUrl}", {
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(select${child.className}Item)
				}).done(function() {
					${child.classNameLowerCase}ListGrid.datagrid("refresh", null, null);
				});
			}
		});
	});
	</#list>
	</#if>
});
