package com.dsep.service.expert.evaluation;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalAchvVM;


public interface EvalAchvService {

	/**
	 * 通过专家的信息和具体成果子项id即subQuestion
	 * 展示专家为摸个具体成果子项打分的界面，包括显示已经打过的分数
	 */
	public PageVM<EvalAchvVM> getAchvResults(CurrentBatchExpertInfo info,
			String subQuestionId, int pageIndex, int pageSize);

	/**
	 * 获取专家对于某个题目的打分进度
	 */
	public abstract String getAchvProg(String batchId, String expertId,
			String subQuestionId);
	/**
	 * 通过专家实体的
	 * 1、专家类别（如：行业专家or学术专家）
	 * 2、学科门类（如：JSJ计算机，通过专家的discId获得） 
	 * 3、题目类型（此处为成果打分）
	 * 来获取该专家打分题目的题干信息
	 */
	/*public abstract List<EvalQuestionVM> getAchvQs(CurrentBatchExpertInfo info,
			int qType, String subQuestionId);*/
}
