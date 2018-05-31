package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;
import java.util.Map;

public interface DMCommonService {
	
	abstract public List<Map<String,String>> getData(String entityId, Map<String, Object> params, 
			String orderPropName, boolean asc, 
			int pageIndex, int pageSize);
	
	abstract public List<String[]> getData(String entityId,List<String>attrNames,
			Map<String, Object> params,String sqlCondition, String orderPropName, boolean asc, 
			int pageIndex, int pageSize);
}
