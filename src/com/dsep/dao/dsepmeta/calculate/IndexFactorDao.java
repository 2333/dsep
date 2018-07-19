package com.dsep.dao.dsepmeta.calculate;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.ConvFactorValue;

public interface IndexFactorDao extends DsepMetaDao<ConvFactorValue,String> {
	
	public abstract Map<String,Double> getIndexFactor(String indexName,String catId);

	/**
	 * 查找指定的折算系数打分平均值
	 * @param discId 学科ID
	 * @param indexContent 指标项内容，若设为null或者""则查找指定学科的所有折算系数
	 * @return
	 */
	public abstract List<ConvFactorValue> getCFValueByContentAndDisc(String discId, 
			String indexContent);
	

}
