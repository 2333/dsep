
package com.dsep.service.file.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dsep.entity.dsepmeta.Eval;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMExportService;
import com.dsep.service.file.ExportService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.service.flow.PreFlowService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.meta.domain.search.SearchGroup;

public class ExportServiceImpl extends MetaOper implements ExportService {
	private DMExportService dMExportService;
	private PreFlowService preFlowService;
	private EvalFlowService evalFlowService;
	private DisciplineService disciplineService;
	
	public DisciplineService getDisciplineService() {
		return disciplineService;
	}
	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
	public EvalFlowService getEvalFlowService() {
		return evalFlowService;
	}
	public void setEvalFlowService(EvalFlowService evalFlowService) {
		this.evalFlowService = evalFlowService;
	}
	public PreFlowService getPreFlowService() {
		return preFlowService;
	}
	public void setPreFlowService(PreFlowService preFlowService) {
		this.preFlowService = preFlowService;
	}
	@Override
	public String exportExcelByEntityId(String unitId, String discId, String entityId, String occasion,boolean sortOrder, String orderPropName, String rootPath) {
		return dMExportService.exportExcel(unitId, discId, entityId,occasion, sortOrder, orderPropName, rootPath);
		// TODO Auto-generated method stub
	}
	@Override
	public String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList, boolean sortOrder, String orderPropName,
			String rootPath, Map<String,String> additionalParams) {
		// TODO Auto-generated method stub
		return dMExportService.exportTeacherExcelByEntityId(entityId,teacherList,sortOrder,orderPropName, rootPath, additionalParams);
	}
	@Override
	public String exportExcelByEntityId(String unitId, String discId,
			String entityId,String occasion, boolean sortOrder, String orderPropName,
			String rootPath, SearchGroup searchGroup) {
		// TODO Auto-generated method stub
		return dMExportService.exportExcel(unitId, discId, entityId, occasion,sortOrder, orderPropName, rootPath, searchGroup);
	}
	@Override
	public String exportExcelByData(List<List<String>> excelTitles,
			List<List<String[]>> excelRowData, List<String> sheetName, String rootPath,
			String storeFolder) {
		Map<String, String> excelPathMap = FileOperate.exportExcel(excelTitles, excelRowData, sheetName, rootPath, storeFolder);
		// TODO Auto-generated method stub
		return JsonConvertor.obj2JSON(excelPathMap);
	}

	@Override
	public String exportBriefSheet(String unitId, String discId, String location) {
		
		// TODO Auto-generated method stub
		return dMExportService.exportBriefSheetPDF(unitId, discId, location);
	}
	/* getter && setter */
	public DMExportService getdMExportService() {
		return dMExportService;
	}
	public void setdMExportService(DMExportService dMExportService) {
		this.dMExportService = dMExportService;
	}
	
	@Override
	public String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList, boolean sortOrder, String orderPropName,
			String rootPath, Map<String, String> additionalParams,
			SearchGroup searchGroup) {
		// TODO Auto-generated method stub
		return dMExportService.exportTeacherExcelByEntityId(entityId, teacherList, 
				sortOrder, orderPropName, rootPath,
				additionalParams, searchGroup);
	}
	@Override
	public String exportPreExcel(String unitId,String rootPath) {
		
		List<PreEval> preList = preFlowService.getPreEvalsByUnitIds(unitId);
		
		List<String> sheetName = new ArrayList<String>();
		sheetName.add("预参评信息汇总");
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		titleNames.get(0).add("学科代码");
		titleNames.get(0).add("是否参评");
		titleNames.get(0).add("是否需要报告");
		List<List<String[]>> rowStrings =new ArrayList<List<String[]>>();
		rowStrings.add(new ArrayList<String[]>());
		for(PreEval preEval : preList)
		{
			String[] row =new String[3];
			row[0]=preEval.getDiscId().toString();
			row[1]=(preEval.getIsEval())?"是":"否";
			row[2]=(preEval.getIsReport())?"是":"否";
 			rowStrings.get(0).add(row);
		}
		
		String sheetTitle=Dictionaries.getUnitName(unitId)+"预参评信息汇总";
		Map<String, String> excelPathMap=FileOperate.exportExcel(titleNames, rowStrings, sheetName, sheetTitle,rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}
	@Override
	public String exportEvalExcel(String unitId, String rootPath) {
		
		List<Eval> evalList = evalFlowService.getEvalByUnitId(unitId);
		List<String> sheetName = new ArrayList<String>();
		sheetName.add("参评信息汇总");
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		titleNames.get(0).add("学科代码");
		titleNames.get(0).add("学科名称");
		titleNames.get(0).add("学科采集状态");
		titleNames.get(0).add("学科当前版本号");
		titleNames.get(0).add("是否参评");
		titleNames.get(0).add("是否需要报告");
		List<List<String[]>> rowStrings =new ArrayList<List<String[]>>();
		rowStrings.add(new ArrayList<String[]>());
		for(Eval eval : evalList)
		{
			String[] row =new String[6];
			row[0]=eval.getDiscId().toString();
			row[1]=disciplineService.getDisciplineNameById(eval.getDiscId().toString());
			row[2]=Dictionaries.getCollectFlowTypes(eval.getState());    
			row[3]=(eval.getDiscVersionNo()==null)?"未生成":eval.getDiscVersionNo();
			row[4]=(eval.getIsEval())?"是":"否";
			row[5]=(eval.getIsReport())?"是":"否";
 			rowStrings.get(0).add(row);
		}
		String sheetTitle=Dictionaries.getUnitName(unitId)+"参评信息汇总";
		Map<String, String> excelPathMap=FileOperate.exportExcel(titleNames, rowStrings, sheetName, sheetTitle,rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}
	@Override
	public String exportTeacherBriefSheet(String teacherId) {
		// TODO Auto-generated method stub
		
		return null;
	}
}

