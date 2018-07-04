package com.dsep.util.briefsheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.service.dsepmeta.dsepmetas.DMCommonService;

public class DataFetcher {
	private static DMCommonService dmCommonService;
	
	public static Object fetchData(Map<String, String> entries,
								String unitId, String discId, String teacherId){
		Map<String,Object> dataModel = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (unitId == null || !unitId.equals("")) {
			params.put("UNIT_ID", unitId);		// 单位代码
		}
		if (discId == null || !discId.equals("")) {
			params.put("DISC_ID", discId);		// 学科代码
		}
		if (teacherId == null || !teacherId.equals("")) {
			params.put("CGSSR_ID", teacherId);	// 教师代码
		}
		if(entries != null){
			for(java.util.Map.Entry<String, String> titleEntry : entries.entrySet()){
				List<Map<String,String>> gridData = dmCommonService.getData(titleEntry.getValue(), params, null, true, 0, 15);
				dataModel.put(titleEntry.getKey(), gridData);
			}
		}
		return dataModel;
	}
	
	public DMCommonService getDmCommonService() {
		return dmCommonService;
	}
	public void setDmCommonService(DMCommonService dmCommonService) {
		this.dmCommonService = dmCommonService;
	}
}
