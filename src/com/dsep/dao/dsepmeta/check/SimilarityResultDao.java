package com.dsep.dao.dsepmeta.check;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.SimilarityResult;

public interface SimilarityResultDao extends DsepMetaDao<SimilarityResult,String>{
	
	/**
	 * 返回查重结果信息
	 * @param userId 查重用户
	 * @param entityId 实体ID
	 * @param unitId 单位ID
	 * @param discId 学科ID
	 * @return
	 */
	abstract public List<SimilarityResult> getSimilarityResults(String userId, String entityId, String unitId, String discId,
			int pageIndex, int pageSize, String orderPropName, boolean asc);
	/**
	 * 删除用户上次查重的该采集项的结果
	 * @param userId
	 */
	abstract public void deleteResultByUser(String userId, String entityId);
	
	/**
	 * 返回用户查重结果的所有entity列表，用于更新查重入口表
	 * @param userId
	 * @return
	 */
	abstract public Map<String, String> getSimilarityEntity(String userId);
	
	
	/**
	 * 根据传进来的entityIds过滤，返回结果表里边有重复数据的entityIds
	 * @param entityIds
	 * @param userId
	 * @return
	 */
	abstract public List<String> getSimilarityEntityIds(List<String> entityIds, String userId);

}
