package com.jointt.generator.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static Configuration configuration = null;
	private static Template template = null;

	/**
	 * 渲染模板字符串。
	 * 
	 * @param templateString
	 *            模板字符串
	 * @param model
	 *            数据源
	 * @return 渲染后的字符串
	 */
	public static String rendereString(String templateString, Map<String, ?> model) {
		try {
			StringWriter result = new StringWriter();
			Template t = new Template("name", new StringReader(templateString), new Configuration());
			t.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 渲染Template文件.
	 * 
	 * @param template
	 *            Template文件
	 * @param model
	 * @return 渲染后的Template文件
	 * @since 1.0
	 */
	public static String renderTemplate(Template template, Object model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回字符流
	 * 
	 * @param filePath
	 * @param fileName
	 * @param model
	 * @return
	 */
	public static InputStream renderTemplate(String filePath, String fileName, Map<String, ?> model) {
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		try {
			configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File(filePath)); // "D:/dev/data.xml"
			template = configuration.getTemplate(fileName, DEFAULT_ENCODING);
			OutputStreamWriter out = new OutputStreamWriter(baout, DEFAULT_ENCODING);
			template.process(model, out);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new ByteArrayInputStream(baout.toByteArray());
	}

	public static Template getTemplate(String filePath, String fileName) {
		try {
			configuration = new Configuration();
			// 设置模板的根目录,加载模板的一种设置共3中
			configuration.setDirectoryForTemplateLoading(new File(filePath));
			// 从Configuration实例中获取模板实例,这里存储的都是解析过的模板内容
			template = configuration.getTemplate(fileName, DEFAULT_ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return template;
	}

	public static Template getTemplate(Class<?> clases, String filePath, String fileName) {
		try {
			configuration = new Configuration();
			// 设置模板的根目录,加载模板的一种设置共3中
			configuration.setClassForTemplateLoading(clases, filePath);
			// 从Configuration实例中获取模板实例,这里存储的都是解析过的模板内容
			template = configuration.getTemplate(fileName, DEFAULT_ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	public static void main(String[] args) {
		Template template2 = getTemplate(FreeMarkerUtil.class.getClass(), "/demo/", "demo.ftl");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "quanyongan");
		List<User> list = new ArrayList<User>();
		list.add(new User("1", "quanyongan", "guangzhou", "男"));
		list.add(new User("2", "xx", "guangzhou", "女"));
		list.add(new User("3", "rrrr", "xu", "女"));
		list.add(new User("4", "ggg", "fd", "男"));
		map.put("dataList", list);
		String r = renderTemplate(template2, map);
		System.out.println(r);
	}

}
