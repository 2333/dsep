package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperYearImpl extends SqlDDLHelperImpl {
	public SqlDDLHelperYearImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"VARCHAR2(4)"};//字段类型默认类型为4位;
	}

}
