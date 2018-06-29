package com.meta.service.sql;

import java.util.List;

import com.meta.dao.AdditionalOperation;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;

public abstract class SqlDMLHelper { 
	protected MetaAttribute attr;
	protected String versionId;
	
	public SqlDMLHelper(MetaAttribute attr){
		this.setAttr(attr);
		this.versionId = "";
	}
	
	public SqlDMLHelper(MetaAttribute attr, String versionId){
		this.setAttr(attr);
		this.versionId = versionId;
	}
	/**
	 * 根据实体属性信息返回其对应的真实字段名
	 * @param attr 属性
	 * @return 字段名，字符串类型
	 */
	public abstract List<String> getColumnNames();
	/**
	 * 根据实体属性信息返回其对应的一个真实字段名，如果对应多个，只返回其中一个核心字段（不同类型含义可能不同，用于支持排序等）
	 * @param attr 属性
	 * @return 字段名，字符串类型
	 */
	public abstract String getColumnName(); 
	/**
	 * 将在界面上显示的对象，转换为与数据库字段对应的数据类型
	 * @param attr
	 * @param values
	 * @return
	 */
	public abstract List<Object> getColumnValues(Object value);
	/**
	 * 将在界面上显示的对象，转换为与数据库字段对应的数据类型
	 * @param attr
	 * @param values
	 * @return
	 */
	public abstract List<Object> getColumnValues(String value);
	/**
	 * 根据实体属性信息返回其对应的真实数据类型
	 * @param attr 属性
	 * @return 属性类型枚举值
	 */
	public abstract List<MetaDataType> getColumnTypes(); 
	/**
	 * 根据实体属性信息返回其对应的真实数据类型
	 * @param attr 属性
	 * @return 需要执行的附加动作
	 */
	public abstract List<AdditionalOperation> getAddOps();
	
	public MetaAttribute getAttr() {
		return attr;
	}
	public void setAttr(MetaAttribute attr) {
		this.attr = attr;
	}
}
