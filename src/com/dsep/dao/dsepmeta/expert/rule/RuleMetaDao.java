package com.dsep.dao.dsepmeta.expert.rule;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.ExpertSelectionRuleMeta;

public interface RuleMetaDao extends DsepMetaDao<ExpertSelectionRuleMeta, String> {
	public abstract List<ExpertSelectionRuleMeta> getAll();
}
