package com.dsep.dao.dsepmeta.calculate.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.IndexScoreDao;
import com.dsep.entity.dsepmeta.IndexScore;

public class IndexScoreDaoImpl extends 
DsepMetaDaoImpl<IndexScore,String> implements IndexScoreDao{
	private String tableName=super.getTableName("O", "CAL_IndexScore");
	@Override
	public List<IndexScore> getIndexScore(String discId, String unitId,
			String indexId, String indexLevel,String orderPropName, boolean asc, int pageIndex, int pageSize) {
		
		StringBuilder strBud=new StringBuilder("select * from "+tableName);
		List<Object> params=new LinkedList<Object>();
		List<String> conditions=new LinkedList<String>();
		
		if(StringUtils.isNotBlank(discId)){
			conditions.add("DISC_ID");
			params.add(discId);
		}
		if(StringUtils.isNotBlank(unitId)){
			conditions.add("UNIT_ID");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(indexId)){
			conditions.add("INDEX_ID");
			params.add(indexId);
		}
		if(StringUtils.isNotBlank(indexLevel)){
			conditions.add("INDEX_LEVEL");
			params.add(indexLevel);
		}
		 
		strBud.append(super.sqlAndConditon(conditions));
		Object[] values=params.toArray();
		
		List<IndexScore> result;
		result=super.sqlPage(strBud.toString(), pageIndex, pageSize, asc, orderPropName, values);
		
		return result;
	}

}
