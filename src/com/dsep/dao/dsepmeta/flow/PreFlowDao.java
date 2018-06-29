package com.dsep.dao.dsepmeta.flow;

import java.util.List;
import java.util.Map;

import com.dsep.entity.dsepmeta.PreEval;

public interface PreFlowDao {
	/** 获得当前预参评信息
	 * @param unitId （null或空值默认为所有学校）
	 * @param disciplineId (null或空值默认为所有学科)
	 * @param orderPropName
	 * @param asc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	abstract public List<PreEval> getPreDataByPage(String unitId,String disciplineId,Boolean isReport,Boolean isEval,Boolean isUnitReport, String orderPropName, boolean asc, int pageIndex, int pageSize);
	/**
	 * 获取与参评学科数量
	 * @param unitId（null或空值默认为所有学校）
	 * @param discId（null或空值默认为所有学科）
	 * @return
	 */
	abstract public int preEvalCount(String unitId,String discId);
	
	/**
	 * 更新预参评信息
	 * @param preEval
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	abstract public boolean updatePreEval(PreEval preEval) throws NoSuchFieldException, SecurityException;
	
	/**
	 * 保存预参评信息
	 * @param preEval
	 */
	abstract public void savePreEval(PreEval preEval);
	/**
	 * 从基础库将学科、学校信息导入预参评表
	 * @param unitId
	 * @param userId
	 * @return
	 */
	abstract public boolean importDiscsFromBaseDisc(String unitId,String userId);
	
	/**
	 * 本学校是否存在
	 * @param unitId
	 * @return
	 */
	abstract public boolean isHaveUnit(String unitId);
	
	/**
	 * 更新预参评状态
	 * @param unitId
	 * @param state
	 * @param isUnitReport
	 * @return
	 */
	abstract public boolean updateState(String unitId,String state,String isUnitReport);
	/**
	 * 学校提交预参评至中心
	 * 0未提交，1提交
	 * @param unitId
	 * @return
	 */
	abstract public int getUnitPreState(String  unitId);
	
	/**
	 * 是否订阅单位报告
	 * @param unitId
	 * @return
	 */
	abstract public String isUnitReport(String unitId);
	
	/**
	 * 通过学校Id获取所有预参评学科
	 * @param unitId
	 * @return
	 */
	abstract public List<PreEval> getPreEvalsByUnitId(String unitId);
}
