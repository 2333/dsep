package com.dsep.service.check.similarity.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;

import com.dsep.dao.dsepmeta.check.SimilarityEntryDao;
import com.dsep.dao.dsepmeta.check.SimilarityResultDao;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SimilarityEntry;
import com.dsep.entity.dsepmeta.SimilarityResult;
import com.dsep.service.check.similarity.SimilarityCheckService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.dsepmeta.dsepmetas.DMSimilarityCheckService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.service.file.ExportService;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;
import com.meta.entity.MetaEntity;

public class SimilarityCheckServiceImpl implements SimilarityCheckService {

	private DMDiscIndexService discIndexService;
	private DMSimilarityCheckService dmSimilarityCheckService;
	private DMViewConfigService dmViewConfigService;

	private ExportService exportService;


	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public DMViewConfigService getDmViewConfigService() {
		return dmViewConfigService;
	}

	public void setDmViewConfigService(DMViewConfigService dmViewConfigService) {
		this.dmViewConfigService = dmViewConfigService;
	}

	public DMSimilarityCheckService getDmSimilarityCheckService() {
		return dmSimilarityCheckService;
	}

	public void setDmSimilarityCheckService(
			DMSimilarityCheckService dmSimilarityCheckService) {
		this.dmSimilarityCheckService = dmSimilarityCheckService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	
	private SimilarityEntryDao similarityEntryDao;
	private SimilarityResultDao similarityResultDao;
	
	public SimilarityEntryDao getSimilarityEntryDao() {
		return similarityEntryDao;
	}

	public void setSimilarityEntryDao(SimilarityEntryDao similarityEntryDao) {
		this.similarityEntryDao = similarityEntryDao;
	}

	public SimilarityResultDao getSimilarityResultDao() {
		return similarityResultDao;
	}

	public void setSimilarityResultDao(SimilarityResultDao similarityResultDao) {
		this.similarityResultDao = similarityResultDao;
	}
	
	

	@Override
	public PageVM<SimilarityEntry> getSimilarityEntry(User user,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		
		PageVM<SimilarityEntry> result = null;
		
		List<SimilarityEntry> list = similarityEntryDao.getSimilarityEntry(user.getId(), pageIndex, pageSize, orderPropName, asc);

		int count = list.size();
		
		result = new PageVM<SimilarityEntry>(pageIndex, count, pageSize, list);
		
		return result;
	}
	
	@Override
	public void initSimilarityEntry(User user){
		
		if(similarityEntryDao.isEmpty(user.getId())){
			//初始化入口表,学科用户读取自己学科相关采集项，学校用户与中心用户则读取全集
			List<MetaEntity> entities;
			if(user.getUserType().equals("3")){
				entities = discIndexService.getDiscEntity(user.getDiscId());
			}else{
				entities = discIndexService.getAllEntities();
			}
			similarityEntryDao.saveSimilarityEntry(user.getId(), entities);
		}
	}
	
	

	@Override
	public void updateSimilarityEntry(List<String> entityIds, String userId) {
		
		List<String> similarityEntityIds = similarityResultDao.getSimilarityEntityIds(entityIds , userId);
		
		similarityEntryDao.updateSimilarityEntry(userId, similarityEntityIds);
		
	}

	@Override
	@Async
	public void startCheck(List<String> entityIds, String userId, String unitId, String discId) {
		
		//List<MetaEntity> entities = discIndexService.getAllEntities();//获取所有实体全集
		
		for(String entityId: entityIds)
		{
			dmSimilarityCheckService.startCheck(userId, unitId, discId, entityId);
		}
	}

	@Override
	public PageVM<SimilarityResult> getSimilarityResults(String entityId,
			String userId, String unitId, String discId, String orderPropName, boolean asc, int pageIndex, int pageSize) {
		
		PageVM<SimilarityResult> result = null;
		
		List<SimilarityResult> list = similarityResultDao.getSimilarityResults(userId, entityId , unitId, discId, pageIndex, pageSize, orderPropName, asc);
		
		List<SimilarityResult> countList = similarityResultDao.getSimilarityResults(userId, entityId , unitId, discId, 0, 0, null, true);

		int count = countList.size();
		
		result = new PageVM<SimilarityResult>(pageIndex, count, pageSize, list);
		
		return result;
	}
	
	@Override
	public ViewConfig getLocalDataConfig(String entityId) {
		ViewConfig vc = dmViewConfigService.getViewConfig(entityId);
		vc.setVisible("UNIT_ID", "学校", true, 80);
		vc.setVisible("DISC_ID", "学科", true, 120);
		return vc;
	}

	@Override
	public JqgridVM getSimilarityGroupDetail(String entityId, String dataId,
			String simIds, String sidx, boolean order_flag, int page,
			int pageSize, String unitId) {
		List<Map<String, String>> list = dmSimilarityCheckService.getSimilarityGroupDetail(
				entityId, dataId, simIds , sidx, order_flag, page, pageSize, unitId);
		int count = list.size();
		return new JqgridVM(page, count, pageSize, list);
	}

	@Override
	public String getSimilarityExport(User user, String rootPath) {
		String fileString=null;
		
		List<SimilarityEntry> entryList = getSimilarityEntities(user);
		
		if(entryList.isEmpty()){
			return "false";
		}
		
		List<String> entityList = getExportEntities(user, entryList);
		List<String> sheetName = getExportSheetName(user, entryList);
		
		List<List<String[]>> exportDatas = getExportDatas(user, entityList);
		List<List<String>> exportTitles = getExportTitles(user, entityList);

		fileString = exportService.exportExcelByData(exportTitles, exportDatas, sheetName, rootPath, "SimCheck");
		return fileString;
	}


	/*
	 * 获取该用户查重查出的实体
	 */
	private List<SimilarityEntry> getSimilarityEntities(User user) {
		List<SimilarityEntry> entries = similarityEntryDao.getSimilarityEntry(user.getId(), 0, 0, null, true);
		return entries;
	}

	/*
	 * 获取用户查重查出的实体
	 */
	private List<String> getExportEntities(User user, List<SimilarityEntry> entryList) {
		List<String> entities = new LinkedList<String>();
		for(SimilarityEntry se: entryList){
			entities.add(se.getEntityId());
		}
		return entities;
	}
	
	/*
	 * 获取查重的Sheet名称
	 */
	private List<String> getExportSheetName(User user, List<SimilarityEntry> entryList) {
		List<String> entities = new LinkedList<String>();
		for(SimilarityEntry se: entryList){
			entities.add(se.getEntityChsName());
		}
		return entities;
	}
	
	/*
	 * 获取用户查重查出的所有数据
	 */
	private List<List<String[]>> getExportDatas(User user, List<String> entityList) {
		
		List<List<String[]>> result = new LinkedList<List<String[]>>();
		for (String entityId : entityList) {
			result.add(getEachSheetData(user, entityId));
		}
		return result;
	}
	
	
	/*
	 * 获取每一张表格的数据
	 */
	private List<String[]> getEachSheetData(User user, String entityId) {
		
		List<String[]> result = new LinkedList<String[]>();
		
		List<SimilarityResult> similarityGroup = similarityResultDao.getSimilarityResults(user.getId(), entityId, null, null, 0, 0, null, true);
		
		List<String> entityShowAttr= new LinkedList<String>();
		entityShowAttr = dmSimilarityCheckService.getShowAttrList(entityId);
		List<Map<String, String>> groupData = new LinkedList<Map<String, String>>();
		
		int index = 0;
		
		for(SimilarityResult sr :similarityGroup){
			
			String dataId = sr.getDataId();
			String simWithIds = sr.getSimilarityIds();
			String similarityField = sr.getFieldName();
			
			groupData = dmSimilarityCheckService.getSimilarityGroupDetail(entityId, dataId, simWithIds, null, true, 0, 0, user.getUnitId());
			
			List<String[]> oneGroup = new LinkedList<String[]>();
			
			oneGroup = convertToArry(groupData ,entityShowAttr , index, similarityField);
			
			index ++;
			
			result.addAll(oneGroup);
		}
		
		return result;
	}

	/*
	 * 获取查重数据的列名称列表
	 */
	private List<List<String>> getExportTitles(User user, List<String> entityList) {
		List<List<String>> result = dmSimilarityCheckService.getExcelTitles(user, entityList);
		for(List<String> ls: result){
			ls.add(1, "查重字段");
		}
		return result;
	}
	
	/*
	 * 将List<Map<String, String>> 格式转化为List<String[]>数据，并添加组号和查重字段
	 */
	private List<String[]> convertToArry(List<Map<String, String>> list ,List<String> showAttrs,int k, String filedName) {
		List<String[]> result = new LinkedList<String[]>();
		int length=list.get(0).size();
		
		for (int i = 0; i < list.size(); i++) {
			String[] Item = new String[length];
			Map<String, String> elementOfList = list.get(i);
			Item[0]="第 " + (k+1) + " 组";
			int index=1;
			if(StringUtils.isNotBlank(filedName))
			{
				Item[1]=filedName;
				index++;
			}
				
			for(Map.Entry<String, String> entry:elementOfList.entrySet()){     
				if(showAttrs.contains(entry.getKey())){
					Item[index++]=entry.getValue();
				}
			}
			result.add(Item);
		}
		return result;
	}
	
}
