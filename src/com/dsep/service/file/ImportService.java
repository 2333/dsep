/**
 * Project Name:DSEP
 * File Name:ImportService.java
 * Package Name:com.dsep.service.file
 * Date:2014年5月9日下午5:17:16
 *
 */

package com.dsep.service.file;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


/**
 * ClassName: ImportService <br/>
 * Reason: TODO 直接面向controller的导入相关的service  <br/>
 * date: 2014年5月9日 下午5:17:16 <br/>
 *
 * @author QianYuxiang
 */
public interface ImportService {
	
	/***
	 * 
	 * createExcelTmpByEntityId: 根据entityId生成用于导入的excel模板 <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年5月12日 上午10:42:44
	 * @param entityId 
	 * @param rootPath
	 * @param additionalParams TODO
	 * @return
	 */
	public abstract String createExcelTmpByEntityId(String entityId,String occasion,String rootPath, Map<String,String> additionalParams);
	/***
	 * 
	 * importExcelByEntityId:根据entityId导入excel数据. <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年5月13日 上午8:53:07
	 * @param entityId
	 * @param excelFile
	 * @param rootPath
	 * @param additionalViewConfigSetting TODO
	 * @param additionalParams TODO
	 * @return 返回前台解析需要的json格式数据
	 * @throws Exception 
	 */
	public abstract String importExcelByEntityId(String entityId,String occasion, MultipartFile excelFile, String rootPath, Map<String, Map<Object, Object>> additionalViewConfigSetting, Map<String, String> additionalParams) throws Exception;
}

