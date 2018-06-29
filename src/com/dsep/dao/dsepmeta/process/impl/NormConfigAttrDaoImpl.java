package com.dsep.dao.dsepmeta.process.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.process.NormConfigAttrDao;
import com.dsep.entity.dsepmeta.NormConfigAttr;

public class NormConfigAttrDaoImpl extends DsepMetaDaoImpl<NormConfigAttr, String>
implements NormConfigAttrDao{

	String tableName=super.getTableName("O", "NORM_CONFIGATTR");
	@Override
	public List<String> getNormField(String entityId) {
		String sql="select FIELD_NAME from "+tableName + " where ENTITY_ID='"+entityId+"'";
		List<String> result = super.GetShadowResult(sql);
		return result;
	}
	@Override
	public String getNormDataSet(String entityId, String fieldName) {
		StringBuilder sql=new StringBuilder("select VALUE from "+tableName);
		
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		if(StringUtils.isNotBlank(entityId)){
			conditionColumns.add("ENTITY_ID");
			params.add(entityId);
		}
		
		if(StringUtils.isNotBlank(fieldName)){
			conditionColumns.add("FIELD_NAME");
			params.add(fieldName);
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		Object[] values=params.toArray();
		List<String> list=super.GetShadowResult(sql.toString(), values);
		
		return list.get(0);
	}

}
