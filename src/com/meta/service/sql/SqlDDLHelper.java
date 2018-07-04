package com.meta.service.sql;

import com.meta.entity.MetaAttribute;

public abstract class SqlDDLHelper {	
	protected MetaAttribute attr;
	protected String versionId;	
	
	public SqlDDLHelper(MetaAttribute attr){
		this.attr = attr;
		this.versionId = "";
	}
	
	public SqlDDLHelper(MetaAttribute attr, String versionId){
		this.attr = attr;
		this.versionId = versionId;
	}
	/**
	 * 获得建表的字段名
	 * @param attr
	 * @return
	 */
	public abstract String[] getColumnNames();
	/**
	 * 获得建表所需的字段类型
	 * @param attr
	 * @return
	 */
	public abstract String[] getColumnTypeNames();
	/**
	 * 获得建表的所需的缺省值和是否为null
	 * @param attr
	 * @return
	 */
	public abstract String[] getDefaultValueNames();
	public MetaAttribute getAttr() {
		return attr;
	}
	public void setAttr(MetaAttribute attr) {
		this.attr = attr;
	}
	
}
