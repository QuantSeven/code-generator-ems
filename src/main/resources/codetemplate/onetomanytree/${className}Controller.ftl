<#include "common.ftl"> 
package ${controller_package};

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${model_package}.${className};
<#list table.childrens as child>
import ${child.templateModel.modelPackage}.${child.className};
</#list>
import ${service_package}.${className}Service;
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
	/*--------------------------------查询所有-----------------------------------*/
	@Override
	public List<${className}> list(HttpServletRequest request, HttpServletResponse response)  {
		return ${classNameLowerCase}Service.getAll();
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
			return success(getMessage("msg.success.add"));
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
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public Json update(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int updatedRecords = ${classNameLowerCase}Service.modify(${classNameLowerCase});
			if (updatedRecords <= 0) {
				return error(getMessage("msg.error.update"));
			}
			return success(getMessage("msg.success.update"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.update"));
		}
	}

	/*--------------------------------删除操作-----------------------------------*/
	@Override
	public Json remove(HttpServletRequest request, HttpServletResponse response) {
		try {
			<#list table.primaryKeyColumns as pkColumn>
			${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
			</#list> 
			int deletedRecords = ${classNameLowerCase}Service.remove(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
			if (deletedRecords <= 0) {
				return error(getMessage("msg.error.delete"));
			} 
			return success("${classNameLowerCase}/index",getMessage("msg.success.delete"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.delete"));
		}
	}
	/*--------------------------------查看操作-----------------------------------*/
	@Override
	public ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		<#list table.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase}Service.get(<#list table.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>));
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
	
	
	// ----------------------------------------从表操作------------------------------------//
	<#list table.childrens as child>
	
	@RequestMapping(value = "${child.classNameLowerCase}DataGrid", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public DataGrid ${child.classNameLowerCase}DataGrid(PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = ServletUtil.getParametersMapStartingWith(request, "filter_");
		pageRequest.setParameter(paramMap);
		DataGrid dg = ${classNameLowerCase}Service.get${child.className}Datagrid(pageRequest);
		return dg;
	}

	@RequestMapping(value = "/add${child.className}Form", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add${child.className}Form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("action", "${classNameLowerCase}/insert${child.className}");
		request.setAttribute("${child.relationColumn.columnNameLowerCase}", request.getParameter("${child.relationColumn.columnNameLowerCase}"));
		return new ModelAndView("${child.templateModel.subPackagePath}/${child.classNameLowerCase}_edit");
	}

	@RequestMapping(value = "/insert${child.className}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json insert(@ModelAttribute("${child.classNameLowerCase}") ${child.className} ${child.classNameLowerCase}, HttpServletRequest request, HttpServletResponse response){
		try {
			if (!StringUtil.isNullOrEmpty(${child.classNameLowerCase})) {
				${classNameLowerCase}Service.create${child.className}(${child.classNameLowerCase});
			}
			return success(getMessage("msg.success.add"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.add"));
		}
	}
	@RequestMapping(value = "/edit${child.className}Form", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit${child.className}Form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		<#list child.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${child.className} ${child.classNameLowerCase} = ${classNameLowerCase}Service.get${child.className}ByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${child.classNameLowerCase}", ${child.classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update${child.className}");
		return new ModelAndView("${child.templateModel.subPackagePath}/${child.classNameLowerCase}_edit");
	}
	
	@RequestMapping(value = "/update${child.className}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json update${child.className}(${child.className} ${child.classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int updatedRecords = ${classNameLowerCase}Service.modify${child.className}(${child.classNameLowerCase});
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

	@RequestMapping(value = "/delete${child.className}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json delete${child.className}(@RequestBody List<${child.className}> ${child.classNameLowerCase}s, HttpServletRequest request, HttpServletResponse response) {
		try {
			${classNameLowerCase}Service.removeAll${child.className}(${child.classNameLowerCase}s);
			return success(getMessage("msg.success.delete"));
		} catch (Exception e) {
			e.printStackTrace();
			return error(getMessage("msg.error.delete"));
		}
	}
	
	@RequestMapping(value = "/view${child.className}Form", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view${child.className}Form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		<#list child.primaryKeyColumns as pkColumn>
		${pkColumn.javaType} ${pkColumn.columnNameLowerCase} = ${pkColumn.javaType}.valueOf(request.getParameter("${pkColumn.columnNameLowerCase}"));
		</#list> 
		${child.className} ${child.classNameLowerCase} = ${classNameLowerCase}Service.get${child.className}ByPk(<#list child.primaryKeyColumns as pkColumn>${pkColumn.columnNameLowerCase}<#if pkColumn_has_next>, </#if></#list>);
		request.setAttribute("${child.classNameLowerCase}", ${child.classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update${child.className}");
		return new ModelAndView("${child.templateModel.subPackagePath}/${child.classNameLowerCase}_edit");
	}
	</#list>
}




