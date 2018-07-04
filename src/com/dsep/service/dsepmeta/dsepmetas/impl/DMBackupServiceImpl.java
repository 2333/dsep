package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.databackup.CenterDataBackupDao;
import com.dsep.dao.dsepmeta.databackup.DisciplineDataBackupDao;
import com.dsep.entity.dsepmeta.CategoryCollect;
import com.dsep.entity.dsepmeta.DiscCategory;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.util.Configurations;
import com.dsep.util.Tools;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.JqgridVM;
import com.meta.domain.search.SearchGroup;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;
import com.meta.entity.MetaEntity;

public class DMBackupServiceImpl extends MetaOper implements DMBackupService {

	private DMDiscIndexService discIndexService;
	private DisciplineDataBackupDao disciplineDataBackupDao;
	private CenterDataBackupDao centerDataBackupDao;
	
	public CenterDataBackupDao getCenterDataBackupDao() {
		return centerDataBackupDao;
	}

	public void setCenterDataBackupDao(CenterDataBackupDao centerDataBackupDao) {
		this.centerDataBackupDao = centerDataBackupDao;
	}

	public DisciplineDataBackupDao getDisciplineDataBackupDao() {
		return disciplineDataBackupDao;
	}

	public void setDisciplineDataBackupDao(
			DisciplineDataBackupDao disciplineDataBackupDao) {
		this.disciplineDataBackupDao = disciplineDataBackupDao;
	}

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	private boolean isEntryNull(String mapKey,String mapValue){
		if( mapKey != null && mapValue != null 
				&& mapKey != "" && mapValue != ""
					&& mapKey != "null" && mapValue != "null"){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public boolean backupDiscipline(String unitId, String discId,String versionId) {
		// TODO Auto-generated method stub
		//此Map的key为原表的entityId,value为备份表的entityId
	    List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(discId);
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, versionId);
			disciplineDataBackupDao.backupDiscipline(tableName, backupTableName, unitId, discId, versionId);
	    }
	    return true;
	}

	@Override
	public boolean restoreDiscipline(String unitId, String discId, String versionId) {
		// TODO Auto-generated method stub
		List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(discId);
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, versionId);
			disciplineDataBackupDao.restoreDiscipline(tableName, backupTableName, unitId, discId, versionId);
	    }
	    return true;
	}
	
	@Override
	public boolean deleteDiscipline(String versionId, String discId) {
		// TODO Auto-generated method stub
		List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(discId);
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, versionId);
			   disciplineDataBackupDao.deleteBackup(versionId, backupTableName);
	    }
		return true;
	}

	@Override
	public List<CollectionTreeVM> getDiscBackupTree(String discId) {
		// TODO Auto-generated method stub
		String catId = discCategoryDao.getCatByDiscId(discId);
		String collectId= categoryDao.getCollectIdById(catId);
		List<CategoryCollect> categoryCollects = categoryCollectDao.getCategoryTreeById(collectId);
		List<CollectionTreeVM> treeVMs= new ArrayList<CollectionTreeVM>(0);
		for(CategoryCollect c:categoryCollects){
			if("R".equals(c.getMetaCat()))
				continue;
			CollectionTreeVM cTreeVM = new CollectionTreeVM(c.getId(),c.getMetaChsName(),c.getParentId()
					,c.getMetaChsName(),true,true,c.getMetaId());
			treeVMs.add(cTreeVM);
		}
		return treeVMs;
	}

	@Override
	public JqgridVM getJqGridData(String entityId, String unitId,
			String discId, String versionId, SearchGroup searchGroup,int pageIndex, int pageSize,
			String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		if (!params.containsKey("UNIT_ID")&&StringUtils.isNotBlank(unitId)) {
			params.put("UNIT_ID", unitId);// 单位代码
		}
		if (!params.containsKey("DISC_ID")&&StringUtils.isNotBlank(discId)) {
			params.put("DISC_ID", discId);// 学科代码*/
		}
		if(!params.containsKey("VERSION_ID")&&StringUtils.isNotBlank(versionId)){
			params.put("VERSION_ID", versionId);//版本号
		}
		return super.getJqGridData(entityId, versionId, params, searchGroup.toString(), pageIndex, pageSize, orderPropName, asc);
	}

	@Override
	public boolean backupCenter(String versionId) {
		// TODO Auto-generated method stub
		List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(null);
		if( tableNameList != null && tableNameList.size() > 0 )
		{
			System.out.println("------------------------");
			System.out.println(tableNameList.size());
			System.out.println("------------------------");
		}
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, versionId);
			centerDataBackupDao.backupCenter(tableName, backupTableName, versionId);
	    }
	    return true;
	}

	@Override
	public JqgridVM getJqGridDataByCatId(String entityId, String catId,
			String versionId, SearchGroup searchGroup, int pageIndex, int pageSize,
			String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		List<String> discIds=discCategoryDao.getDiscByCatId(catId);
		List<Object> discIdObjcts= new ArrayList<Object>(0);
		for(String discId:discIds){
			discIdObjcts.add((Object)discId);
		}
		Map<String, Object> params = new HashMap<String,Object>(0);
		params.put("VERSION_ID", versionId);
		SearchGroup newSearchGroup = new SearchGroup("and",new SearchRule("DISC_ID",discIdObjcts.toArray(),SearchType.STRING));
		newSearchGroup.addSubGroup(searchGroup);
		return super.getJqGridData(entityId, versionId,params, newSearchGroup.toString(), pageIndex, pageSize, orderPropName, asc);
	}

	@Override
	public boolean deleteBackupData(String versionId) {
		// TODO Auto-generated method stub
		List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(null);
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, versionId);
			centerDataBackupDao.deleteCenterData(versionId, backupTableName);
	    }
		return false;
	}

	@Override
	public JqgridVM getCollectDataDetail(String entityId, List<String> itemIds,
			String versionId, String sidx, boolean order_flag, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		MetaEntity entity = metaEntityService.getById(entityId);
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();		
		for(String itemId :itemIds){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("VERSION_ID", versionId);
			params.put(entity.getPkName(), itemId);
			list.addAll(super.getBackupData(entityId,versionId,params, sidx, order_flag, page, pageSize));
		}
		int count = list.size();
		return new JqgridVM(page, count, pageSize, list);
	}

	@Override
	public boolean createAllBackupTable() {
		// TODO Auto-generated method stub
		List<String> tableNameList = discIndexService.getCollectTableNameByDiscId(null);
		if( tableNameList != null && tableNameList.size() > 0 )
		{
			System.out.println("------------------------");
			System.out.println(tableNameList.size());
			System.out.println("------------------------");
		}
		for(String tableName:tableNameList){
			String backupTableName = Configurations.getBackupTable(tableName, "bak");
			centerDataBackupDao.deleteBackupTable(backupTableName);
			centerDataBackupDao.createBackupTable(tableName, backupTableName);
			/*centerDataBackupDao.backupCenter(tableName, backupTableName, versionId);*/
	    }
	    return true;
	}



	


}
