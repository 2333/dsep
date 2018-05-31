package com.dsep.dao.dsepmeta.expert.evaluation;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalProgress;

public interface EvalProgressDao extends DsepMetaDao<EvalProgress, String> {
	/*
	 * 两种拿到题目的方式（其一）：为成果打分准备
	 */
	public abstract EvalProgress getEvalProgressByQuestionId(String expertId, String questionId);

	/*
	 * 两种拿到题目的方式（其二）：为指标打分、声誉打分和排名打分准备
	 */
	public abstract List<EvalProgress> getEvalProgressByQuestionType(String expertId,
			Integer questionType);

	/* 因为打分是有先后顺序的，如：必须先打指标体系才能打成果分
	 * 只有前项完成度为100%时后项才能打分，这里找出可以打分的earliest后项
	 * 该方法是建立在id是有序的基础上的
	 */
	public abstract Integer getEarliestQ(String expertId);

	public abstract EvalProgress getBySequ(String expertId, Integer sequ);

	public abstract void updateQuestionProgress(String expertId, String questionId,
			Integer questionType, Integer alteredNumber);
	
	// 强制刷新progress表中数据，newSetNumber是从结果表中计算得来的
	public abstract void flushQuestoinProgress(String expertId, String questionId,
			Integer questionType, Integer newSetNumber);
	
}
