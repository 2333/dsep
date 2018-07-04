/**
 * Project Name:DSEP
 * File Name:ExportService.java
 * Package Name:com.dsep.service.file
 * Date:2014年5月14日下午3:51:54
 *
 */

package com.dsep.service.file;

import java.util.List;
import java.util.Map;

import com.meta.domain.search.SearchGroup;

/**
 * ClassName: ExportService <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年5月14日 下午3:51:54 <br/>
 *
 * @author QianYuxiang
 */
public interface ExportService {
	/***
	 * 
	 * exportExcelByData:根据传入的数据导出excel表格. <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年5月14日 下午3:58:54
	 * @param excelTitles
	 * @param excelRowData
	 * @param sheetName
	 * @param rootPath
	 * @param storeFolder
	 * @return
	 */
	public abstract String exportExcelByData(List<List<String>> excelTitles, List<List<String[]>> excelRowData, List<String> sheetName, String rootPath, String storeFolder);
	/**
	 * exportExcelByEntityId:根据EntityId导出excel表格. <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年5月14日 下午4:05:01
	 * @param unitId
	 * @param discId
	 * @param entityId
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @return
	 */
	public abstract String exportExcelByEntityId(String unitId, String discId, String entityId,String occasion,
			boolean sortOrder, String orderPropName, String rootPath);
	/**
	 * 根据查询条件导出结果
	 * @param unitId
	 * @param discId
	 * @param entityId
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @param searchGroup
	 * @return
	 */
	public abstract String exportExcelByEntityId(String unitId, String discId, String entityId,String occasion,
			boolean sortOrder, String orderPropName, String rootPath,SearchGroup searchGroup);
	/***
	 * 
	 * @author QianYuxiang
	 * @date 2014年5月14日 下午4:52:04
	 * @param unitId
	 * @param discId
	 * @param location
	 * @return
	 */
	public abstract String exportBriefSheet(String unitId,String discId,String location);
	/**
	 * exportTeacherExcelByTeacherId.教师用户登录后导出教师的相关成果 <br/>
	 *
	 * @author QianYuxiang
	 * @date 2014年7月2日 下午4:08:45
	 * @param entityId
	 * @param teacherList TODO
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @param additionalParams TODO
	 * @return
	 */
	public abstract String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList, boolean sortOrder, String orderPropName, String rootPath,
			Map<String,String> additionalParams);
	/**
	 * 根据查询条件导出数据
	 * @param entityId
	 * @param teacherList
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @param additionalParams
	 * @param searchGroup
	 * @return
	 */
	public abstract String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList,boolean sortOrder,String orderPropName,
			String rootPath,Map<String, String>additionalParams,
			SearchGroup searchGroup);
	/**
	 * 根据unitID导出学科预参评信息
	 * @param unitId
	 * @param rootPath 
	 * @return
	 */
	public abstract String exportPreExcel(String unitId, String rootPath);
	/**
	 * 根据学校ID导出学科参评信息
	 * @param unitId
	 * @return
	 */
	public abstract String exportEvalExcel(String unitId,String rootPath);
	/**
	 * 根据教师ID生成教师简况表
	 * @param teacherId
	 * @return
	 */
	public abstract String exportTeacherBriefSheet(String teacherId);
}

