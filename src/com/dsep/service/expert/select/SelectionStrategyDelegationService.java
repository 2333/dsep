package com.dsep.service.expert.select;

import java.util.List;

import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

/**
 * 1 该接口旨在实现策略模式，继承此接口的类可以实现自己的遴选算法
 * 2 继承该接口的类是一个代理类，代理了SelectionServiceImpl中的遴选，
 * SelectionServiceImpl只是负责收集数据
 */
public interface SelectionStrategyDelegationService {
	public abstract List<Expert> select(
			List<OuterExpert> experts,
			List<ExpertSelectionRuleDetail> ruleDetails, 
			EvalBatch evalBatch,
			List<ExpertNumberGroupByUnitId> selectedNumberGroupByUnitId,
			String currentDisc);
}
