package com.meta.service.sql.impl;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperDoubleImpl extends SqlDDLHelperImpl {
	
	public SqlDDLHelperDoubleImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public String[] getColumnTypeNames() {
		return new String[]{"NUMBER("+attr.getDataLength()+",2)"};//double类型默认精度小数点后2位
	}
}
