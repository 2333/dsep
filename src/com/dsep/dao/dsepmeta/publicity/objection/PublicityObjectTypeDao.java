package com.dsep.dao.dsepmeta.publicity.objection;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.PublicityObjectType;

public interface PublicityObjectTypeDao extends DsepMetaDao<PublicityObjectType,String>{
	
	public List<PublicityObjectType> getObjectTypeListByEntityId(String entityId); 
}
