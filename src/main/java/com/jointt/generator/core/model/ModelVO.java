package com.jointt.generator.core.model;

import java.io.Serializable;

/**
 * 定义公共的接口方法
 * 
 * @author yongan.quan
 */
public interface ModelVO extends Serializable {

	public String getClassName();

	public String getPackageName();

	public String getTemplateType();

	public String getTableName();
}
