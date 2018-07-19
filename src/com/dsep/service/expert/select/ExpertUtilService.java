package com.dsep.service.expert.select;

import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.util.expert.ExpertEvalCurrentStatus;

/**
 * 处理一些边缘逻辑 比如
 * 获得专家的Email 
 * 筛选条件时获得有哪些学校的专家参评等
 * 
 */
public interface ExpertUtilService {
	/**
	 * 获得专家ID获得邮箱，用于邮件通知
	 */
	public abstract List<String> getEmailAddrs(String expertId)
			throws InstantiationException, IllegalAccessException;

	/**
	 * 用于群发通知邮件
	 */
	public abstract Map<Expert, List<String>> getNotMailEmailAddrs();

	/**
	 * 用于补发邮件
	 */
	public abstract Map<Expert, List<String>> getNotReplyEmailAddrs();

	/*
	 * public abstract List<String> getAttendExpertsUnits() throws
	 * InstantiationException, IllegalAccessException;
	 */
	public abstract String showExpertStatistics(ExpertSelectionRule rule,
			String batchId);
	
	/**
	 * 统计某一学科下，每个学校的某些状态的已选专家人数
	 */
	public List<ExpertNumberGroupByUnitId> countExpertNumbersByConditionsGroupByUnitId(
			String discId, String batchId,
			List<ExpertEvalCurrentStatus> statuses);
}
