package com.dsep.dao.dsepmeta.publicity.feedback;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.FeedbackResponse;

public interface FeedbackResponseDao extends DsepMetaDao<FeedbackResponse,String>{
	
	/**
	 * 中心开启反馈时改变反馈数据的状态
	 * @currentRoundId 当前公示轮次的状态
	 * @return
	 */
	public boolean updateWhenCenterBeginFeedback(String currentRoundId);

	/**
	 * 是否所有的反馈收都已给出答复意见
	 * @param unitId
	 * @param feedbackType TODO
	 * @param feedbackRoundId
	 * @return
	 */
	public int getNoAdviceNumber(String unitId, String feedbackType, String feedbackRoundId);
	
	/**
	 * 对于某一采集项的某条数据项可能会有多条反馈数据
	 * 本函数获取那些存在多条反馈数据的数据项
	 * 通过集合函数从反馈源中筛选出这些数据
	 * @param conditionalResponse
	 * @return
	 */
	public List<Map<String,String>> getSameItemResponse(int pageIndex,
			int pageSize,boolean desc, String orderPropName,FeedbackResponse conditionalResponse);
	
	/**
	 * 学校提交反馈答复数据到中心
	 * @param unitId
	 * @param feedbackType TODO
	 * @param currentRoundId
	 * @return
	 */
	public boolean submitFeedbackResponse(String unitId,String feedbackType, String currentRoundId);

	/**
	 * 学校的反馈意见为删除时
	 * @param feedbackResponseId
	 * @param responseItemId
	 * @param entityItemId
	 * @return
	 */
	public int saveDeleteResponse(String feedbackRoundId,
			String entityItemId);
	
	
	public int getSameItemResponseCount(FeedbackResponse queryResponse);
	
	/**
	 * 删除某个类型的反馈数据源
	 * @param feedbackRoundId
	 * @param feedbackType
	 * @return
	 */
	public int deleteFeedbackSource(String feedbackRoundId, String feedbackType);
	
}
