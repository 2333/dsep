package com.dsep.dao.dsepmeta.process;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.NormConfig;
import com.dsep.entity.dsepmeta.NormConfigAttr;

public interface NormConfigAttrDao extends DsepMetaDao<NormConfigAttr, String>{
	/**
	 * 获得某实体的所有需要规范化的字段
	 * @param entityId
	 * @return
	 */
	public abstract List<String> getNormField(String entityId);
	
	/**
	 * 获得规范化数据集，字段VALUE
	 * @param entityId
	 * @param fieldName
	 * @return
	 */
	public abstract String getNormDataSet(String entityId,String fieldName);
	
}
