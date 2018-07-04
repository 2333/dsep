package com.dsep.dao.dsepmeta.check;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.LogicCheckAttrResult;

public interface LogicCheckAttrResultDao extends
		DsepMetaDao<LogicCheckAttrResult, String> {

	/**
	 * 
	 * 分页取Logic_Attr中的数据
	 */
	public abstract List<LogicCheckAttrResult> getLogicCheckAttrResults(
			String unitId, String discId, String entityId, String userId,
			int pageIndex, int pageSize, boolean asc, String orderPropName);

	public abstract List<LogicCheckAttrResult> getExportResultsOfError(
			String unitId, String discId, String entityId, String userId);

	/**
	 * 
	 * 用于显示，显示Logic_Attr中的条数
	 */
	public abstract int getCount(String unitId, String discId, String entityId,
			String userId);

	public abstract void deleteCheckResultData(String unitId, String discId,
			String entityId, String userId);

	// public abstract void newResultRow(Map<String, String> data);
	/**
	 * 
	 * @param result
	 *            保存一个结果
	 */
	public abstract void saveLogicCheckAttrResult(LogicCheckAttrResult result);

	/**
	 * 
	 * @param results
	 *            保存结果集
	 */
	public abstract void saveLogicCheckAttrResults(
			List<LogicCheckAttrResult> results);

	/**
	 * 
	 * @param userId
	 *            删除一个用户的所有查询结果
	 * @param userId2
	 * @param discId
	 */
	public abstract void deleteLogicCheckAttrResultsByUserId(String unitId,
			String discId, String userId);

}
