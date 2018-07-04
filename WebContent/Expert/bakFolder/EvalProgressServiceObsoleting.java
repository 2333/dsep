package com.dsep.service.expert.evaluation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.util.expert.eval.QType;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalProgressServiceObsoleting {
	/*
	 * 初始化专家的打分流程，重要接口！
	 
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void initExpertEvalProgress(String expertId, String discId);

	
	 * 显示专家的打分流程table
	 
	public abstract String getProgressTableContent(String expertId,
			String discId);

	
	 * 在evalService的saveResults方法中调用，saveResults上加了事务，故此处不用写事务
	 * 
	 * 前端页面按下保存分数按钮时要触发的监听器，根据变动的题目答案数量修改打分进度
	 * 返回值是：如果本页全部打完时下一个页面的路由
	 * 
	 * 在前端页面中，除了成果打分要传递questionId外，
	 * 其余的打分只需要传递打分类型就可以找出下一道题目。 
	 
	public abstract String listenerForSaveButton(String expertId,
			String questionId, Integer type, int alteredAnswerNumber);

	
	 * 获取下一道题目的路由
	 
	public String getNextQuestionRoute(String expertId, String questionId,
			Integer type);

	
	 * 获取上一道题目的路由
	 
	public String getPrevQuestionRoute(String expertId, String questionId,
			QType type);

	// 以下五个getXXPCT返回打分完成度百分比
	public abstract String getIndicatorIndexPCT(String expertId);

	public abstract String getIndicatorWeightPCT(String expertId);

	public abstract String getAchievementPCT(String expertId);

	public abstract String getReputationPCT(String expertId);

	public abstract String getRankingPCT(String expertId);*/
}
