package com.meta.service.sql.impl;

import java.util.LinkedList;
import java.util.List;

import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;

public class SqlDMLHelperDicImpl extends SqlDMLHelperImpl {
	public SqlDMLHelperDicImpl(MetaAttribute attr) {
		super(attr);
	}
	//字典类型如何处理？作为一个字段？作为两个字段？
	@Override
	public List<MetaDataType> getColumnTypes() {
		List<MetaDataType> columnTypes = new LinkedList<MetaDataType>();
		columnTypes.add(MetaDataType.NVARCHAR2);
		return columnTypes;		
	}
}
