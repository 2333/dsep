/**
 * Project Name:DSEP
 * File Name:DMImportService.java
 * Package Name:com.dsep.service.dsepmeta.dsepmetas
 * Date:2014年5月9日下午4:21:37
 *
 */

package com.dsep.service.dsepmeta.dsepmetas;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: DMImportService <br/>
 * Reason: TODO 与meta有关的importService，不直接对接controller，但是会由importService直接调用 <br/>
 * date: 2014年5月9日 下午4:21:37 <br/>
 *
 * @author QianYuxiang
 */
public interface DMImportService {
	/***
	 * 
	 * createExcelTmp:由service调用的DMService，用于生成excel模板. <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年5月12日 下午1:37:13
	 * @param entityId
	 * @param rootpath
	 * @param additioanlParams TODO
	 * @return
	 */
	public abstract String createExcelTmp(String entityId,String occasion, String rootpath, Map<String,String> additioanlParams);

	/**
	 * importExcel: 获得viewConfig和Jqgrid数据组合封装成json
	 * @author QianYuxiang
	 * @date 2014年5月13日 上午9:03:28
	 * @param entityId
	 * @param excelFile
	 * @param rootPath
	 * @param additionalViewConfigSetting TODO
	 * @param additionalParams TODO
	 * @return
	 * @throws Exception 
	 */
	public abstract String importExcel(String entityId,String occasion, MultipartFile excelFile, String rootPath, Map<String, Map<Object, Object>> additionalViewConfigSetting, Map<String, String> additionalParams) throws Exception;

}

