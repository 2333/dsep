package com.dsep.dao.dsepmeta.calculate.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.IndexAvgWtDao;
import com.dsep.entity.dsepmeta.IndexAvgWt;

public class IndexAvgWtDaoImpl extends 
DsepMetaDaoImpl<IndexAvgWt,String> implements IndexAvgWtDao{
	private String getIndexAvgWtTableName(){
		return super.getTableName("O", "INDEX_AVG_WT");
	}
	@Override
	public List<IndexAvgWt> getIndexAvgWtByIndexAndDisc(String indexItemId,
			String discId){
		String sql = " select * from " + getIndexAvgWtTableName() + " t "
				+ " where t.DISC_ID='" + discId + "' and t.INDEX_ID='"
				+ indexItemId + "'";
		return super.sqlFind(sql);
	}
	
}
