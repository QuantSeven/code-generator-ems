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

import ${base_package}.common.web.ServletUtils;
import ${base_package}.web.controller.admin.AbstractController;
import ${base_package}.web.ui.DataGrid;
import ${base_package}.web.ui.JsonModel;
import ${base_package}.web.ui.PageRequest;

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
	public DataGrid<${className}> dataGrid(PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = ServletUtils.getParametersStartingWith(request, "filter_");
		pageRequest.setParameter(paramMap);
		DataGrid<${className}> dataGrid = ${classNameLowerCase}Service.getDatagrid(pageRequest);
		return dataGrid;
	}

	/*--------------------------------添加操作-----------------------------------*/
	@Override
	public ModelAndView addFrom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("action", "${classNameLowerCase}/insert");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public JsonModel insert(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int insertRecords = ${classNameLowerCase}Service.create(${classNameLowerCase});
			if (insertRecords <= 0) {
				return error("新增失败！！！");
			}
			<#if template_type == 'model'> 
			return success("新增成功！！！");
			<#else>
			return success("${classNameLowerCase}/index",getMessage("msg.success.add"));
			</#if>
		} catch (Exception e) {
			e.printStackTrace();
			return error("新增失败！！！错误如下：" + e.getMessage());
		}
	}

	/*--------------------------------编辑操作-----------------------------------*/
	@Override
	public ModelAndView editForm(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		${className} ${classNameLowerCase} = ${classNameLowerCase}Service.get(${pk.columnNameLowerCase});
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase});
		request.setAttribute("action", "${classNameLowerCase}/update");
		return new ModelAndView("${sub_package_path}/${classNameLowerCase}_edit");
	}

	@Override
	public JsonModel update(${className} ${classNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int updatedRecords = ${classNameLowerCase}Service.modify(${classNameLowerCase});
			if (updatedRecords <= 0) {
				return error("更新失败！！！");
			}
			<#if template_type == 'model'> 
			return success("更新成功！！！");
			<#else>
			return success("${classNameLowerCase}/index",getMessage("msg.success.update"));
			</#if>
		} catch (Exception e) {
			e.printStackTrace();
			return error("更新失败！！！错误如下：" + e.getMessage());
		}
	}

	/*--------------------------------删除操作-----------------------------------*/
	<#if template_type == 'model'> 
	@Override
	public JsonModel deleteAll(${pk.javaType}[] ${classNameLowerCase}Ids, HttpServletRequest request, HttpServletResponse response) {
		try {
			int deletedRecords = ${classNameLowerCase}Service.removeAll(${classNameLowerCase}Ids);
			if (deletedRecords <= 0) {
				return error("删除失败！！！");
			} 
			return success("删除成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
			return error("删除失败！！！错误如下：" + e.getMessage());
		}
	}
	<#else>
	@Override
	public JsonModel delete(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) {
		try {
			int deletedRecords = ${classNameLowerCase}Service.removeAll(${pk.columnNameLowerCase});
			if (deletedRecords <= 0) {
				return error("删除失败！！！");
			} 
			return success("${classNameLowerCase}/index","删除成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
			return error("删除失败！！！错误如下：" + e.getMessage());
		}
	}
	</#if>
	/*--------------------------------查看操作-----------------------------------*/
	@Override
	public ModelAndView view(${pk.javaType} ${pk.columnNameLowerCase}, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("${classNameLowerCase}", ${classNameLowerCase}Service.get(${pk.columnNameLowerCase}));
		<#if template_type == 'inner'> 
		request.setAttribute("hideBtnSave", true);
		</#if>
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
}




