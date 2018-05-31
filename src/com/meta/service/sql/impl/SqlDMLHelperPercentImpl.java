package com.meta.service.sql.impl;

import java.util.LinkedList;
import java.util.List;

import com.meta.dao.AdditionalOperation;
import com.meta.dao.impl.AdditionalOperationPercentImpl;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaPercentData;

public class SqlDMLHelperPercentImpl extends SqlDMLHelperImpl {

	public SqlDMLHelperPercentImpl(MetaAttribute attr) {
		super(attr);
	}

	@Override
	public List<MetaDataType> getColumnTypes() {
		List<MetaDataType> columnTypes = new LinkedList<MetaDataType>();
		//columnTypes.add(MetaDataType.CHAR);
		//columnTypes.add(MetaDataType.INT);
		//columnTypes.add(MetaDataType.VARCHAR2);
		columnTypes.add(MetaDataType.VARCHAR2);
		return columnTypes;
	}

	@Override
	public List<String> getColumnNames() {
		//List<String> columnNames = new LinkedList<String>();
		//变为3个字段：类型（char(1)，'0'-次序，'1'-百分比;缺省'0'），数量（int, 缺省1），本单位值（VARCHAR2(10)，缺省'1'）
		//columnNames.add(MetaPercentData.getTypeColumn(attr.getName()));
		//columnNames.add(MetaPercentData.getNumColumn(attr.getName()));
		//columnNames.add(MetaPercentData.getValueColumn(attr.getName()));		
		//return columnNames;
		return super.getColumnNames();
	}

	/**
	 * 返回值值类型字段名
	 */
	@Override
	public String getColumnName(){
		return MetaPercentData.getValueColumn(attr.getName());
	}
	
	@Override
	public List<AdditionalOperation> getAddOps() {
		//List<AdditionalOperation> addOps = new LinkedList<AdditionalOperation>();
		//AdditionalOperation addOp = new AdditionalOperationPercentImpl(attr.getName());
		//addOps.add(addOp);
		//return addOps;
		return super.getAddOps();
	}

	@Override
	public List<Object> getColumnValues(Object value) {
		/*List<Object> columnValues= new LinkedList<Object>();
		MetaPercentData data = (MetaPercentData) value;//转换成所需的类型
		columnValues.add(data.getType());
		columnValues.add(data.getNum());
		columnValues.add(data.getValue());
		return columnValues;*/
		return super.getColumnValues(value);
	}

	@Override
	public List<Object> getColumnValues(String value) {
		/*List<Object> columnValues= new LinkedList<Object>();
		MetaPercentData data = new MetaPercentData(value);//转换成所需的类型
		columnValues.add(data.getType());
		columnValues.add(data.getNum());
		columnValues.add(data.getValue());
		return columnValues;*/
		return super.getColumnValues(value);
	}	
	
	
}
