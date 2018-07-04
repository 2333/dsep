package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;
import java.util.Map;

import com.dsep.domain.UnitDiscCollect;
import com.dsep.entity.dsepmeta.IndexMap;
import com.meta.entity.MetaEntity;

/**
 * 学科指标体系系列数据
 * @author thbin
 *
 */
public interface DMDiscIndexService  {
	/*
	 * 获得某个学科对应的学科门类信息
	 * @param discId
	 * @return
	 */
	public Map<String, String> getDiscIndex(String discId);
	
	/**
	 * 获得某个学科对应的指标体系
	 * @param discId
	 * @return
	 */
	public abstract List<MetaEntity> getDiscEntity(String discId);
	
	/**根据学科获取该学科相关的采集项实体ids
	 * @param discId
	 * @return
	 */
	public abstract  List<String> getEntityIdsByDiscId(String discId);
	/**
	 * 获取学校、学科、采集项的实体Id
	 * @return
	 */
	public abstract List<UnitDiscCollect> getUnitDiscCollects(String unitId,String discId);
	
	/**
	 * 通过学科获取该学科的备份表名
	 */
	public abstract List<String> getCollectTableNameByDiscId(String discId);
	
	/**
	 * 通过学科获取学科门类根采集项Id
	 * @param discId
	 * @return
	 */
	public abstract String getCategotyByDiscId(String discId);
	
	/**
	 * 获取某个学科的指标体系项下的所有指标条目
	 * @param indexId
	 * @return
	 */
	public abstract List<IndexMap> getIndexMapsByDiscId(String discId);
	
	public abstract List<MetaEntity> getAllEntities();
	
}
