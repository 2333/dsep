package com.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.meta.dao.MetaEntityMapDao;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaAttributeMap;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityMap;
import com.meta.service.MetaEntityMapService;

public class MetaEntityMapServiceImpl implements MetaEntityMapService{
	
	private MetaEntityMapDao metaEntityMapDao;

	public MetaEntityMapDao getMetaEntityMapDao() {
		return metaEntityMapDao;
	}

	public void setMetaEntityMapDao(MetaEntityMapDao metaEntityMapDao) {
		this.metaEntityMapDao = metaEntityMapDao;
	}

	@Override
	public List<MetaEntityMap> getEntityMaps(String oriEntityId,
			String tarEntityId, String catId) {
		// TODO Auto-generated method stub
		return metaEntityMapDao.getEntityMaps(oriEntityId, tarEntityId, catId);
	}
	

	@Override
	public MetaEntityMap getEntityMap(String oriEntityId, String tarEntityId,
			String catId) {
		// TODO Auto-generated method stub
		return metaEntityMapDao.getEntityMap(oriEntityId, tarEntityId, catId);
	}


	@Override
	public MetaEntityMap getEntityMap(String tarEntityId, String catId) {
		return metaEntityMapDao.getEntityMap("", tarEntityId, catId);
	}	

	@Override
	public Map<String, String> convertData(MetaEntityMap entityMap,
			Map<String, String> srcData) {
		Map<String, String> rowData = new HashMap<String, String>();
		for(MetaAttributeMap attrMap: entityMap.getAttributeMaps())
		{
			String rule = attrMap.getRule();
			String value = srcData.get(attrMap.getOriginAttr().getName());
			if(StringUtils.isNotBlank(rule))
			{
				//需要进行转换
			}
			if(StringUtils.isBlank(value)){
				value="------";
			}
			rowData.put(attrMap.getTargetAttr().getName(), value);
		}
		return rowData;
	}

	@Override
	public MetaAttribute getOrigMapAttr(MetaEntityMap map, String tarMapAttr) {
		for(MetaAttributeMap attrMap:map.getAttributeMaps())
		{
			if(attrMap.getTargetAttr().getName().equals(tarMapAttr))
			{
				return attrMap.getOriginAttr();
			}
		}
		return null;
	}

}
