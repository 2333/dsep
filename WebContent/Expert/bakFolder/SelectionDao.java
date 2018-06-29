package com.dsep.dao.dsepmeta.expert.selection;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public interface SelectionDao extends Dao<OuterExpert, String> {
	public abstract List<Expert> selectExperts(List<Object> disciplines, List<Object> units,
			List<OuterExpert> list,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails);

	public abstract List<OuterExpert> sequenceExperts(
			List<OuterExpert> experts);
	
	public abstract List<OuterExpert> rangeExperts(
			List<OuterExpert> experts);
}
