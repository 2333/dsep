package com.dsep.dao.dsepmeta.expert.evaluation;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.Expert;

public interface EvalQuestionDao extends DsepMetaDao<EvalQuestion, String> {
	/**
	 * 删除某个批次下所有的元question，此接口用于生成实际question使用
	 * 当生成实际question之后，应该删除元question
	 */
	public abstract void deleteMetaQuestionsForOneBatch(String batchId);
	
	/**
	 * 
	 * @param Id
	 * @return
	 */
	public abstract EvalQuestion getEvalQuestionById(String id);

	/**
	 * 专家只能打自己discId的题目
	 * 另外因为成果打分有多项：团队、学生等
	 * 要根据subQuestionId才能区分出团队、学生等的差别
	 * 0830在用
	 */
	public abstract List<EvalQuestion> getAchvQsByPage(String paperId, String discId,
			String subQuestionId, int pageIndex, int pageSize);

	/**
	 * 一次性拿到所有的某个成果打分的题目，不分页，有别于getAchvQsByPage
	 */
	public abstract List<EvalQuestion> getAllAchvQs(String paperId, String discId,
			String subQuestionId);

	public abstract List<EvalQuestion> getRepuQs(String paperId, String discId, Boolean includeMetaQ);

	//0830 要写meta和非meta
	/**
	 * 
	 * @param includeMetaQ 如果includeMetaQ is true，会查找出包括元Q的所有Qs 
	 * 在生成题目的时候，最初有一个占位Q指明了生成题目的元信息，在生成完之后这个元Q没有被删除
	 * 它的特征是isMeta字段是true
	 * 
	 */
	public abstract List<EvalQuestion> getIndicIdxQs(String paperId, String discId, Boolean includeMetaQ);

	public abstract List<EvalQuestion> getIndicWtQs(String paperId, String discId, Boolean includeMetaQ);

	public abstract List<EvalQuestion> getAllMetaQuestionsByPaperId(String paperId);

	public abstract EvalQuestion getQuestionByIndexItemId(String IndexItemId,
			String expertTypeId);

	// 0830在用
	public abstract List<EvalQuestion> getIndexQuestions(String expertId,
			List<String> indexItemIds);

	// 现在Question是固化了的，优秀毕业生可能有10001、10002和10006三个问题，其实都是针对优秀毕业生的
	// 这三个在路由上应当显示为一个问题
	public abstract List<EvalQuestion> getDistinctQuestionsByPaper(EvalPaper evalPaper,
			Expert expert);

	public abstract List<EvalQuestion> getQuestionsByType(List<String> scoreTypes);
}
