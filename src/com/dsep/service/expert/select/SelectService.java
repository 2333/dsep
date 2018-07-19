package com.dsep.service.expert.select;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.expert.Expert;

@Transactional(propagation = Propagation.SUPPORTS)
public interface SelectService {
	/**
	 * 根据规则遴选专家 
	 * 这个接口实现中是
	 * 1/清空某批次下的专家库
	 * 2/获得各种数据（如遴选的某个学科的专家，遴选的规则）
	 * 3/保存最后的结果
	 * 
	 * 真正的遴选工作交由SelectionStrategyDelegationService的实现类完成
	 * 
	 * batchId在补选的时候有用，正常遴选的时候传递null
	 */
	public abstract void select(String ruleId, String batchId,
			Boolean isReSelect) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 遴选之前先清空某批次下的专家库，返回的是没有被删除的专家（如：已经发送了邮件的）
	 * 注意：此transaction直接提交，即便遴选过程中出现异常没有完成遴选，清空动作不可逆
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract List<Expert> deleteByBatch(String batchId);
	
	/**
	 * 遴选规模太大，不能放在一个transaction中
	 * 此处每完成一个学科，就进行专家写入操作并记录当前循环到的学科
	 * 如果碰到异常下次就从最后记录的学科开始遴选
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void saveExpertsAndCurrentDisc(
			List<Expert> list, String currentDiscId);
	
	/**
	 * 设置最近一次使用的规则，用于统计和补选
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void setLastUsedRule(String ruleId, String batchId);
	
}
