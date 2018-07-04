
package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.dsepmeta.viewconfig.views.JqgridColConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.JqgridViewConfig;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMImportService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.util.CollectSampleData;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.JqgridVM;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaDataType;

public class DMImportServiceImpl extends MetaOper implements DMImportService {

	private DMViewConfigService dmViewConfigService;
	@Override
	public String createExcelTmp(String entityId,String occasion, String rootpath, Map<String,String> additioanlParams) {
		MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId,occasion);
		String sheetName = metaEntityDomain.getChsName();
		List<String> sampleData = new ArrayList<String>(0);
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		LinkedHashMap<String, List<Object>> colData = new LinkedHashMap<String,List<Object>>();
		for(int i = 0; i < attrDomains.size(); ++i){
			if(attrDomains.get(i).isEditable()&&
					!MetaDataType.FILE.equals(attrDomains.get(i).getDataTypeObject())){
				sampleData.add(CollectSampleData.createExcelSample(attrDomains.get(i)));
				colData.put(attrDomains.get(i).getChsName(), getSelectList(attrDomains.get(i)));
			}
		}
		sampleData.add("本行为样例数据，上传时请删除！");
		//add additional params
		if(additioanlParams != null){
			for(java.util.Map.Entry<String, String> titlEntry : additioanlParams.entrySet()){
				colData.put(titlEntry.getValue(), null);
			}
		}
		// TODO Auto-generated method stub
		Map<String, String> excelPathMap = FileOperate.createExcelTemplate(sheetName, colData, rootpath,sampleData);
		return JsonConvertor.obj2JSON(excelPathMap);
	}

	/**
	 * @throws Exception 
	 * @see com.dsep.service.dsepmeta.dsepmetas.DMImportService#importExcel(java.lang.String, org.springframework.web.multipart.MultipartFile, java.lang.String, Map, Map)
	 */
	@Override
	public String importExcel(String entityId,String occasion, MultipartFile excelFile, String rootPath, Map<String, Map<Object, Object>> additionalViewConfigSetting, Map<String, String> additionalParams) throws Exception{
		//get&&reset viewConfig
		JqgridViewConfig viewConfig = (JqgridViewConfig)dmViewConfigService.getViewConfigByCategory(entityId,occasion);
		if(additionalViewConfigSetting != null){
			List<JqgridColConfig> colConfigs = viewConfig.getColConfigs();
			for(JqgridColConfig colConfig : colConfigs){
				for(java.util.Map.Entry<String, Map<Object, Object>> attrSet : additionalViewConfigSetting.entrySet()){
					if(colConfig.getName().equals(attrSet.getKey())){
						colConfig.setEditable((boolean)attrSet.getValue().get("editable"));
						colConfig.setHidden((boolean)attrSet.getValue().get("hidden"));
					}
				}
			}
		}
		//get JqgirdVM
		MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId,occasion);
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		String tableName = metaEntityDomain.getChsName();
		List<String> titleValues = new ArrayList<String>();
		List<String> titleNames = new ArrayList<String>();
		for(int i = 0; i < attrDomains.size(); ++i){
			if(attrDomains.get(i).isEditable()&&!attrDomains.get(i).isHidden()){
				titleValues.add(attrDomains.get(i).getName());
				titleNames.add(attrDomains.get(i).getChsName());
			}
		}
		if(additionalParams != null){
			for(java.util.Map.Entry<String, String> titleEntry : additionalParams.entrySet()){
				titleValues.add(titleEntry.getKey());
				titleNames.add(titleEntry.getValue());
			}
		}
		JqgridVM jqgridVM = FileOperate.importExcel(tableName, titleValues, titleNames, excelFile, rootPath);
		//merge vewConfig && jqgridVM
		Map<String, Object> importDataMap = new HashMap<String, Object>();
		importDataMap.put("viewconfig", viewConfig);
		importDataMap.put("jqgridvm", jqgridVM);
		String result = JsonConvertor.obj2JSON(importDataMap);
		return result;
	}
	
	/* private functions */
	/***
	 * 私有函数，仅供createExcelTmp调用，生成下拉框选项的list
	 * @param attrDomain
	 * @return list类型返回下拉框选项，对非下拉框返回null
	 */
	private List<Object> getSelectList(MetaAttrDomain attrDomain) {
		if(attrDomain.getDataType().equals("DIC")){//下拉框选项返回下拉list
			Map<String, String> selectMap = attrDomain.getDicItems();
			List<Object> colSelectList = new ArrayList<Object>(selectMap.values());
			return colSelectList;
		}
		else {//非下拉框统一返回null
			return null;
		}
	}
	
	/* getter && setter */
	public DMViewConfigService getDmViewConfigService() {
		return dmViewConfigService;
	}
	public void setDmViewConfigService(DMViewConfigService dmViewConfigService) {
		this.dmViewConfigService = dmViewConfigService;
	}

}

