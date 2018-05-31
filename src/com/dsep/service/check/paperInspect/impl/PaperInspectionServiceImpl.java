package com.dsep.service.check.paperInspect.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dsep.dao.dsepmeta.check.SpotResultDao;
import com.dsep.entity.dsepmeta.SpotResult;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.check.paperInspect.PaperInspectionService;
import com.dsep.service.file.ExportService;
import com.dsep.util.GUID;
import com.dsep.util.datacheck.PaperInspectionAlgorithm;
import com.dsep.vm.PageVM;

public class PaperInspectionServiceImpl implements PaperInspectionService{

	private UnitService unitService;
	private DisciplineService disciplineService;
	private ExportService exportService;
	
	private SpotResultDao spotResultDao;
	
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
	
	public SpotResultDao getSpotResultDao() {
		return spotResultDao;
	}

	public void setSpotResultDao(SpotResultDao spotResultDao) {
		this.spotResultDao = spotResultDao;
	}
	
	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	/*****************************************************************/
	/**
	 * 获取参评学校
	 * @return
	 */
	private List<String> getUnitIDList(){
		List<String> result=new LinkedList<String>();
		result=unitService.getAllEvalUnitIds();
		return result;
	}
	
	/**
	 * 获取某个学校的所有参评学科
	 * @param unitID
	 * @return
	 */
	private List<String> getDiscIDList(String unitID){
		List<String> result=new LinkedList<String>();
		result=disciplineService.getJoinDisciplineByUnitId(unitID);
		return result;
	}
	
	/**
	 * 调用抽查算法进行抽查
	 * @param unit_disc
	 * @return
	 */
	@Override
	public void startSpot(){
		List<String> unitIDList=new LinkedList<String>();
		unitIDList=getUnitIDList();
		
		spotResultDao.deleteAllSpotResult();
		for(int i=0;i<unitIDList.size();i++){
			String unitID=unitIDList.get(i);
			List<String> discIDList=new LinkedList<String>();
			discIDList=getDiscIDList(unitID);
			
			// 获得该学校下抽查的学科
			List<String> spotDiscList=PaperInspectionAlgorithm.randomInspection(discIDList);
			
			// 把该该学校下抽到的学科List存到结果表中
			for(int k=0;k<spotDiscList.size();k++){
				String discID=spotDiscList.get(k);
				SpotResult spotRes=new SpotResult();
				spotRes.setId(GUID.get());
				spotRes.setDiscID(discID);
				spotRes.setUnitID(unitID);
				
				//取到discIDea和unitIDea对应的中文名称
				String unitName=unitService.getUnitNameById(unitID);
				String discName=disciplineService.getDisciplineNameById(discID);
				
				spotRes.setUnitName(unitName);
				spotRes.setDiscName(discName);
				spotResultDao.saveOneSpotResult(spotRes);
			}
		}
	}
	
	@Override
	public PageVM<SpotResult> getSpotList(int pageIndex, int pageSize,
			boolean desc, String orderProperName) {
		
		List<SpotResult> list=spotResultDao.selectAllSpotResult(pageIndex, pageSize,
				desc, orderProperName);
		int count=spotResultDao.getCount();
		
		return new PageVM(pageIndex, count, pageSize, list);
	}

	@Override
	public String getExportSpotList(String rootPath) {
		
		String fileString=null;
		List<List<String>> titleList=new LinkedList<List<String>>();
		List<String> sheets=new LinkedList<>();
		List<List<String[]>> exportData=new LinkedList<List<String[]>>();
		
		List<String> titles=new LinkedList<String>();
		titles.add("单位代码");
		titles.add("单位名称");
		titles.add("一级学科代码");
		titles.add("一级学科名称");
		titleList.add(titles);
		
		sheets.add("抽查清单");
		exportData=getExportData();
		
		fileString = exportService.exportExcelByData(titleList,
				exportData, sheets, rootPath, "PaperInspect");
		
		return fileString;
	}
	private List<List<String[]>> getExportData(){
		List<String[]> data=new LinkedList<String[]>();
		List<SpotResult> list=spotResultDao.getAllData();
		for(int i=0;i<list.size();i++){
			SpotResult spotResult=list.get(i);
			String[] item=new String[4];
			item[0]=spotResult.getUnitID();
			item[1]=spotResult.getUnitName();
			item[2]=spotResult.getDiscID();
			item[3]=spotResult.getDiscName();
			data.add(item);
		}
		List<List<String[]>> result=new LinkedList<List<String[]>>();
		result.add(data);
		return result;
	}

	

}
