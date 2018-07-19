package com.meta.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaEntityMap;

public interface MetaEntityMapDao extends Dao<MetaEntityMap, String>{
	/**
	 * 获取映射实体
	 * @param oriEntityId 源实体ID
	 * @param tarEntityId 目标实体id
	 * @param catId //门类ID
	 * @return
	 */
	public List<MetaEntityMap> getEntityMaps(String oriEntityId,String tarEntityId,String catId); 
	
	/**
	 * 获取唯一一个映射实体
	 * @param oriEntityId
	 * @param tarEntityId
	 * @param catId
	 * @return
	 */
	public MetaEntityMap getEntityMap(String oriEntityId,String tarEntityId,String catId);
}
