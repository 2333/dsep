package com.meta.dao;

import java.util.Map;

/**
 * 用于对Dao取回的以Map存储的key（字段名）-value（字段值）形式记录的数据进行后续处理
 * 
 * @author thbin
 * @version 2014-02-26
 */
public abstract class AdditionalOperation {
	/**
	 * 字段名，不是数据库真实字段名，而是MetaAttribute中定义的原始字段名
	 */
	protected String columnName;

	public AdditionalOperation(String columnName) {
		this.columnName = columnName;
	}

	public abstract void execObject(Map<String, Object> row);
	public abstract void exec(Map<String, String> row);

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
