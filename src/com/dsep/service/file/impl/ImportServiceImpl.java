package com.dsep.service.file.impl;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMImportService;
import com.dsep.service.file.ImportService;

public class ImportServiceImpl extends MetaOper implements ImportService {

	private DMImportService dMImportService;
	
	public DMImportService getdMImportService() {
		return dMImportService;
	}
	public void setdMImportService(DMImportService dMImportService) {
		this.dMImportService = dMImportService;
	}

	@Override
	public String createExcelTmpByEntityId(String entityId,String occasion, String rootpath, Map<String,String> additionalParams) {
		return dMImportService.createExcelTmp(entityId, occasion,rootpath, additionalParams);
		// TODO Auto-generated method stub
	}
	/**
	 * TODO 调用DM包获得viewConfig和JqgridVM数据返回给前台JS回调函数进行解析.
	 * @throws Exception 
	 * @see com.dsep.service.file.ImportService#importExcelByEntityId(java.lang.String, org.springframework.web.multipart.MultipartFile, java.lang.String, Map, Map)
	 */
	@Override
	public String importExcelByEntityId(String entityId,String occasion,MultipartFile excelFile, String rootPath, Map<String, Map<Object, Object>> additionalViewConfigSetting, Map<String, String> additionalParams) throws Exception {
		return dMImportService.importExcel(entityId,occasion,excelFile,rootPath, additionalViewConfigSetting, additionalParams);
		// TODO Auto-generated method stub
	}

}

