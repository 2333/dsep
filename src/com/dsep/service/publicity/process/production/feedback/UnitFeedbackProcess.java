package com.dsep.service.publicity.process.production.feedback;

import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.feedback.FeedbackResponseStatus;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;

public class UnitFeedbackProcess extends FeedbackProcess{

	private String unitId;
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public UnitFeedbackProcess(String unitId){
		setUnitId(unitId);
	}
	
	
	@Override
	protected void setFeedbackCondition(FeedbackResponse queryResponse) {
		// TODO Auto-generated method stub
		queryResponse.setProblemUnitId(this.unitId);
	}

	@Override
	public Map<String, String> getResponseStatusMap() {
		// TODO Auto-generated method stub
		Map<String,String> responseMap = new LinkedMap();
		responseMap.put(FeedbackResponseStatus.UNIT.getStatus(),"未提交");
		responseMap.put(FeedbackResponseStatus.SUMBIT.getStatus(), "已提交");
		return responseMap;
	}

	@Override
	public Map<String, String> getDiscMap(DisciplineService disciplineService) {
		// TODO Auto-generated method stub
		return disciplineService.getJoinDisciplineMapByUnitId(this.unitId);
	}

	@Override
	public Map<String, String> getUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return null;
	}

}
