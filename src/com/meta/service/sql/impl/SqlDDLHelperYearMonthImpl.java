package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperYearMonthImpl extends SqlDDLHelperImpl {

	public SqlDDLHelperYearMonthImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"VARCHAR2(6)"};//字段类型默认类型为6位;
	}

}
