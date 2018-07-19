package com.dsep.service.expert.evaluation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalRepuVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalRankService {
	/*// 初始化所有学校的排名
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void initAllUnitsRanking(String questionId, String expertId);
*/
	// 从某个排名移动到另一个排名
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void updateAUnitRanking(String questionId,
			String resultId, String expertId, String oldPosition, String newPosition);
	
	// 获得所有学校的排名,按排名顺序返回
	public abstract PageVM<EvalRepuVM> getAllUnitsRanking(
			String questionId, String expertId);
}
