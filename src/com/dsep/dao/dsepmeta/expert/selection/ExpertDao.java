package com.dsep.dao.dsepmeta.expert.selection;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.entity.expert.Expert;
import com.dsep.util.expert.ExpertEvalCurrentStatus;

public interface ExpertDao extends DsepMetaDao<Expert, String> {

	public abstract void addExpertSelected(Expert expert);

	/**
	 * 删除dsep_expert_selected表中所有没有发送邮件的专家 返回值是没有被删除的专家，用于log
	 */
	public abstract List<Expert> deleteExpertsNotMailed(String batchId);

	/**
	 * 表连接查询排序
	 */
	public abstract List<Expert> hqlPage(int pageIndex, int pageSize,
			Boolean desc, String orderProperName);

	/**
	 * 根据专家编号获取专家所属学科ID的集合， 一般为一个，但也可能为两个或多个
	 */
	public List<String> getExpertDiscs(String expertId);

	/**
	 * 根据专家姓名获取已选专家的专家编号 用于跨表查询
	 */
	public abstract List<String> getExpertNumberByName(String name);

	/**
	 * 根据专家编号查询专家
	 */
	public abstract List<String> getExpertByExpertNumber(String expertNumber);

	/**
	 * 根据专家姓名获取已选专家的专家编号 用于跨表查询
	 */
	public abstract List<String> getExpertNumberByDiscIdAndUnitId(
			String discId, String unitId);

	public abstract List<Expert> page(String batchId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract List<Expert> getExpertsNotMailed();

	public abstract List<Expert> getExpertsNotReplied();

	public abstract List<Expert> query(String name);

	public abstract List<Expert> query(String name, String expertNo,
			String expertDisc, Integer expertCurrentStatus, String batchId);

	public abstract List<Expert> query(String expertId, String expertName,
			String expertDisc, Integer expertCurrentStatus,
			String expertNumber, String expertUnit, String expertIs985,
			String expertIs211);

	/**
	 * 通过专家登录ID(即邮箱)来查找专家，可能会有多个，因为一个专家可能被多个批次选中
	 * 这里专家既然能够登录，表示专家确认参评，所以查找出的都是ExpertEvalCurrentStatus是Confirmed的
	 */
	public abstract List<Expert> getComfirmedExpertsByLoginId(String loginId);

	public abstract int Count(String batchId);

	// 统计遴选结果
	public abstract Map<List<String>, List<String>> getStatisticsInfo(
			String batchId);

	public abstract void modifyExpertEmail(String id, String newEmail,
			String validateCode);

	public abstract List<Expert> queryExpertsByDiscIdOrDiscId2(String discId,
			String batchId);

	public abstract List<String> queryExpertZJBHsByDiscIdOrDiscId2(
			String discId, String batchId);

	public abstract List<ExpertNumberGroupByUnitId> countExpertNumbersByConditionsGroupByUnitId(
			String discId, String batchId, List<Integer> statuses);

}
