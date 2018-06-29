package com.dsep.service.datacalculate.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.calculate.CalResultDao;
import com.dsep.dao.dsepmeta.calculate.DataCalculateDao;
import com.dsep.dao.dsepmeta.calculate.DiscIndexValueDao;
import com.dsep.dao.dsepmeta.calculate.IndexScoreDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DataCalculateConfig;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;
import com.dsep.entity.dsepmeta.IndexScore;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.datacalculate.DataCalculateService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMDataCalculateService;
import com.dsep.service.file.ExportService;
import com.dsep.util.Dictionaries;
import com.dsep.util.datacalculate.ConvertNumber;
import com.dsep.util.datacalculate.DiscLastIndexValueVM;
import com.dsep.vm.PageVM;

public class DataCalculateServiceImpl extends MetaOper implements
		DataCalculateService {	
	
	private DataCalculateDao dataCalculateDao;
	private DiscIndexValueDao discIndexValueDao;
	private CalResultDao calResultDao;
	private IndexScoreDao indexScoreDao;
	
	private DisciplineService disciplineService;
	private UnitService unitService;
	private ExportService exportService;
	private DMDataCalculateService dmDataCalculateService;

	public DiscIndexValueDao getDiscIndexValueDao() {
		return discIndexValueDao;
	}

	public void setDiscIndexValueDao(DiscIndexValueDao discIndexValueDao) {
		this.discIndexValueDao = discIndexValueDao;
	}
	
	public DataCalculateDao getDataCalculateDao() {
		return dataCalculateDao;
	}

	public void setDataCalculateDao(DataCalculateDao dataCalculateDao) {
		this.dataCalculateDao = dataCalculateDao;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}

	public DMDataCalculateService getDmDataCalculateService() {
		return dmDataCalculateService;
	}

	public void setDmDataCalculateService(
			DMDataCalculateService dmDataCalculateService) {
		this.dmDataCalculateService = dmDataCalculateService;
	}
	
	public CalResultDao getCalResultDao() {
		return calResultDao;
	}

	public void setCalResultDao(CalResultDao calResultDao) {
		this.calResultDao = calResultDao;
	}

	public IndexScoreDao getIndexScoreDao() {
		return indexScoreDao;
	}

	public void setIndexScoreDao(IndexScoreDao indexScoreDao) {
		this.indexScoreDao = indexScoreDao;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	@Override
	public PageVM<DataCalculateConfig> showDataCalculateConfig(User user,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		PageVM<DataCalculateConfig> result = null;
		String userId = user.getLoginId();
		List<DataCalculateConfig> list = dataCalculateDao
				.getDataCalculateConfig(pageIndex, pageSize, asc, orderPropName);

		int count = list.size();

		if (count <= 0) {// 初次计算，需要进行初始化
			Map<String, String> discAndName = disciplineService
					.getAllEvalDiscMap();
			Set<String> keys = discAndName.keySet();

			for (String discId : keys) {
				DataCalculateConfig dataCalConfig = new DataCalculateConfig();
				dataCalConfig.setCalStatus("0");
				dataCalConfig.setDiscId(discId);
				dataCalConfig.setUserId(userId);
				dataCalConfig.setDiscName(discAndName.get(discId));
				dataCalConfig.setDistance("未聚类");
				// 先在配置信息表中存储所有参评学科的信息，完成初始化
				dataCalculateDao.save(dataCalConfig);
				list.add(dataCalConfig);
			}
			count = list.size();
		}
		result = new PageVM<DataCalculateConfig>(pageIndex, count, pageSize,
				list);
		return result;
	}

	@Override
	public void dataCalculate(User user,List<String> discIds) throws Exception {
		String excepStr="";
		for(String discId:discIds){
			List<String> discList=new ArrayList<String>();
			discList.add(discId);
			String log1 = dmDataCalculateService.calLastIndex(discList);  //算末级指标数值
			if(log1.equals("没有对应的指标体系")){
				excepStr=excepStr+discId+"没有对应的指标体系;";
				continue;
			}
			dmDataCalculateService.convertHundredMark(discList);  //计算末级指标的百分制得分
			dmDataCalculateService.calTotalScore(discList); //计算学科总分
			dmDataCalculateService.calIndexScore(discList);  //计算一二级指标得分
			dmDataCalculateService.deductScore(discList);  //在总分中对某些指标进行扣分
			dmDataCalculateService.sortUnits(discList);
			for(String disc:discList){
				this.updateDataCalConfig(user, disc);
			}
		}
		if(StringUtils.isNotBlank(excepStr)){
			throw new Exception(excepStr);
		}
	}
	
	@Override
	public void dataCluster(List<String> discIds, double limit) {
		dmDataCalculateService.cluster(discIds,limit);
		///更新配置表中的类间距离
		List<DataCalculateConfig> list = dataCalculateDao
				.getDataCalculateConfig(0, 0, true, null);
		for(int i=0;i<list.size();i++){
			DataCalculateConfig dc=list.get(i);
			String discId=dc.getDiscId();
			if(discIds.contains(discId)){
				dc.setDistance(Double.toString(limit));
				dataCalculateDao.saveOrUpdate(dc);
			}
		}
	}

	@Override
	public PageVM<DiscLastIndexValueVM> showDataCalculateResults(String discId) {
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discId);
		List<DiscLastIndexValueVM> vmList = new ArrayList<DiscLastIndexValueVM>();
		for (DiscLastIndexValue e : list) {
			DiscLastIndexValueVM vm = new DiscLastIndexValueVM();
			vm.setDiscLastIndexValue(e);
			vm.setDiscName(Dictionaries.getDisciplineName(discId));
			vm.setUnitName(Dictionaries.getUnitName(e.getUnitId()));
			vmList.add(vm);
		}
		PageVM<DiscLastIndexValueVM> p = new PageVM<DiscLastIndexValueVM>(0,
				999, 20, vmList);
		return p;
	}

	@Override
	public String getExportIndex(String rootPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExportWeight(String rootPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean calWeightAndFactor(String calType) {
		// TODO Auto-generated method stub
		return false;

	}
	
	private void updateDataCalConfig(User user, String discId) {
		String userId = user.getLoginId();
		List<DataCalculateConfig> list = dataCalculateDao
				.getDataCalculateConfig(0, 0, false, "DISC_ID");

		for (DataCalculateConfig dcc : list) {
			if (dcc.getDiscId().equals(discId)) {
				dcc.setUserId(userId);
				dcc.setCalTime(new Date());
				dcc.setCalStatus("1");
				dcc.setDistance("未聚类");
			}
			dataCalculateDao.saveOrUpdate(dcc);
		}
	}
	
	
	//////////////////////////////计算结果展示的方法////////////////////////////////
	@Override
	public PageVM<DataCalculateConfig> showResultConfig(String orderPropName,
			boolean asc, int pageIndex, int pageSize) {

		PageVM<DataCalculateConfig> result = null;

		List<DataCalculateConfig> list = dataCalculateDao
				.getDataCaledConfig(pageIndex, pageSize, true,
						orderPropName);
		int count = list.size();
		result = new PageVM<DataCalculateConfig>(pageIndex, count, pageSize,
				list);
		return result;
	}

	@Override
	public PageVM<CalResult> showResult(String discID,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		
		PageVM<CalResult> result = null;
		List<CalResult> list=calResultDao.getResult(discID,null, pageIndex, pageSize, asc, orderPropName);
		int count=list.size();
	
		result= new PageVM<CalResult>(pageIndex,count,pageSize,list);
		
		return result;
	}

	@Override
	public PageVM<CalResult> showUnitResult(String unit, String orderName,
			boolean asc, int pageIndex, int pageSize) {
		
		PageVM<CalResult> result = null;
		
		//检测输入的unit是学校ID号码还是学校名称
		boolean isUnitID=ConvertNumber.isNum(unit);
		String unitId="";
		if(isUnitID==false){
			//根据unitName找到对应的unitID
			
		}else{
			unitId=unit;
		}
		List<CalResult> list=calResultDao.getResult(null,unitId, pageIndex, pageSize, asc, orderName);
		int count=list.size();
		
		result= new PageVM<CalResult>(pageIndex,count,pageSize,list);
		return result;
	}
	@Override
	public PageVM<IndexScore> showDetial(String discId, String unitId,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		PageVM<IndexScore> result=null;
		List<IndexScore> list=indexScoreDao.getIndexScore(discId, unitId, null, null, orderPropName, asc, pageIndex, pageSize);
		int count=list.size();
		result=new PageVM<IndexScore>(pageIndex,count,pageSize,list);
		return result;
	}

	@Override
	public String getExportData(String rootPath) {
		String fileStr=null;
		List<DataCalculateConfig> list = dataCalculateDao
				.getDataCaledConfig(0, 0, true,
						"DISC_ID");
		List<String> discList=new LinkedList<String>();
		for(DataCalculateConfig dc:list){
			discList.add(dc.getDiscId());
		}
		if(discList.isEmpty())
			return "false";
		List<List<String>> titles=this.getExportTitles(discList);
		List<List<String[]>> exportData=this.getExportData(discList);
		fileStr=exportService.exportExcelByData(titles, exportData, discList, rootPath, "calculate");
		return fileStr;
	}

	private List<List<String>> getExportTitles(List<String> discIds){
		List<List<String>> result=new LinkedList<List<String>>();
		for(String discId:discIds){
			List<String> item=new LinkedList<String>();
			item.add("学科");
			item.add("学校");
			item.add("得分");
			item.add("排名");
			item.add("聚类类别");
			result.add(item);
		}
		return result;
	}
	private List<List<String[]>> getExportData(List<String> discIds){
		List<List<String[]>> result=new LinkedList<List<String[]>>();
		for(String discId:discIds){
			List<CalResult> list=calResultDao.getResult(discId,null, 0, 0, true, "SCORE");
			List<String[]> dataOneDisc=new LinkedList<String[]>();
			for(CalResult calResult:list){
				String [] item=new String[5];
				item[0]=calResult.getDiscName();
				item[1]=calResult.getUnitName();
				item[2]=Double.toString(calResult.getScore());
				item[3]=Integer.toString(calResult.getRank());
				item[4]=calResult.getCluClass();
				dataOneDisc.add(item);
			}
			result.add(dataOneDisc);
		}
		return result;
	}
	
}
