package com.dsep.dao.dsepmeta.check.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.SimilarityEntryDao;
import com.dsep.entity.dsepmeta.SimilarityEntry;
import com.meta.entity.MetaEntity;

public class SimilarityEntryDaoImpl extends DsepMetaDaoImpl<SimilarityEntry, String>
		implements SimilarityEntryDao{

	@Override
	public boolean isEmpty(String userId) {
		
		String tableName = super.getTableName("D", "similarity_entry");
		
		StringBuilder sql = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<SimilarityEntry> list = super.sqlFind(sql.toString(), values);
		
		return list.isEmpty();
	}
	
	@Override
	public void saveSimilarityEntry(String userId, List<MetaEntity> entities) {
		for(MetaEntity me: entities){
			SimilarityEntry sr = new SimilarityEntry();
			sr.setUserId(userId);
			sr.setEntityId(me.getId());
			sr.setEntityChsName(me.getChsName());
			super.save(sr);
		}
	}
	
	@Override
	public List<SimilarityEntry> getSimilarityEntry(String userId,
			int pageIndex, int pageSize, String orderPropName, boolean asc) {
		
		String tableName = super.getTableName("D", "similarity_entry");
		
		StringBuilder sql = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		sql.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<SimilarityEntry> list = super.sqlPage(sql.toString(), pageIndex, pageSize, asc, orderPropName, values);
		
		return list;
	}

	@Override
	public void deleteSimilarityEntry(String userId) {
		
		String tableName = super.getTableName("D", "similarity_entry");
		
		StringBuilder hql = new StringBuilder("delete from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		
		hql.append(super.hqlAndCondtion(conditionColumns));
		
		super.sqlBulkUpdate(hql.toString(), params.toArray()); 
	}

	@Override
	public void updateSimilarityEntry(String userId, List<String> similarityEntityIds) {
		for(String s :similarityEntityIds){
			SimilarityEntry sr = getSimilarityEntry(userId, s);
			sr.setSimFlag('1');
			super.saveOrUpdate(sr);
		}
	}
	
	//获取查重入口采集项用于更新状态位
	private SimilarityEntry getSimilarityEntry(String userId, String entityId) {
		
		String tableName = super.getTableName("D", "similarity_entry");
		
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
		
		List<SimilarityEntry> list = super.sqlFind(sql.toString(),  values);
		
		if(list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public void initSimilarityFlag(String userId, String entityId) {
		
		SimilarityEntry sr = getSimilarityEntry(userId, entityId);
		sr.setCheckDate(new Date());
		sr.setSimFlag('0');
		super.saveOrUpdate(sr);
	}

}
