package com.dsep.service.dsepmeta.dsepmetas;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.meta.domain.search.SearchGroup;

public interface DMExportService {

	/***
	 * 根据entityId导出excel数据，只导出一个学科的一个entity数据
	 * @param unitId
	 * @param discId
	 * @param entityId
	 * @param sortOrder
	 * @param sortName
	 * @param rootPath
	 * @return
	 */
	public abstract String exportExcel(String unitId,String discId,String entityId,String occasion,boolean sortOrder,String orderPropName,String rootPath);
	/**
	 * 根据查询条件导出
	 * @param unitId
	 * @param discId
	 * @param entityId
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @return
	 */
	public abstract String exportExcel(String unitId,String discId,String entityId,String occasion,boolean sortOrder,String orderPropName,String rootPath,SearchGroup searchGroup);
	/***
	 * 根据学校、学科导出简况表，一所学校的一个学科对应一张简况表
	 * @param unitId
	 * @param discId
	 * @param location
     * @return
	 */
	public abstract String exportBriefSheetPDF(String unitId,String discId,String location);
	public abstract void generateMEMOTemplate(String unitId, String discId,
			String ckEditorData) throws FileNotFoundException;

	/**
	 * @author QianYuxiang
	 * @date 2014年7月4日 上午10:00:10
	 * @param entityId
	 * @param teacherList TODO
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @param additionalParams TODO
	 * @return
	 */
	public abstract String exportTeacherExcelByEntityId(String entityId, List<Object> teacherList,
			boolean sortOrder, String orderPropName, String rootPath, Map<String,String> additionalParams);
	/**
	 * 通过条件导出教师数据
	 * @param entityId
	 * @param teacherList
	 * @param sortOrder
	 * @param orderPropName
	 * @param rootPath
	 * @param additionalParams
	 * @param searchGroup
	 * @return
	 */
	public abstract String exportTeacherExcelByEntityId(String entityId, List<Object> teacherList,
			boolean sortOrder, String orderPropName, String rootPath, Map<String,String> additionalParams,SearchGroup searchGroup);
	
}
