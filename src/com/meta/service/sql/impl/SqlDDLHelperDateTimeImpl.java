package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperDateTimeImpl extends SqlDDLHelperImpl {
	public SqlDDLHelperDateTimeImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"DATE"};//oracle中日期时间类型直接使用Date
	}
}
