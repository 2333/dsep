package com.dsep.service.dataprocess.normalization.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.dsep.dao.dsepmeta.process.NormConfigAttrDao;
import com.dsep.dao.dsepmeta.process.NormConfigDao;
import com.dsep.dao.dsepmeta.process.NormResultDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.NormConfig;
import com.dsep.entity.dsepmeta.NormResult;
import com.dsep.service.dataprocess.normalization.NormalizationService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.file.ExportService;
import com.dsep.vm.PageVM;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;

public class NormalizationServiceImpl extends MetaOper implements NormalizationService{

	private NormConfigDao normConfigDao;
	private NormConfigAttrDao normConfigAttrDao;
	private NormResultDao normResultDao;
	
	private MetaEntityService metaEntityService;
	private DMDiscIndexService discIndexService;
	private ExportService exportService;
	
	@Override
	public PageVM<NormConfig> initNormaTable(User user) {
		List<String> fieldList=new ArrayList<String>();
		PageVM<NormConfig> result=null;
		
		List<NormConfig> list=normConfigDao.getConfig();
		if(list.size()==0){
			List<MetaEntity> entitys=discIndexService.getAllEntities();
			for(MetaEntity entity: entitys){
				NormConfig normConfig=new NormConfig();
				String entityId=entity.getId();
				String chsName=entity.getChsName();
				
				MetaEntityDomain domain= metaEntityService.getEntityDomain(entityId);
				fieldList=normConfigAttrDao.getNormField(entityId);
				List<NormResult> normList = new LinkedList<NormResult>();
				//初始化字段的配置,查看该实体是否有不规范数据
				for(String field:fieldList){
					String fieldChsName=this.getFieldChsName(domain, field);
					//获取entityID下的数据
					List<String> entityData=normResultDao.oneEntityIdData(domain.getName(), field);
					//获取非规范数据
					List<String> notNormData=this.notNormData(entityData,domain, field);
					for(int i=0;i<notNormData.size();i++){
						String notNormDataValue=notNormData.get(i);
						NormResult normResult=new NormResult();
						normResult.setEntityId(entityId);
						normResult.setEntityChsName(chsName);
						normResult.setFieldName(field);
						normResult.setFieldChsName(fieldChsName);
						normResult.setOldValue(notNormDataValue);
						normResult.setNormValue("未规范");
						normList.add(normResult);
						normResultDao.saveOrUpdate(normResult);
					}
				}
				
				//如果该实体有不规范数据,初始化实体配置表中关于该实体的规范状态
				if(normList.size()!=0){
					normConfig.setEntityId(entityId);
					normConfig.setTableChsName(chsName);
					normConfig.setNormStatus(0);
					list.add(normConfig);
					normConfigDao.saveOrUpdate(normConfig);
				}
			}
		}
		
		int count=list.size();
		result=new PageVM<NormConfig>(0,count,0,list);
		
		return result;
	}

	@Override
	public int updateNormTable(User user,String entityId ) {
		String userId = user.getId();
		//查看该entityId下是否已经都规范
		List<NormResult> list = normResultDao.oneEntityResult(entityId);
		boolean allNormed = true;
		int status;
		//查看有没有映射关系NormResult是还没有规范的
		for(NormResult normResult:list){
			String normValue=normResult.getNormValue();
			if(normValue.equals("未规范")){
				allNormed = false; break;
			}
		}
		status = allNormed ? 1 : 2 ;
		
		NormConfig normConfig = normConfigDao.getConfigByEntityId(entityId);
		normConfig.setNormStatus(status);
		normConfig.setUserId(userId);
		
		normConfigDao.updateStatus(normConfig);
		
		return status;
	}

	@Override
	public PageVM<NormResult> showNormaFieldData(String entityId) {
		List<NormResult> normList = new LinkedList<NormResult>();
		
		int status;
		//查找该采集实体的规范状态，如果状态为0表示未规范，需要从采集表中去查找需要规范的数据
		status=normConfigDao.selectStatus(entityId);
		//status不等于0，表示规范中，或者规范已完成。从NORMRESULT中获得关于entityId实体的结果记录
		
		normList=normResultDao.oneEntityResult(entityId);
			
		PageVM<NormResult> result = new PageVM<NormResult>(0,normList.size(),0,normList);
		return result;
	}

	@Override
	public String showNormDataSet(String entityId, String fieldName) {
		String normSet=normConfigAttrDao.getNormDataSet(entityId, fieldName);
		String[] option=normSet.split(",");
		StringBuilder str=new StringBuilder();
		
		for(int i=0;i<option.length;i++){
			if(i!=option.length-1)
				str.append(option[i]+":"+option[i]+";");
			else
				str.append(option[i]+":"+option[i]);
		}
		return str.toString();
	}
	
