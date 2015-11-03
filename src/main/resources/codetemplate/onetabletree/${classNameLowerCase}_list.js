<#include "common.ftl">  
$("document").readyfn(function() {
	var context = this, selectItem = null, selectItems = null, ${classNameLowerCase}Tree = null;
	var i18n${className} = pousheng.getI18N('${classNameAllLowerCase}'); // 初始化的时候获取国际化的文字
	var ${classNameLowerCase}ListGrid = $("#${classNameLowerCase}ListGrid").datagrid();

	/****************************--双击查看记录--******************************/
	${classNameLowerCase}ListGrid.datagrid("option", "onDblClickRow", function(index, item) {
		selectItem = item;
		showView();
	});
	
	/** ***********************菜单树结构start****************************** */
	var setting${className}Tree = {
		url : '${classNameLowerCase}/list',
		data : {
			key : {
				name : "${table.searchNodeColumn.columnNameLowerCase}"
			},
			simpleData : {
				enable : true,
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
				${classNameLowerCase}ListGrid.datagrid("refresh",null,{filter_${table.parentColumn.columnNameLowerCase}:treeNode.${table.idKeyColumn.columnNameLowerCase}});
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
         ${classNameLowerCase}ListGrid.datagrid("refresh",null,$(context).find("form").getFieldValues());
	});
	
	
	/*************************--查看记录信息公共弹出窗口--*************************/
	var showView = function view() {
		$.modal({
			title : i18n${className}.txt.view,
			width : 600,
			height : 400,
			remote : "${classNameLowerCase}/view",
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
							init${className}Tree();//刷新树结构
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
});
