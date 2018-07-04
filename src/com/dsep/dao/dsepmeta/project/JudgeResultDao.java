package com.dsep.dao.dsepmeta.project;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.JudgeResult;

public interface JudgeResultDao extends DsepMetaDao<JudgeResult,String>{
	
	public abstract JudgeResult getResultByItemId(String itemId);

	public abstract JudgeResult getResultById(String parameter);

}
