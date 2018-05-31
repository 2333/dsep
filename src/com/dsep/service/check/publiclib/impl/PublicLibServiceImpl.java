package com.dsep.service.check.publiclib.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dsep.dao.dsepmeta.check.PubEntryDao;
import com.dsep.dao.dsepmeta.check.PubFieldConfigDao;
import com.dsep.dao.dsepmeta.check.PubResultDao;
import com.dsep.dao.dsepmeta.check.PubTableConfigDao;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.PubEntry;
import com.dsep.entity.dsepmeta.PubResult;
import com.dsep.service.check.publiclib.PublicLibService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMPublicLibService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.service.file.ExportService;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

public class PublicLibServiceImpl extends MetaOper implements PublicLibService{

	private DMViewConfigService dmViewConfigService;
	private DMPublicLibService dmPublicLibService;
	private PubEntryDao pubEntryDao;
	private PubTableConfigDao pubTableConfigDao;
	private PubFieldConfigDao pubFieldConfigDao;
	private PubResultDao pubResultDao;
	private ExportService exportService;
	
	public DMViewConfigService getDmViewConfigService() {
		return dmViewConfigService;
	}
	public void setDmViewConfigService(DMViewConfigService dmViewConfigService) {
		this.dmViewConfigService = dmViewConfigService;
	}
	
	public ExportService getExportService() {
		return exportService;
	}
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	public PubEntryDao getPubEntryDao() {
		return pubEntryDao;
	}
	public void setPubEntryDao(PubEntryDao pubEntryDao) {
		this.pubEntryDao = pubEntryDao;
	}
	public PubTableConfigDao getPubTableConfigDao() {
		return pubTableConfigDao;
	}
	public void setPubTableConfigDao(PubTableConfigDao pubTableConfigDao) {
		this.pubTableConfigDao = pubTableConfigDao;
	}
	public PubFieldConfigDao getPubFieldConfigDao() {
		return pubFieldConfigDao;
	}
	public void setPubFieldConfigDao(PubFieldConfigDao pubFieldConfigDao) {
		this.pubFieldConfigDao = pubFieldConfigDao;
	}
	public PubResultDao getPubResultDao() {
		return pubResultDao;
	}
	public void setPubResultDao(PubResultDao pubResultDao) {
		this.pubResultDao = pubResultDao;
	}
	public DMPublicLibService getDmPublicLibService() {
		return dmPublicLibService;
	}
	public void setDmPublicLibService(DMPublicLibService dmPublicLibService) {
		this.dmPublicLibService = dmPublicLibService;
	}
	
	
	
	
	@Override
	public PageVM<PubEntry> getPubEntry(int pageIndex, int pageSize) {
		
		List<PubEntry> list = pubEntryDao.getAll();
		int count = list.size();
		
		PageVM<PubEntry> result  = new PageVM<PubEntry>(pageIndex, count, pageSize, list);
		
		return result;
	}
	
	@Override
	public void startPubCompare(String userId){
		dmPublicLibService.startPubCompare(userId);
	}
	
	@Override
	public PageVM<PubResult> getResultDataByType(String pubLibId,
			String type, int pageIndex, int pageSize,
			boolean desc, String orderProperName) {
		List<PubResult> list = pubResultDao.getPubResults(pubLibId, type,pageIndex, pageSize,
				desc, orderProperName);
		int count = list.size();
		PageVM<PubResult> result  = new PageVM<PubResult>(pageIndex, count, pageSize, list);
		return result;
	}
	
	@Override
	public ViewConfig getLocalDataConfig(String entityId) {
		ViewConfig vc = dmViewConfigService.getViewConfig(entityId);
		return vc;
	}
	
	@Override
	public JqgridVM getLocalDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize) {
		List<Map<String, String>> list = dmPublicLibService.getLocalDataDetail(
				entityId, itemId ,sidx, order_flag, page, pageSize);
		int count = list.size();
		return new JqgridVM(page, count, pageSize, list);
	}
/**************************************导出功能部分*****************************************/	
	private List<String> getpublibIDList(){
		List<String> publibIDList=new LinkedList<String>();
		List<String> result = new LinkedList<String>();
		
		publibIDList=pubEntryDao.getCheckedPublib_ID();
		
		for(int i=0;i<publibIDList.size();i++){
			String publidID=publibIDList.get(i);
			if(pubResultDao.hasPubResultByPubId(publidID))
				result.add(publidID);
		}
		return result;
	}
	
	/**
	 * 返回导出文件的表头
	 * @return
	 */
	private List<List<String>> exportTitles(int count){
		
		List<List<String>> result=new LinkedList<List<String>>();
		
		for(int i=0;i<count;i++){
			List<String> titles = new LinkedList<String>();
			titles.add("学校ID");
			titles.add("学科ID");
			titles.add("实体名称");
			titles.add("标识字段");
			titles.add("字段");
			titles.add("本地值");
			titles.add("公共库值");
			titles.add("比对结论");
			result.add(titles);
		}
		return result;
	}
	
	/**
	 * 返回要导出到Excel的数据
	 * @param publibIDList
	 * @return
	 */
	private List<List<String[]>> getExportData(List<String> publibIDList){
		List<List<String[]>> result = new LinkedList<List<String[]>>();
 		
		for(int i=0;i<publibIDList.size();i++){
			String publibID=publibIDList.get(i);
			List<String[]> onePubResult=getSingleExportData(publibID);
			result.add(onePubResult);
		}
		return result;
	}
	/**
	 * 根据一个公共库的ID获取该公共库比对的结果
	 * @param publibID 公共库ID
	 * @return
	 */
	private List<String[]> getSingleExportData(String publibID){
		List<String[]> result = new LinkedList<String[]>();
		
		List<PubResult> data=pubResultDao.getPubResults(publibID, null, 0, 0, true, "UNIT_ID");
		for(int i=0;i<data.size();i++){
			PubResult pr=data.get(i);
			String[] item=new String[8];
			item[0]=pr.getUnitId();
			item[1]=pr.getDiscId();
			item[2]=pr.getEntityChsName();
			item[3]=pr.getFlagField();
			item[4]=pr.getLocalChsField();
			item[5]=pr.getLocalValue();
			item[6]=pr.getPubValue();
			if(pr.getCompareResult()=='O')
				item[7]="与公共库不一致";
			else
				item[7]="不存在公共库";
			result.add(item);
		}
		
		return result;
	}
	/**
	 * 返回公共库的中文名字，就是要导出excel的sheetName
	 * @param publibIDs
	 * @return
	 */
	private List<String> getsheetNames(List<String> publibIDs){
		List<String> result = new LinkedList<String>();
		for(int i=0;i<publibIDs.size();i++){
			String publibID=publibIDs.get(i);
			PubEntry pubEntry=pubEntryDao.getPUBLIB_CHS_NAME(publibID);
			
			result.add(pubEntry.getPublibChsName());
		}
		return result;
	}
	/*************Excel表导出函数***************/
	@Override
	public String getExportCompareInfo(User user, String rootPath) {
	
		String fileString=null;
		int count;
		List<String> publibIDList = this.getpublibIDList();
		List<List<String[]>> exportData=this.getExportData(publibIDList);
		count=publibIDList.size();
		List<List<String>> excelTitles = this.exportTitles(count);
		List<String> excelSheets = this.getsheetNames(publibIDList);
		
		fileString = exportService.exportExcelByData(excelTitles,
				exportData, excelSheets, rootPath, "PubCompare");
		return fileString;
	}
	
}
