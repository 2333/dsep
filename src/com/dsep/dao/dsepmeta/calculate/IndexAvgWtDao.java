package com.dsep.dao.dsepmeta.calculate;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.IndexAvgWt;

public interface IndexAvgWtDao extends DsepMetaDao<IndexAvgWt,String>{
	/**
	 * 获得指定的末级指标权重平均值（按学科或者门类）
	 * @param indexItemId 末级指标项ID
	 * @param discId 学科ID
	 * @return
	 */
	public abstract List<IndexAvgWt> getIndexAvgWtByIndexAndDisc(String indexItemId,
			String discId);
	
}
