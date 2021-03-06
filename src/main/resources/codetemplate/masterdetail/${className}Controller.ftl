<#include "common.ftl">  
package ${controller_package};


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ${model_package}.${className};
import ${service_package}.${className}Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ${base_package}.web.controller.base.AbstractController;
import ${base_package}.web.ui.DataGrid;
import ${base_package}.web.ui.Json;
import ${base_package}.web.ui.PageRequest;

import framework.generic.utils.string.StringUtil;
import framework.generic.utils.webutils.ServletUtil;

@Controller
@RequestMapping("${classNameLowerCase}/*")
public class ${className}Controller extends AbstractController<${className}, ${pk.javaType}> {

	private ${className}Service ${classNameLowerCase}Service;

	@Resource
	public void set${className}Service(${className}Service ${classNameLowerCase}Service) {
		this.${classNameLowerCase}Service = ${classNameLowerCase}Service;
	}

	/*-------------------------------列表显示页面---------------------------------*/
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_list");
	}

	@Override
	public DataGrid dataGrid(PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = ServletUtil.getParametersMapStartingWith(request, "filter_");
		pageRequest.setParameter(paramMap);
		DataGrid dataGrid = ${classNameLowerCase}Service.getDatagrid(pageRequest);
		return dataGrid;
	}

	/*--------------------------------添加操作-----------------------------------*/
	@Override
	public ModelAndView addFrom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("action", "${classNameLowerCase}/insert");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public Json insert(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int insertRecords = ${classNameLowerCase}Service.create(${classNameLowerCase});
			if (insertRecords <= 0) {
				return error(getMessage("msg.error.add"));
			}
			<#if template_type == 'model'> 
			return success(getMessage("msg.success.add"));
			<#else>
			return success("${classNameLowerCase}/index",getMessage("msg.success.add"));
			</#if>
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.add"));
		}
	}

	/*--------------------------------编辑操作-----------------------------------*/
	@Override
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		<#list table.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.getByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public Json update(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int updatedRecords = ${classNameLowerCase}Service.modify(${classNameLowerCase});
			if (updatedRecords <= 0) {
				return error(getMessage("msg.error.add"));
			}
			<#if template_type == 'model'> 
			return success(getMessage("msg.success.update"));
			<#else>
			return success("${classNameLowerCase}/index",getMessage("msg.success.update"));
			</#if>
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.update"));
		}
	}

	/*--------------------------------删除操作-----------------------------------*/
	<#if template_type == 'model'> 
	@Override
	public Json deleteAll(${pk.javaType}[] ${classNameLowerCase}Ids, HttpServletRequest request, HttpServletResponse response) {
		try {
			int deletedRecords = ${classNameLowerCase}Service.removeAll(${classNameLowerCase}Ids);
			if (deletedRecords <= 0) {
				return error(getMessage("msg.error.delete"));
			} 
			return success(getMessage("msg.success.delete"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.delete"));
		}
	}
	<#else>
	@Override
	public Json delete(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		try {
			int deletedRecords = ${classNameLowerCase}Service.removeAll(${pk.columnNameLowerCase});
			if (deletedRecords <= 0) {
				return error(getMessage("msg.error.delete"));
			} 
			return success("${classNameLowerCase}/index",getMessage("msg.success.delete"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.delete"));
		}
	}
	</#if>
	/*--------------------------------查看操作-----------------------------------*/
	<#if template_type == 'model'> 
	@Override
	public ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		<#list table.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.getByPk(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}
	<#else>
	@Override
	public ModelAndView view(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase}Service.get(${pk.columnNameLowerCase}));
		request.setAttribute("hideBtnSave", true);
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}
	</#if>
	/*--------------------------------验证操作-----------------------------------*/
	@Override
	public boolean validatePk(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(${pk.columnNameLowerCase});
		if (StringUtil.isNullOrEmpty(${classNameLowerCase})) {
			return true;
		}
		return false;
	}
}




