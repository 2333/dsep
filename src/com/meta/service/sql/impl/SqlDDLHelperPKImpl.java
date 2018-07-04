package com.meta.service.sql.impl;

import org.apache.commons.lang.StringUtils;

import com.meta.entity.MetaAttribute;

public class SqlDDLHelperPKImpl extends SqlDDLHelperImpl {

	public SqlDDLHelperPKImpl(MetaAttribute attr, String versionId) {
		super(attr, versionId);
	}

	@Override
	public String[] getColumnNames() {		
		if(StringUtils.isEmpty(versionId)){
			return new String[]{super.getColumnNames()[0], "primary key ("+attr.getName().toUpperCase()+")"};
		}else{
			return new String[]{super.getColumnNames()[0], "VERSION_ID", "primary key (VERSION_ID, "+attr.getName().toUpperCase()+")"};
		}		
	}

	@Override
	public String[] getColumnTypeNames() {
		if(StringUtils.isEmpty(versionId)){
			return new String[]{super.getColumnTypeNames()[0],""};
		}else{
			return new String[]{super.getColumnTypeNames()[0],"varchar2(32)", ""};
		}
	}

	@Override
	public String[] getDefaultValueNames() {
		if(StringUtils.isEmpty(versionId)){
			return new String[]{super.getDefaultValueNames()[0],""};
		}else{
			return new String[]{super.getDefaultValueNames()[0],"", ""};			
		}
	}

}
