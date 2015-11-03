package com.jointt.generator.core.window;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GeneratorWindow extends JFrame {

	private static final long serialVersionUID = 6689527129096807864L;
	public JPanel parentPanel;
	public JLabel lblPackageName;
	public JTextField txtPackageName;
	public JLabel lblEntityName;
	public JTextField txtEntityName;
	public JLabel lblTableName;
	public JTextField txtTableName;
	public JCheckBox chkDao;
	public JCheckBox chkDaoXml;
	public JCheckBox chkService;
	public JCheckBox chkServiceImpl;
	public JCheckBox chkController;
	public JCheckBox chkModel;
	public JCheckBox chkJsp;
	public JCheckBox chkJavaScript;
	public JCheckBox chkI18n;
	public JRadioButton modelRadioButton;
	public JRadioButton innerRadioButton;

	public GeneratorWindow() {
		parentPanel = new JPanel();
		setContentPane(parentPanel);
		parentPanel.setPreferredSize(new Dimension(400, 350));
		parentPanel.setLayout(new GridLayout(4, 1));

		// 基本信息
		lblPackageName = new JLabel("包名（小写）：");
		txtPackageName = new JTextField();
		lblEntityName = new JLabel("实体类名（首字母大写）：");
		txtEntityName = new JTextField();
		lblTableName = new JLabel("表名：");
		txtTableName = new JTextField();
		// 选择要生成的类
		chkDao = new JCheckBox("Dao");
		chkDao.setSelected(true);
		chkDaoXml = new JCheckBox("DaoXml");
		chkDaoXml.setSelected(true);
		chkService = new JCheckBox("Service");
		chkService.setSelected(true);
		chkServiceImpl = new JCheckBox("ServiceImpl");
		chkServiceImpl.setSelected(true);
		chkController = new JCheckBox("Controller");
		chkController.setSelected(true);
		chkModel = new JCheckBox("Model");
		chkModel.setSelected(true);
		chkJsp = new JCheckBox("Jsp");
		chkJsp.setSelected(true);
		chkJavaScript = new JCheckBox("Js");
		chkJavaScript.setSelected(true);
		chkI18n = new JCheckBox("I18N");
		chkI18n.setSelected(true);
		// 页面展示风格
		ButtonGroup pageStyleButtonGroup = new ButtonGroup();
		modelRadioButton = new JRadioButton("弹出窗口风格(Model)");
		modelRadioButton.setSelected(true);
		innerRadioButton = new JRadioButton("内嵌页风格");
		pageStyleButtonGroup.add(modelRadioButton);
		pageStyleButtonGroup.add(innerRadioButton);
		// 按钮
		JButton btnGenerator = new JButton("生成");
		btnGenerator.addActionListener(new GeneratorActionListener(this));
		JButton btnExit = new JButton("退出");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel baseInfoPanel = new JPanel();
		baseInfoPanel.setLayout(new GridLayout(3, 2));
		baseInfoPanel.setBorder(BorderFactory.createTitledBorder("基本信息:"));
		baseInfoPanel.add(lblTableName);
		baseInfoPanel.add(txtTableName);
		baseInfoPanel.add(lblPackageName);
		baseInfoPanel.add(txtPackageName);
		baseInfoPanel.add(lblEntityName);
		baseInfoPanel.add(txtEntityName);
		parentPanel.add(baseInfoPanel);

		JPanel generatorFilePanel = new JPanel();
		generatorFilePanel.setLayout(new GridLayout(3, 3));
		generatorFilePanel.setBorder(BorderFactory.createTitledBorder("选择要生成的文件:"));
		generatorFilePanel.add(chkDao);
		generatorFilePanel.add(chkDaoXml);
		generatorFilePanel.add(chkService);
		generatorFilePanel.add(chkServiceImpl);
		generatorFilePanel.add(chkController);
		generatorFilePanel.add(chkModel);
		generatorFilePanel.add(chkJsp);
		generatorFilePanel.add(chkJavaScript);
		generatorFilePanel.add(chkI18n);
		parentPanel.add(generatorFilePanel);

		JPanel pageStylePanel = new JPanel();
		pageStylePanel.setLayout(new GridLayout(1, 2));
		pageStylePanel.setBorder(BorderFactory.createTitledBorder("页面展示风格:"));
		pageStylePanel.add(modelRadioButton);
		pageStylePanel.add(innerRadioButton);
		parentPanel.add(pageStylePanel);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		btnPanel.add(btnGenerator);
		btnPanel.add(btnExit);
		parentPanel.add(btnPanel);

		setTitle("宝胜国际代码生成器[单表模型]");
		setVisible(true);
		setDefaultCloseOperation(3);
		setSize(new Dimension(500, 400));
		setLocationRelativeTo(getOwner());
	}

	public static void main(String[] paramArrayOfString) {
		try {
			new GeneratorWindow().pack();
		} catch (Exception localException) {
			System.out.println(localException.getMessage());
		}
	}
}
