package com.dsep.dao.dsepmeta.calculate;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.IndexScore;

public interface IndexScoreDao extends DsepMetaDao<IndexScore, String> {
	
	/**
	 * 获得某学校后学科的所有一二级指标得分
	 * @param discId
	 * @param unitId
	 * @param indexId
	 * @param indexLevel
	 * @return
	 */
	public abstract List<IndexScore> getIndexScore(String discId,String unitId,String indexId,String indexLevel,String orderPropName, boolean asc, int pageIndex, int pageSize);
}
