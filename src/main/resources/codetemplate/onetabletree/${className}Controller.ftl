<#include "common.ftl">  
package ${controller_package};

import java.util.List;
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
	public ModelAndView addFrom(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("action", "${classNameLowerCase}/insert");
		request.setAttribute("${classNameLowerCase}s",${classNameLowerCase}Service.getAll());
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public Json insert(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		try {
			int insertRecords = ${classNameLowerCase}Service.create(${classNameLowerCase});
			if (insertRecords <= 0) {
				return error(getMessage("msg.error.add"));
			}
			return success(getMessage("msg.success.add"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.add"));
		}
	}

	/*--------------------------------编辑操作-----------------------------------*/
	@Override
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) {
		<#list table.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public Json update(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		try {
			int updatedRecords = ${classNameLowerCase}Service.modify(${classNameLowerCase});
			if (updatedRecords <= 0) {
				return error(getMessage("msg.error.add"));
			}
			return success(getMessage("msg.success.update"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.update"));
		}
	}

	/*--------------------------------删除操作-----------------------------------*/
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
	
	/*--------------------------------查看操作-----------------------------------*/
	@Override
	public ModelAndView view(${pk.javaType} key,HttpServletRequest request, HttpServletResponse response) {
		<#list table.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}
	
	/*--------------------------------验证操作-----------------------------------*/
	@Override
	public boolean validatePk(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(${pk.columnNameLowerCase});
		if (StringUtil.isNullOrEmpty(${classNameLowerCase})) {
			return true;
		}
		return false;
	}
	/*--------------------------------查询所有-----------------------------------*/
	@Override
	public List<${className}> list(HttpServletRequest request, HttpServletResponse response)  {
		return ${classNameLowerCase}Service.getAll();
	}
}




