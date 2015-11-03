<#include "common.ftl"> 
$("document").readyfn(function() {
	var context = this, selectItem = null, selectItems = null, ${classNameLowerCase}Tree = null;
	var i18n${className} = pousheng.getI18N('${classNameAllLowerCase}'); // 初始化的时候获取国际化的文字
	var ${classNameLowerCase}ListGrid = $("#${classNameLowerCase}ListGrid").datagrid();
	
	<#list table.childrens as child>
	var select${child.className}Item = null;
	var ${child.classNameLowerCase}ListGrid = $(context).find("#${child.classNameLowerCase}ListGrid").datagrid({pagination:false,singleSelect:false});
	</#list>
	
	/** ***********************菜单树结构start****************************** */
	var setting${className}Tree = {
		url : '${classNameLowerCase}/list',
		data : {
			key : {
				name : "${table.searchNodeColumn.columnNameLowerCase}",
				children:"nodes"
			},
			simpleData : {
				enable : false,
				idKey : '${table.idKeyColumn.columnNameLowerCase}',
				pIdKey : '${table.parentColumn.columnNameLowerCase}'
			}
		},
		view : {
			// common.js 中公共的方法,给搜索选中的节点加深颜色
			fontCss : getFontCss
		},
		callback : {
			// 单击节点
			onClick : function(event, treeId, treeNode) {
				selectItem = treeNode;
				<#list table.childrens as child>
				${child.classNameLowerCase}ListGrid.datagrid("refresh",null,{filter_${table.parentColumn.columnNameLowerCase}:treeNode.${table.idKeyColumn.columnNameLowerCase}});
				</#list>
			},
			/****************************--双击查看记录--******************************/
			onDblClick: function(event, treeId, treeNode) {
				selectItem = treeNode;
				showView();
			}
		}
	};
	var init${className}Tree = function() {
		$.getJSON('${classNameLowerCase}/list', function(data) {
			if (data.length <= 0) {
				return;
			}
			$.fn.zTree.init($(context).find("#${classNameLowerCase}Tree"), setting${className}Tree, data);
			${classNameLowerCase}Tree = $.fn.zTree.getZTreeObj("${classNameLowerCase}Tree");
		});
	};
	init${className}Tree();// 第一次加载调用
	/** **************以下是查询树节点数据的具体步骤 common.js 中公共的方法************ */
	$(context).find("#keyword").on("keyup", function() {
		searchNode('${table.searchNodeColumn.columnNameLowerCase}', $(this), ${classNameLowerCase}Tree);
	}).on("blur", function() {
		blurKey();
	});
	/** ***********************菜单树结构end****************************** */
	
	
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
		if ($.isEmptyObject(selectItem)) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecordedit);
			return;
		}
		var options = {
			title : i18n${className}.txt.add,
			url : '${classNameLowerCase}/updateForm',
			requestParam : {
				<#list table.primaryKeyColumns as pkColumn>
				${pkColumn.columnNameLowerCase} : selectItem.${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>,</#if>
				</#list>
			}
		};
		modelDialog(options);
	});
	
	/****************************--删除记录--*********************************/
	$("#${classNameLowerCase}-toolbar").on("click","#delete",function(){
		if ($.isEmptyObject(selectItem)) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecorddelete);
			return;
		}
		pousheng.confirm(i18n${className}.msg.confirmdelete, function(r) {
			if (r) {
				pousheng.ajaxData("${classNameLowerCase}/remove", {
					data : {
						<#list table.primaryKeyColumns as pkColumn>
						${pkColumn.columnNameLowerCase} : selectItem.${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>,</#if>
						</#list>
					}
				}).done(function() {
					init${className}Tree();//刷新树
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
		if ($.isEmptyObject(selectItem)) {
			pousheng.warnMsg(i18n${className}.msg.selectonerecorddelete);
			return;
		}
		showView();
	});
	
	
	/*************************--查看记录信息公共弹出窗口--*************************/
	var showView = function view() {
		$.modal({
			title : i18n${className}.txt.view,
			width : 600,
			height : 400,
			remote : "${classNameLowerCase}/viewForm",
			requestParam : {
				<#list table.primaryKeyColumns as pkColumn>
				${pkColumn.columnNameLowerCase} : selectItem.${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>,</#if>
				</#list>
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
							init${className}Tree();
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
	//子表弹出框，添加，编辑
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
		if($.isEmptyObject(selectItem)){
			pousheng.warnMsg(i18n${className}.msg.noselect);
			return;
		}
		var options = {
			title: i18n${className}.txt.add${child.classNameAllLowerCase},
			url:'${classNameLowerCase}/add${child.className}Form',
			requestParam : {
				${child.parentRelationColumn.columnNameLowerCase} : selectItem.${child.parentRelationColumn.columnNameLowerCase}
			},
			paramDataGrid : ${child.classNameLowerCase}ListGrid
		};
		subModelDialog(options);
	});
	$("#${child.classNameLowerCase}-toolbar").on("click","#edit${child.className}",function(){
		select${child.className}Item = ${child.classNameLowerCase}ListGrid.datagrid("getSelect");
		if($.isEmptyObject(select${child.className}Item)){
			pousheng.warnMsg(i18n${className}.msg.noselect${child.classNameAllLowerCase});
			return;
		}
		var options = {
			title: i18n${className}.txt.edit${child.classNameAllLowerCase},
			url:'${classNameLowerCase}/edit${child.className}Form',
			requestParam : {
				<#list child.primaryKeyColumns as pkColumn>
				${pkColumn.columnNameLowerCase} : select${child.className}Item.${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if>
				</#list>
			},
			paramDataGrid : ${child.classNameLowerCase}ListGrid
		};
		subModelDialog(options);
	});
	$("#${child.classNameLowerCase}-toolbar").on("click","#delete${child.className}",function(){
		select${child.className}Item = ${child.classNameLowerCase}ListGrid.datagrid("getSelectedRows");
		if(select${child.className}Item && select${child.className}Item.length <= 0){
			pousheng.warnMsg(i18n${className}.msg.noselect${child.classNameAllLowerCase});
			return;
		}
		pousheng.confirm(i18n${className}.msg.confirmdelete${child.classNameAllLowerCase}, function(r) {
			if (r) {
				pousheng.ajaxData("${classNameLowerCase}/delete${child.className}", {
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(select${child.className}Item)
				}).done(function() {
					${child.classNameLowerCase}ListGrid.datagrid("refresh", null, null);
				});
			}
		});
	});
	/****************************--查询--*********************************/
	$("#${child.classNameLowerCase}-toolbar").on("click","#search${child.className}",function(){
         ${child.classNameLowerCase}ListGrid.datagrid("refresh",null,$(context).find("#${child.classNameLowerCase}Form").getFieldValues());
	});
	/****************************--查看记录信息--*********************************/
	$("#${child.classNameLowerCase}-toolbar").on("click","#view${child.className}",function(){
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
	</#if>
});
