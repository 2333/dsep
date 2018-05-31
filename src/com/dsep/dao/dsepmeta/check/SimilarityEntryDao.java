package com.dsep.dao.dsepmeta.check;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.SimilarityEntry;
import com.meta.entity.MetaEntity;

public interface SimilarityEntryDao extends DsepMetaDao<SimilarityEntry, String>{

	/**
	 * 判断用户的查重入口表是否为空
	 * @param userId
	 * @return
	 */
	abstract public boolean isEmpty(String userId);

	
	/**
	 * 初始化用户查重入口
	 * @param entities
	 */
	abstract public void saveSimilarityEntry(String userId, List<MetaEntity> entities);
	
	/**
	 * 根据用户ID返回该用户的查重入口表
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	abstract public List<SimilarityEntry> getSimilarityEntry(String userId, int pageIndex, int pageSize, String orderPropName, boolean asc);

	/**
	 * 删除上一次用户查重的入口表
	 * @param userId
	 */
	abstract public void deleteSimilarityEntry(String userId);
	
	/**
	 * 查重完成后更新入口表
	 * @param userId
	 * @param entityList
	 */
	abstract public void updateSimilarityEntry(String userId, List<String> similarityEntityIds);

	/**
	 * 查重前先置状态位为：通过
	 * @param userId
	 * @param entityId
	 */
	abstract public void initSimilarityFlag(String userId, String entityId);

}
