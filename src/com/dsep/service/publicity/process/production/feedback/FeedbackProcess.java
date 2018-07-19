package com.dsep.service.publicity.process.production.feedback;

import java.util.Map;

import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.feedback.FeedbackResponseService;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackResponseVM;

public abstract class FeedbackProcess {

	/**
	 * 根据不同用户设置查询条件
	 * @param queryResponse
	 */
	protected abstract void setFeedbackCondition(FeedbackResponse queryResponse);
	
	/**
	 * 根据查询条件获取反馈答复的列表
	 * @param queryResponse
	 * @param order_flag
	 * @param orderName
	 * @param pageIndex
	 * @param pageSize
	 * @param feedbackResponseService
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public PageVM<FeedbackResponseVM> getFeedbackResponse(
			FeedbackResponse queryResponse, boolean order_flag,
			String orderName, int pageIndex, int pageSize,
			FeedbackResponseService feedbackResponseService) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		setFeedbackCondition(queryResponse);
		return feedbackResponseService.getFeedbackResponseVM(pageIndex, pageSize, order_flag, orderName, queryResponse);
	}
	
	/**
	 * 获取反馈答复状态的Map
	 * @return
	 */
	public abstract Map<String,String> getResponseStatusMap();
	
	/**
	 * 获取学科的Map
	 * @param disciplineService
	 * @return
	 */
	public abstract Map<String,String> getDiscMap(DisciplineService disciplineService);

	/**
	 * 获取学校的Map，中心使用此函数
	 * @param unitService
	 * @return
	 */
	public abstract Map<String,String> getUnitMap(UnitService unitService);
	
}
