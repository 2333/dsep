package com.dsep.dao.dsepmeta.process;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.NormResult;

public interface NormResultDao extends DsepMetaDao<NormResult,String>{

	/**
	 * 根据entityId获得某一个实体的规范结果
	 * @param entityId
	 * @return
	 */
	public abstract List<String> oneEntityIdData(String entityName,String fieldName);
	/**
	 * 某一个entity下的规范结果
	 * @param entityId
	 * @return
	 */
	public abstract List<NormResult> oneEntityResult(String entityId);
	
	public abstract List<NormResult> oneEntityNotNorm(String entityId);
}
