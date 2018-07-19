package com.dsep.dao.dsepmeta.check.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.PubTableConfigDao;
import com.dsep.entity.dsepmeta.PubTabConfig;

public class PubTableConfigDaoImpl extends DsepMetaDaoImpl<PubTabConfig, String> 
	implements PubTableConfigDao{

	@Override
	public String getId(String pubLibId) {
		
		String id = null;
		
		List<PubTabConfig> list  = super.getAll();
		
		if(list.size() >= 0){
			for(PubTabConfig ptc : list){
				String pubId = ptc.getPublibId();
				if(pubId.equals(pubLibId))
					id = ptc.getId();
			}
		}
		
		return id;
	}
	
}
