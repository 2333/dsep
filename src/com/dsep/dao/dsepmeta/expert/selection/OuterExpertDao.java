package com.dsep.dao.dsepmeta.expert.selection;

import java.util.List;

import com.dsep.dao.common.ExpDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;

public interface OuterExpertDao extends
		ExpDao<OuterExpert> {

	/**
	 * 获取一页数据
	 * 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract List<OuterExpert> getPageFromOtherDBs(
			Integer pageIndex, Integer pageSize, String orderProperName,
			Boolean asc) throws InstantiationException, IllegalAccessException;

	/*
	 * 一次取出所有数据
	 */
	public abstract List<OuterExpert> getAll()
			throws InstantiationException, IllegalAccessException;

	public abstract List<OuterExpert> getByDisc(String discId,
			List<String> selectedExpertNumbers) throws InstantiationException,
			IllegalAccessException;

	public abstract List<OuterExpert> getExperts(
			List<Expert> experts) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 通过专家姓名查找未被选择专家，要排除已选专家
	 */
	public abstract List<OuterExpert> getExpertsByName(String name,
			List<String> selectedExpertNumbers) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 通过专家编号查找专家
	 */
	public abstract OuterExpert getExpertsByExpertNumber(
			String expertNumber) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 通过学科、学校代码查找专家，要排除已选专家
	 */
	public abstract List<OuterExpert> getExpertsQueryByDisAndUnit(
			String discId, String unitId, List<String> selectedExpertNumbers)
			throws InstantiationException, IllegalAccessException;

	/**
	 * 通过专家编号找专家，主要用于替换专家
	 */
	public abstract List<OuterExpert> getExpertsByExpertNumbers(
			List<String> expertsNumbers) throws InstantiationException,
			IllegalAccessException;

	public abstract int getExpertInfoCount();
	
	
	/*新增，仅供重构测试*/
	public abstract List<OuterExpert> getByDisc2(String discId,
			List<String> selectedExpertNumbers, int level) throws InstantiationException,
			IllegalAccessException;
	

	public abstract List<OuterExpert> getAllByCondition()
			throws InstantiationException, IllegalAccessException;
	/*新增，仅供重构测试*/
}
