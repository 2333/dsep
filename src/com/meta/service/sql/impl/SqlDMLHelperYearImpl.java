package com.meta.service.sql.impl;

import java.util.LinkedList;
import java.util.List;

import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;

public class SqlDMLHelperYearImpl extends SqlDMLHelperImpl {

	public SqlDMLHelperYearImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public List<MetaDataType> getColumnTypes() {
		List<MetaDataType> columnTypes = new LinkedList<MetaDataType>();
		columnTypes.add(MetaDataType.VARCHAR2);
		return columnTypes;
	}
	

}
