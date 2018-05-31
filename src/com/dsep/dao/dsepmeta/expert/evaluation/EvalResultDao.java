package com.dsep.dao.dsepmeta.expert.evaluation;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;

public interface EvalResultDao extends DsepMetaDao<EvalResult, String> {
	//  0830有用
	public void saveOrUpdate(EvalResult result, EvalQuestion question,
			Boolean isUpdate);
	
	public void submit(String paperId, String expertId);

	/**
	 * 当前设计只根据专家Id和问题Id就可以获得某个Question下的所有Result
	 */
	public List<EvalResult> getResultsByQId(String expertId, String questionId);

	/**
	 * 只根据专家Id和问题类型就可以获得某类Question下的所有Result，0830有用
	 */
	public List<EvalResult> getResultsByQType(String expertId,
			Integer scoretType);

	public List<EvalResult> getAchvResultsBySubQuestionId(String expertId,
			String subQuestionId);

	public abstract void updateUnitRanking(String questionId, String expertId,
			String resultId, String oldPosition, String newPosition);
	
	/**
	 * 根据学科或者门类获得指定类型（末级指标项权重、折算系数、主观项等）的所有打分
	 * @param discId 学科ID
	 * @param catId 门类ID
	 * @param expertType 专家类型，可以设置也可以不考虑
	 * @param indexItemId 末级指标项ID 
	 * @return
	 */
	public abstract List<EvalResult> getSameDiscOrCatValue(String discId,String catId,
			String expertType,String subQuestionId);

	/**
	 * 获得同一学科的所有指定类型（折算系数、末级指标项权重等）的打分
	 * @param discId 学科ID
	 * @param expertType 专家类型，设为null代表不设限制
	 * @param qType 问题类型，指定所要获得的结果类型
	 * @return
	 */
	public abstract List<EvalResult> getSameDiscIndicWtOrIndexValue(String discId,String expertType,int qType);
	
	/**
	 * 获得某一学校某一学科某一指定类型的所有打分
	 * @param subQuestionId 指定打分项类型
	 * @param discId
	 * @param unitId
	 * @return
	 */
	public abstract List<EvalResult> getSameUnitValueBySubQuestionId(String subQuestionId,String discId,String unitId);
}
