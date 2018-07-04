package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperFileImpl extends SqlDDLHelperImpl{

	public SqlDDLHelperFileImpl(MetaAttribute attr) {
		super(attr);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String[] getDefaultValueNames() {
		return new String[]{"default ''"};
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"VARCHAR2(200)"};
	}

	

}
