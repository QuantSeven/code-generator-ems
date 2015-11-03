<#assign base_package = table.templateModel.basePackage> 
<#assign dao_package = table.templateModel.daoPackage> 
<#assign service_package = table.templateModel.servicePackage> 
<#assign serviceimpl_package = table.templateModel.serviceImplPackage> 
<#assign controller_package = table.templateModel.controllerPackage> 
<#assign model_package = table.templateModel.modelPackage>
 
<#assign sub_package_path = table.templateModel.subPackagePath> 
<#assign template_type = table.templateModel.templateType> 
<#assign jsp_path = table.templateModel.jspPath> 
<#assign js_path = table.templateModel.javaScriptPath> 

<#assign className = table.className> 
<#assign classNameLowerCase = className?uncap_first> 
<#assign classNameAllLowerCase = table.classNameAllLowerCase> 
<#assign pk = table.primaryKeyColumns[0]> 