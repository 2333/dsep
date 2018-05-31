package com.dsep.dao.dsepmeta.base;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.CategoryCollect;
import com.meta.entity.MetaEntity;

public interface CategoryCollectDao extends DsepMetaDao<CategoryCollect, String>{
	/**
	 * 由门类获取该门类的采集项
	 * @param id
	 * @return
	 */
	public abstract List<CategoryCollect> getCategoryTreeById(String id);
	/**
	 * 通过父ID获取门类
	 * @param parentId
	 * @return
	 */
	public abstract List<CategoryCollect> getCategorysByParentId(String parentId);
	/**
	 * 由id获取门类
	 * @param id
	 * @return
	 */
	public abstract CategoryCollect getCatCollectById(String id);
	
	/**
	 * 导入逻辑检查表
	 */
	public abstract void importToLogic(String unitId,String discId,String collectId);
	/**
	 * 通过门类采集项ID获取该门类的所有EntityId
	 * @param catId
	 * @return
	 */
	public abstract List<String> getEntiyIdsById(String id);
	/**
	 * 获取entityId和backupId
	 * @param catColectId
	 * @return
	 */
	public abstract Map<String,String> getEnIdAndEnBkIdsById(String id);
	
	/**
	 * 通过学校Id和学科Id获得实体Id和中文名字，学科Id为null获取本校的所有实体Id和中文名字
	 * 学校、学科ID均为null，获取所有实体Id和中文名字
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract Map<String, String> getEntityIdAndName(String unitId,String discId);
	/**
	 * 获取所有实体Id和中文名字
	 * @return
	 */
	public abstract Map<String, String> getAllEntityAndBackupId();
	
	/**
	 * 获取所有根节点的ID
	 * @return
	 */
	public abstract List<String> getAllRootCollectId();
	/**
	 * 通过采集项根Id，获取实体Id和实体name
	 * @param collectId
	 * @return
	 */
	public abstract List<String[]> getEntityIdAndName(String id);
	/**
	 * 获取所有的EntityIds
	 * @return
	 */
	public abstract List<String> getAllEntityIds();
	
}
