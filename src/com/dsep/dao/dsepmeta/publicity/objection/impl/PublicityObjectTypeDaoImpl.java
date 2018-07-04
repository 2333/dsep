package com.dsep.dao.dsepmeta.publicity.objection.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.objection.PublicityObjectTypeDao;
import com.dsep.entity.dsepmeta.PublicityObjectType;

public class PublicityObjectTypeDaoImpl extends DsepMetaDaoImpl<PublicityObjectType,String>
	implements PublicityObjectTypeDao{

	@Override
	public List<PublicityObjectType> getObjectTypeListByEntityId(String entityId) {
		// TODO Auto-generated method stub
		String hql = "from PublicityObjectType where objectEntityId = ?";
		Object[] values = new Object[1];
		values[0] = entityId;
		return super.hqlFind(hql, values);
	}
	
}
