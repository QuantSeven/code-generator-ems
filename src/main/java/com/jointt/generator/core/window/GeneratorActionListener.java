package com.jointt.generator.core.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.jointt.generator.constant.TemplateType;
import com.jointt.generator.core.GeneratorOneTable;
import com.jointt.generator.core.model.TableVO;
import com.jointt.generator.utils.PropertiesUtil;
import com.jointt.generator.utils.StringUtil;

public class GeneratorActionListener implements ActionListener {

	public GeneratorWindow generatorWindow;

	public GeneratorActionListener(GeneratorWindow generatorWindow) {
		this.generatorWindow = generatorWindow;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		TableVO tableVO = new TableVO();
		String tipMessages = "";
		if (!StringUtil.isNullOrEmpty(generatorWindow.txtPackageName.getText())) {
			tableVO.setPackageName(generatorWindow.txtPackageName.getText());
		}
		if (!StringUtil.isNullOrEmpty(generatorWindow.txtEntityName.getText())) {
			tableVO.setClassName(generatorWindow.txtEntityName.getText());
		} else {
			tipMessages += "实体类名不能为空！\r\n";
		}
		if (!StringUtil.isNullOrEmpty(generatorWindow.txtTableName.getText())) {
			tableVO.setTableName(generatorWindow.txtTableName.getText());
		} else {
			tipMessages += "表名不能为空！";
		}

		// 要生成的模板
		if (generatorWindow.chkDao.isSelected()) {
			PropertiesUtil.DAO_FLAG = true;
		} else {
			PropertiesUtil.DAO_FLAG = false;
		}
		if (generatorWindow.chkDaoXml.isSelected()) {
			PropertiesUtil.XML_FLAG = true;
		} else {
			PropertiesUtil.XML_FLAG = false;
		}
		if (generatorWindow.chkService.isSelected()) {
			PropertiesUtil.SERVICE_FLAG = true;
		} else {
			PropertiesUtil.SERVICE_FLAG = false;
		}
		if (generatorWindow.chkServiceImpl.isSelected()) {
			PropertiesUtil.SERVICEIMPL_FLAG = true;
		} else {
			PropertiesUtil.SERVICEIMPL_FLAG = false;
		}
		if (generatorWindow.chkModel.isSelected()) {
			PropertiesUtil.MODEL_FLAG = true;
		} else {
			PropertiesUtil.MODEL_FLAG = false;
		}
		if (generatorWindow.chkController.isSelected()) {
			PropertiesUtil.CONTROLLER_FLAG = true;
		} else {
			PropertiesUtil.CONTROLLER_FLAG = false;
		}
		if (generatorWindow.chkJsp.isSelected()) {
			PropertiesUtil.JSP_FLAG = true;
		}
		if (generatorWindow.chkJavaScript.isSelected()) {
			PropertiesUtil.JAVASCRIPT_FLAG = true;
		} else {
			PropertiesUtil.JAVASCRIPT_FLAG = false;
		}
		if (generatorWindow.chkI18n.isSelected()) {
			PropertiesUtil.I18N_FLAG = true;
		} else {
			PropertiesUtil.I18N_FLAG = false;
		}
		// 页面显示的模式
		if (generatorWindow.modelRadioButton.isSelected()) {
			tableVO.setTemplateType(TemplateType.MODEL);
		}
		if (generatorWindow.innerRadioButton.isSelected()) {
			tableVO.setTemplateType(TemplateType.INNER);
		}

		if (!StringUtil.isNullOrEmpty(tipMessages)) {
			JOptionPane.showMessageDialog(null, tipMessages, "温馨提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			GeneratorOneTable generator = new GeneratorOneTable();
			generator.generatorOneTable(tableVO);
			JOptionPane.showMessageDialog(null, "生成代码成功，请刷新项目查看生成的代码。", "成功提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "生成代码失败，请查看控制的错误信息。", "失败提示", JOptionPane.ERROR_MESSAGE);
		}
	}
}
