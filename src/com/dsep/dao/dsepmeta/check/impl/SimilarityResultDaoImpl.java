package com.dsep.dao.dsepmeta.check.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.SimilarityResultDao;
import com.dsep.entity.dsepmeta.SimilarityResult;

public class SimilarityResultDaoImpl extends DsepMetaDaoImpl<SimilarityResult, String>
	implements SimilarityResultDao{

	@Override
	public void deleteResultByUser(String userId, String entityId) {
		
		String tableName = super.getTableName("D", "similarity_result");
		
		StringBuilder hql = new StringBuilder("delete from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("ENTITY_ID");
		}
		
		hql.append(super.hqlAndCondtion(conditionColumns));
		
		super.sqlBulkUpdate(hql.toString(), params.toArray()); 
	}

	@Override
	public List<SimilarityResult> getSimilarityResults(String userId,
			String entityId, String unitId, String discId,
			int pageIndex, int pageSize, String orderPropName, boolean asc) {
		
		String tableName = super.getTableName("D", "similarity_result");
		
		StringBuilder sql = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("ENTITY_ID");
		}
		
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("DISC_ID");
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<SimilarityResult> list = super.sqlPage(sql.toString(), pageIndex, pageSize, asc, orderPropName, values);
		
		return list;
	}

	@Override
	public Map<String, String> getSimilarityEntity(String userId) {
		
		Map<String , String> entityList =new LinkedHashMap<String,String>();
		
		String tableName = super.getTableName("D", "similarity_result");
		
		StringBuilder sql = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<SimilarityResult> list = super.sqlFind(sql.toString(), values);
		
		String entityId, entityChsName;
		for(SimilarityResult sr: list){
			
			entityId = sr.getEntityId();
			entityChsName = sr.getEntityChsName();
			
			if(entityList.containsKey(entityId)) continue;
			else entityList.put(entityId, entityChsName);
		}
		
		return entityList;
	}

	@Override
	public List<String> getSimilarityEntityIds(List<String> entityIds, String userId) {
		
		List<String> similarityEntityIds = new LinkedList<String>();
		
		for(String entityId : entityIds){
			if(!ifThereIsSimilarityData(userId, entityId)){
				similarityEntityIds.add(entityId);
			}
		}
		return similarityEntityIds;
	}
	
	private boolean ifThereIsSimilarityData(String userId, String entityId){
		
		String tableName = super.getTableName("D", "similarity_result");
		
		StringBuilder sql = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("ENTITY_ID");
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<SimilarityResult> list = super.sqlFind(sql.toString(), values);
		
		return list.isEmpty();
	}
}
