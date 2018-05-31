package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaPercentData;

public class SqlDDLHelperPercentImpl extends SqlDDLHelperImpl {

	public SqlDDLHelperPercentImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnNames() {
		/*String[] origColumnNames = super.getColumnNames();
		//变为3个字段：类型（char(1)，'0'-次序，'1'-百分比;缺省'0'），数量（int, 缺省1），本单位值（VARCHAR2(10)，缺省'1'）
		return new String[]{MetaPercentData.getTypeColumn(origColumnNames[0]), 
				MetaPercentData.getNumColumn(origColumnNames[0]), 
				MetaPercentData.getValueColumn(origColumnNames[0])};*/
		return super.getColumnNames();
	}

	@Override
	public String[] getDefaultValueNames() {
		//return new String[]{"default '0' ","default 1 ", "default '1'"};
		return new String[]{"default '1(1)'"};
	}

	@Override
	public String[] getColumnTypeNames() {
		//return super.getColumnTypeNames(attr);
		//return new String[]{"CHAR(1)","INT","VARCHAR2(10)"};
		return new String[]{"VARCHAR2(20)"};
	}

}
