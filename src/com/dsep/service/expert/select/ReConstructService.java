package com.dsep.service.expert.select;

import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public interface ReConstructService {

	public abstract List<Object> select(List<OuterExpert> experts,
			List<ExpertSelectionRuleDetail> ruleDetails, EvalBatch evalBatch,
			String currentDisc, Map<String,List<OuterExpert>> makeUpTree);

	public abstract List<Object> makeUpExperts(EvalBatch batch,String discId,
			Map<String, List<OuterExpert>> makeUpMap,
			Map<String, Integer> lackMap);

}
