package com.dsep.service.expert.evaluation;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalQuestion;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalIndicIdxService {
	/**
	 *  0830在用
	 *  获得指标体系的题目和结果
	 *  展示题目和结果的方法
	 *  命名规范是"show+题目名称(参加QType)+QAndResults+Table"
	 */
	public abstract String showIndicIdxQAndResultsTable(CurrentBatchExpertInfo info);
	
	public abstract String getIndicIdxProg(String batchId, String expertId);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract List<EvalQuestion> getIndicatorIndexQuestions(String discId);
	
	/*public abstract double getAchievementProcess(String expertId,String discId);

	public abstract double getIndexProcess(String expertId, String discId);*/
	
	
	

}
