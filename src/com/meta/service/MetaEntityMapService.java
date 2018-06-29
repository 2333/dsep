package com.meta.service;

import java.util.List;
import java.util.Map;

import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntityMap;

public interface MetaEntityMapService {
	/**
	 * 获取实体映射
	 * @param oriEntityId
	 * @param tarEntityId
	 * @param catId
	 * @return
	 */
	public List<MetaEntityMap> getEntityMaps(String oriEntityId,String tarEntityId,String catId);
	
	/**
	 * 获取一个实体映射
	 * @param oriEntity
	 * @param tarEntityId
	 * @param catId
	 * @return
	 */
	public MetaEntityMap getEntityMap(String oriEntity,String tarEntityId,String catId);
	/**
	 * 根据目标实体ID获得对应的映射表（默认只有一个）
	 * @param tarEntityId 目标实体ID
	 * @param catId 学科分类
	 * @return 实体映射表
	 */
	public MetaEntityMap getEntityMap(String tarEntityId,String catId);
	/**
	 * 将一个原实体数据转换为目标实体数据
	 * @param entityMap 实体映射信息 
	 * @param srcData 原始数据
	 * @return
	 */
	public Map<String, String> convertData(MetaEntityMap entityMap, Map<String, String> srcData);
	/**
	 * 根据目标实体属性，找到元实体属性
	 * @param map
	 * @param tarMapAttr
	 * @return
	 */
	public MetaAttribute getOrigMapAttr(MetaEntityMap map, String tarMapAttr);
}
