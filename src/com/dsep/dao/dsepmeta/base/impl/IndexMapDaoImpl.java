package com.dsep.dao.dsepmeta.base.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.IndexMapDao;
import com.dsep.entity.dsepmeta.IndexMap;

public class IndexMapDaoImpl extends DsepMetaDaoImpl<IndexMap, String> implements IndexMapDao{
	private String getTableName(){
		return super.getTableName("X", "INDEX_MAP");
	}
	@Override
	public List<IndexMap> getIndexMapsByIndexId(String indexId) {
		// TODO Auto-generated method stub
		String hql = "from IndexMap where index.id = ? ";
		return super.hqlFind(hql, new Object[]{indexId});
	}
	

}
