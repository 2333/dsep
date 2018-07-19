package com.dsep.dao.dsepmeta.expert.batch;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalBatch;

public interface EvalBatchDao extends DsepMetaDao<EvalBatch, String> {
	public abstract void modifyEvalBatchStatus(Integer status, String id);
}
