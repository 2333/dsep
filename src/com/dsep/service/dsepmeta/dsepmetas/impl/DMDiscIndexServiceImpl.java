package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.domain.UnitDiscCollect;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaEntity;

public class DMDiscIndexServiceImpl extends MetaOper implements
		DMDiscIndexService {
	private EvalFlowDao evalFlowDao;
	private String getCatEntityId(){
		return super.getEntityId("X", 1);
	}
	private String getCatDiscEntityId(){
		return super.getEntityId("X", 2);
	}
	private String getCatCollectEntityId(){
		return super.getEntityId("X", 3);
	}
	
	@Override
	public Map<String, String> getDiscIndex(String discId){
		MetaEntity catEntity = metaEntityService.getById(getCatEntityId());
		MetaEntity catDiscEntity = metaEntityService.getById(getCatDiscEntityId());
		sqlBuilder.clear();
		sqlBuilder.setSql("select a.ID, a.NAME, a.INDEX_ID, a.COLLECT_ID "+
				"from "+catEntity.getName()+" a, " + catDiscEntity.getName() +" b "
				+ " where a.ID = b.CAT_ID and b.DISC_ID = ? ");
		sqlBuilder.addParameter(discId);
		List<String> columnNames = new LinkedList<String>();
		List<MetaDataType> columnTypes = new LinkedList<MetaDataType>();
		columnNames.add("ID");
		columnNames.add("NAME");
		columnNames.add("INDEX_ID");
		columnNames.add("COLLECT_ID");
		sqlBuilder.setColumnNames(columnNames);
		sqlBuilder.setColumnTypes(columnTypes);
		columnTypes.add(MetaDataType.VARCHAR2);
		columnTypes.add(MetaDataType.NVARCHAR2);
		columnTypes.add(MetaDataType.VARCHAR2);
		columnTypes.add(MetaDataType.VARCHAR2);
		List<Map<String, String>> list = sqlExecutor.execQuery(sqlBuilder, 0, 0);
		if(list.size()==1){ 
			return list.get(0);
		}
		else{
			return null;
		}
	}
	@Override
	public List<MetaEntity> getDiscEntity(String discId) {
		Map<String, String> discIndex = getDiscIndex(discId);
		String entityId = getCatCollectEntityId();
		MetaEntity entity = metaEntityService.getById(entityId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("META_CAT", "E");
		sqlBuilder.buildSingleSelectSql(entity, params, "ID", true);
		sqlBuilder.setSql("select ID, META_CAT, PARENT_ID, META_ID from " +entity.getName()
				+ " where META_CAT = ? and ID like ?");
		sqlBuilder.addParameter(discIndex.get("ID")+"%");
		
		List<Map<String, String>> list = sqlExecutor.execQuery(sqlBuilder, 0, 0);
		if(list==null) return null;
		List<MetaEntity> entities = new LinkedList<MetaEntity>();
		for(Map<String,String> rowData: list){
			String collectEntityId = rowData.get("META_ID");
			MetaEntity collEntity = metaEntityService.getById(collectEntityId);
			entities.add(collEntity);
		}
		return entities;
	}
	
	@Override
	public List<String> getEntityIdsByDiscId(String discId) {
		String catId = super.discCategoryDao.getCatByDiscId(discId);
		String collectId= super.categoryDao.getCollectIdById(catId);
		return categoryCollectDao.getEntiyIdsById(collectId);
	}
	@Override
	public List<UnitDiscCollect> getUnitDiscCollects(String unitId,
			String discId) {
		// TODO Auto-generated method stub
		List<UnitDiscCollect> unitDiscCollects= new ArrayList<UnitDiscCollect>(0);
		List<String> roots= categoryCollectDao.getAllRootCollectId();
		Map<String, List<String[]>> colTree = new HashMap<String,List<String[]>>(0);
	    for(String root: roots){
	    	colTree.put(root, categoryCollectDao.getEntityIdAndName(root));
	    }
	    List<String[]> evalUnitAndDisc=evalFlowDao.getUnitIdDiscIdAndCollectId(unitId, discId);
	    for(String[] strings:evalUnitAndDisc)
	    {
	    	if(strings[2]!=null&&colTree.containsKey(strings[2])){
	    		UnitDiscCollect unitDiscCollect= new UnitDiscCollect(strings[0],strings[1],colTree.get(strings[2]));
		    	unitDiscCollects.add(unitDiscCollect);
	    	}	
	    }
		return unitDiscCollects;
	}
	public EvalFlowDao getEvalFlowDao() {
		return evalFlowDao;
	}
	public void setEvalFlowDao(EvalFlowDao evalFlowDao) {
		this.evalFlowDao = evalFlowDao;
	}
	@Override
	public List<String> getCollectTableNameByDiscId(String discId) {
		// TODO Auto-generated method stub
		List<String> tableNames = new ArrayList<String>(0);
		List<String> collectIds=null;
		if(StringUtils.isNotBlank(discId)){
			String catId = discCategoryDao.getCatByDiscId(discId);
			String cRootId = categoryDao.getCollectIdById(catId);
			collectIds= categoryCollectDao.getEntiyIdsById(cRootId);
		}else{
			collectIds= categoryCollectDao.getAllEntityIds();
		}
		for(String collectId: collectIds){
			tableNames.add(metaEntityService.getById(collectId).getName());
		}
		return tableNames;
	}
	@Override
	public String getCategotyByDiscId(String discId) {
		// TODO Auto-generated method stub
		return discCategoryDao.getCatByDiscId(discId);
	}
	@Override
	public List<IndexMap> getIndexMapsByDiscId(String discId) {
		// TODO Auto-generated method stub
		String catId = discCategoryDao.getCatByDiscId(discId);
		String indexId = categoryDao.getIndexIdById(catId);
		return indexMapDao.getIndexMapsByIndexId(indexId);
	}
	@Override
	public List<MetaEntity> getAllEntities() {
		// TODO Auto-generated method stub
		List<String> entityIds = categoryCollectDao.getAllEntityIds();
		List<MetaEntity> entities = new ArrayList<MetaEntity>(0);
		for(String entityId:entityIds){
			entities.add(metaEntityService.getById(entityId));
		}
		return entities;
	}
}
