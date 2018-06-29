package com.dsep.dao.dsepmeta.expert.batch.impl;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.batch.EvalBatchDao;
import com.dsep.entity.expert.EvalBatch;

public class EvalBatchDaoImpl extends DsepMetaDaoImpl<EvalBatch, String>
		implements EvalBatchDao {
	private String getTableName() {
		return super.getTableName("E", "EVAL_BATCH");
	}

	@Override
	public void modifyEvalBatchStatus(Integer status, String id) {
		String sql = "update " + getTableName() + " set CURRENT_STATUS="
				+ status + " where ID='" + id + "'";
		super.sqlBulkUpdate(sql);
	}

}