	@Override
	public int saveOneNormaResult(String normResultStr) {
		int index;
		int status=0;
		String entityId;
		String id;
		String normValue;
		
		index=normResultStr.indexOf(':');
		id=normResultStr.substring(0,index);
		normValue=normResultStr.substring(index+1);
		NormResult normResult = normResultDao.get(id);
		normResult.setNormValue(normValue);
		normResultDao.saveOrUpdate(normResult);
		//检查配置表是否应该更新
		entityId=normResult.getEntityId();
		NormConfig normConfig=normConfigDao.getConfigByEntityId(entityId);
		status=normConfig.getNormStatus();
		List<NormResult> list1=normResultDao.oneEntityNotNorm(entityId);

		if(list1.size()==0&&status!=1){
			normConfig.setNormStatus(1);
			normConfigDao.saveOrUpdate(normConfig);
			return 1;//表示该实体已经完全规范
		}	
		else if(list1.size()!=0&&status!=2){
			normConfig.setNormStatus(2);
			normConfigDao.saveOrUpdate(normConfig);
			return 2;//进入规范中的状态
		}
		return 9;
	}

	@Override
	public void saveManyNormalResult(List<NormResult> normResultList) {
		
	}

	@Override
	public String exportNormaResult(String rootPath) {
		String fileString = null;
	
		List<List<String>> excelTitle=new ArrayList<List<String>>();
		List<List<String[]>> excelRowData=new ArrayList<List<String[]>>();
		List<String> excelSheetName=new ArrayList<String>();
		
		List<NormConfig> normConfigList=new ArrayList<NormConfig>();
		List<String> titles=new ArrayList<String>();
		int count;
		
		titles.add("采集表名");
		titles.add("采集项");
		titles.add("采集数据");
		titles.add("规范数据");
		
		normConfigList=normConfigDao.getAll();
		count=normConfigList.size();
		
		for(int i=0;i<count;i++){   //对于每一个实体表
			String entityId;
			String entityChsName;
			List<NormResult> normResultList;
			List<String[]> rowData = new ArrayList<String[]>();   //一个实体表的映射结果
			
			NormConfig normConfig = normConfigList.get(i);
			entityId=normConfig.getEntityId();
			entityChsName=normConfig.getTableChsName();
			normResultList = normResultDao.oneEntityResult(entityId);
			for(int j = 0; j < normResultList.size();j++){
				String [] oneRow = new String [4];
				NormResult normResult = normResultList.get(j);
				oneRow[0] = normResult.getEntityChsName();
				oneRow[1] = normResult.getFieldChsName();
				oneRow[2] = normResult.getOldValue();
				oneRow[3] = normResult.getNormValue();
				rowData.add(oneRow);    //添加一行数据，即一条映射结果
			}
			
			excelTitle.add(titles);
			excelSheetName.add(entityChsName);
			excelRowData.add(rowData);
		}
		
		fileString = exportService.exportExcelByData(excelTitle, excelRowData, excelSheetName, rootPath, "normlization");
		return fileString;
	}
	
	/**
	 * 提取出需要规范化的数据
	 * @param list  需要处理的数据，从数据库中提取出来的原始数据
	 * @param standrds 标准化的数据
	 * @return 非规范的数据
	 */
	private List<String> notNormData(List<String> data,MetaEntityDomain domain,String field){
	
		List<String> list=new ArrayList<String>();
		
		Set<String> standardData=new HashSet<String>();
		List<MetaAttrDomain> attrDomains=domain.getAttrDomains();
		for(MetaAttrDomain attrDomain:attrDomains){
			if(attrDomain.getAttribute().getName().equals(field))
				standardData=attrDomain.getDicItems().keySet();
		}
		
		//去除重复
		Set<String> set = new HashSet<String>(data);
		data.clear();
		data.addAll(set);
		
		 //去除标准数据
		for(int i=0;i<data.size();i++){
			if(standardData.contains(data.get(i))==false)
				list.add(data.get(i));
		}
		return list;
	}
	
	/**
	 * 获得一个字段名的中文名字
	 * @param domain
	 * @param field
	 * @return
	 */
	private String getFieldChsName(MetaEntityDomain domain,String field){
		String result="";
		List<MetaAttrDomain> attrDomains=domain.getAttrDomains();
		for(MetaAttrDomain attrDomain:attrDomains){
			if(attrDomain.getAttribute().getName().equals(field))
				result=attrDomain.getChsName();
		}
		return result;
	}
	/////////////////////////geters & seters////////////////////////////////////

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public NormConfigDao getNormConfigDao() {
		return normConfigDao;
	}

	public void setNormConfigDao(NormConfigDao normConfigDao) {
		this.normConfigDao = normConfigDao;
	}

	public NormConfigAttrDao getNormConfigAttrDao() {
		return normConfigAttrDao;
	}

	public void setNormConfigAttrDao(NormConfigAttrDao normConfigAttrDao) {
		this.normConfigAttrDao = normConfigAttrDao;
	}

	public NormResultDao getNormResultDao() {
		return normResultDao;
	}

	public void setNormResultDao(NormResultDao normResultDao) {
		this.normResultDao = normResultDao;
	}

	public MetaEntityService getMetaEntityService() {
		return metaEntityService;
	}

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	
}
