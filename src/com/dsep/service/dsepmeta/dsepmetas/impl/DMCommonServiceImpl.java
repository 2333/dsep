package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.List;
import java.util.Map;

import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMCommonService;

public class DMCommonServiceImpl extends MetaOper implements DMCommonService{

	@Override
	public List<Map<String, String>> getData(String entityId,
			Map<String, Object> params, String orderPropName, boolean asc,
			int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return super.getData(entityId, params, orderPropName, asc, pageIndex, pageSize);
	}

	@Override
	public List<String[]> getData(String entityId,
			List<String> attrNames, Map<String, Object> params,String sqlCondition,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return super.getData(entityId, attrNames, params, sqlCondition, orderPropName, asc, pageIndex, pageSize);
	}

}
