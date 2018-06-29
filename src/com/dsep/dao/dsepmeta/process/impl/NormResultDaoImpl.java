package com.dsep.dao.dsepmeta.process.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.process.NormResultDao;
import com.dsep.entity.dsepmeta.NormResult;

public class NormResultDaoImpl extends DsepMetaDaoImpl<NormResult,String>
implements NormResultDao{

	String tableName = super.getTableName("O", "NORM_RESULT");
	@Override
	public List<String> oneEntityIdData(String tableName,String fieldName) {
		
		String sql="select distinct "+fieldName+" from "+tableName;
		List<String> result=super.GetShadowResult(sql);
		return result;
	}

	@Override
	public List<NormResult> oneEntityResult(String entityId) {
		
		String sql="select * from "+tableName+" where ENTITY_ID='"+entityId+"'";
		List<NormResult> result=super.sqlFind(sql);
		return result;
	}

	@Override
	public List<NormResult> oneEntityNotNorm(String entityId) {
		String sql="select * from "+tableName+" where ENTITY_ID='"+entityId+"' and NORM_VALUE='未规范'";
		List<NormResult> result=super.sqlFind(sql);
		return result;
	}

}
