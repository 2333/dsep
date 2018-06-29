package com.meta.service.sql.impl;

import java.util.LinkedList;
import java.util.List;

import com.meta.dao.AdditionalOperation;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;
import com.meta.service.sql.SqlDMLHelper;

public class SqlDMLHelperImpl extends SqlDMLHelper {

	public SqlDMLHelperImpl(MetaAttribute attr) {
		super(attr);
	}
	public List<MetaDataType> getColumnTypes() {
		List<MetaDataType> columnTypes = new LinkedList<MetaDataType>();
		columnTypes.add(MetaDataType.getDataType(attr.getDataType()));
		return columnTypes;
	}
	public List<String> getColumnNames() {
		List<String> columnNames = new LinkedList<String>();
		columnNames.add(attr.getName());
		return columnNames;
	}

	public String getColumnName(){
		return attr.getName();
	}
	public List<Object> getColumnValues(Object value) {
		List<Object> columnValues = new LinkedList<Object>();
		columnValues.add(value);
		return columnValues;
	}
	
	public List<Object> getColumnValues(String value) {
		List<Object> columnValues = new LinkedList<Object>();
		columnValues.add(value);
		return columnValues;
	}
	
	public List<AdditionalOperation> getAddOps() {
		return null;
	}

	
}
