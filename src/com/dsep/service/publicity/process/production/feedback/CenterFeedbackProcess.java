package com.dsep.service.publicity.process.production.feedback;

import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.feedback.FeedbackResponseStatus;
import com.dsep.entity.enumeration.feedback.ResponseType;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;

public class CenterFeedbackProcess extends FeedbackProcess{

	@Override
	protected void setFeedbackCondition(FeedbackResponse queryResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getResponseStatusMap() {
		// TODO Auto-generated method stub
		Map<String,String> responseMap = new LinkedMap();
		responseMap.put(FeedbackResponseStatus.SUMBIT.getStatus(),"学校已提交");
		/*responseMap.put(FeedbackResponseStatus.FINISH.getStatus(),"已处理");*/
		return responseMap;
	}

	@Override
	public Map<String, String> getDiscMap(DisciplineService disciplineService) {
		// TODO Auto-generated method stub
		return disciplineService.getAllEvalDiscMap();
	}

	@Override
	public Map<String, String> getUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return unitService.getAllEvalUnitMap();
	}

}
