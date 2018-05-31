package com.dsep.service.expert.select;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.expert.ExpertQueryConditions;
import com.dsep.entity.expert.Expert;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectedVM;

/**
 * 针对已选专家的增删改查 
 * CRUD means create retrieve, update, and delete
 * 
 * 按Ctrl + o 查看所有方法
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface ExpertCRUDService {
	/**
	 * 添加单个专家
	 * 
	 * 根据选出的专家实体添加一条到已选专家表中 之所以每次只添加一条而不是整个List,是因为事务的关系
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void addExpert(Expert expert);

	/**
	 * 添加多个专家
	 */
	public abstract void addExperts(List<Expert> experts, String batchId);

	/**
	 * 删除多个专家
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void deleteExperts(List<String> expertIds);

	/**
	 * 替换单个专家,因为新添加的专家信息在外库,只能通过ExpertNumber去找 而被替换专家在本库,通过ExpertId去找
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void replaceExpert(String oldExpertId,
			String newExpertNumber, Boolean isSecond)
			throws InstantiationException, IllegalAccessException;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void modifyExpertEmail(String id, String newEmail);
	

	/**
	 * 通过主键id获取一名已选专家
	 */
	public abstract ExpertSelectedVM getExpert(String expertId);

	/**
	 * 取出已选专家
	 */
	public abstract PageVM<ExpertSelectedVM> getExperts(String batchId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException;

	/**
	 * 给出一个学科代码，在已选专家中获取一级学科码1或者一级学科码2是该学科代码的所有专家
	 */
	public abstract List<Expert> queryInnerExpertsByDiscIdOrDiscId2(
			String discIdOrDiscId2, String batchId);
	
	/**
	 * 给出一个学科代码，在已选专家中获取一级学科码1或者一级学科码2是该学科代码的所有专家的专家编号
	 */
	public abstract List<String> queryInnerExpertZJBHsByDiscIdOrDiscId2(
			String discIdOrDiscId2, String batchId);

	/**
	 * 前台通过多个条件查询已选专家
	 */
	public abstract PageVM<ExpertSelectedVM> queryExperts(
			ExpertQueryConditions conditions) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 通过查询条件查找外部专家
	 * 
	 * @param conditions
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract PageVM<ExpertSelectedVM> queryOuterExperts(
			ExpertQueryConditions conditions) throws InstantiationException,
			IllegalAccessException;

	/**
	 * 根据专家姓名查找未被选择的专家 用于用户手动添加和替换专家 应该考虑是否和上述接口合并？！
	 * 
	 * @param name
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract PageVM<ExpertSelectedVM> queryOuterExpertsByName(String name)
			throws InstantiationException, IllegalAccessException;

	public abstract PageVM<ExpertSelectedVM> queryOuterExpertsByNumber(
			String number) throws InstantiationException,
			IllegalAccessException;

	public abstract PageVM<ExpertSelectedVM> queryOuterExpertsByDiscIdAndUnitId(
			String discId, String unitId) throws InstantiationException,
			IllegalAccessException;
}
