package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperDicImpl extends SqlDDLHelperImpl {

	public SqlDDLHelperDicImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"NVARCHAR2(100)"};//字段类型默认类型为字典项的ID（为VARCHAR2(20)）;
	}

}
