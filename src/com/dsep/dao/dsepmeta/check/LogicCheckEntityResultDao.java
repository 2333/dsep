package com.dsep.dao.dsepmeta.check;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.LogicCheckEntityResult;

public interface LogicCheckEntityResultDao extends
		DsepMetaDao<LogicCheckEntityResult, String> {

	/**
	 * 给中心用，左边表显示各个学校的逻辑检查统计结果
	 */
	public abstract List<Map<String, String>> getResultForCenter(
			String unitId, User user, int pageIndex, int pageSize,
			Boolean desc, String orderProperName);

	/**
	 * 给学校用，左边表显示各个学科的逻辑检查统计结果
	 */
	public abstract List<Map<String, String>> getResultForUnit(
			String discId, User user, int pageIndex, int pageSize,
			Boolean desc, String orderProperName);

	/**
	 * 左边表显示各个实体的逻辑检查结果
	 */
	public abstract List<LogicCheckEntityResult> getResultForDisc(
			String unitId, String discId, String userId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	/**
	 * 对于 学科用户 返回 实体数
	 * 对于 学校用户 返回 学科数
	 * 对于 中心用户 返回 学校数
	 */
	public abstract int getCheckCount(String unitId, String discId,
			String userId);

	public abstract List<LogicCheckEntityResult> getWarnData(String unitId,
			String discId, String entityId, String userId, int pageIndex,
			int pageSize, boolean asc, String orderProName);

	public List<LogicCheckEntityResult> getWarnAndErrorData(String unitId,
			String discId, String entityId, String userId, int pageIndex,
			int pageSize, boolean asc, String orderProName);

	public abstract List<LogicCheckEntityResult> getExportResultsOfWarn(
			String unitId, String discId, String entityId, String userId);

	public abstract int getCheckWarnCount(String unitId, String discId,
			String entityId, String userId);

	public abstract void updatePassInfo(String unitId, String discId,
			Map<String, String> data);

	public abstract void deleteAllByUserId(String userId);

	public abstract void deleteAllOfTheLastData(String unitId, String discId,
			String userId);

	public abstract void saveLogicCheckEntityResults(
			List<LogicCheckEntityResult> results);
}
