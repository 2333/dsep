package com.meta.service.sql.impl;

import org.apache.commons.lang.StringUtils;

import com.meta.entity.MetaAttribute;
import com.meta.service.sql.SqlDDLHelper;

public class SqlDDLHelperImpl extends SqlDDLHelper {
	public SqlDDLHelperImpl(MetaAttribute attr) {
		super(attr);
	}
	public SqlDDLHelperImpl(MetaAttribute attr, String versionId) {
		super(attr, versionId);
	}

	@Override
	public String[] getColumnNames() {		
		return new String[]{attr.getName().toUpperCase()};
	}

	@Override
	public String[] getDefaultValueNames() {
		StringBuilder defaultValue= new StringBuilder("");
		
		if(!StringUtils.isBlank(attr.getDefaultValue())){
			defaultValue.append("default "+attr.getDefaultValue().trim());
		}
		if(attr.getIsNull().equals("0")){
			defaultValue.append(" not null ");
		}
		return new String[]{defaultValue.toString()};
	}

	@Override
	public String[] getColumnTypeNames() {
		StringBuilder columnType=new StringBuilder(attr.getDataType());
		if(attr.getDataLength()>0){
			columnType.append("("+attr.getDataLength()+")");
		}
		return new String[]{columnType.toString()};
	}
	
}
