package com.dsep.service.expert.evaluation;

import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.vm.AchievementVM;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalAchievementServiceBAK {

	/**
	 * 获取某专家有权限进行打分操作的打分项
	 * 
	 * expertId，即专家的loginId
	 * 
	 * Map的key为打分项类型ID，即对应的打分项实体ID；value为打分项名称
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public Map<String, String> getExpertEvalItem(String expertId,String disciplineId);

	/**
	 * 专家同意参评时向学科成果打分表中插入数据
	 * @param expertId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public int insertIntoAchievement(String expertId);
	
	/**
	 * 获取某打分项的打分信息
	 * expertId：专家的loginId
	 * evalItemTypeId：打分项类型Id，即标签页的value
	 * return 打分项的打分信息，包括学校、学科、打分值等
	 */
	public PageVM<AchievementVM> getAchievement(int pageIndex,int pageSize,
			Boolean desc,String orderProperName,String expertId,
			String evalItemTypeId);

	/**
	 * 获取打分项对应的采集项信息
	 * 
	 * @param schoolId
	 *            学校ID
	 * @param disciplineId
	 *            学科ID
	 * @param evalItemTypeId
	 *            打分项类型ID
	 * @return 采集项信息表
	 */
	public PageVM<JqgridVM> getCollection(String schoolId, String disciplineId,
			String evalItemTypeId);

	/**
	 * 存储专家打分信息
	 * 
	 * 专家打分信息，包括专家编号、学科编号、学校编号、打分项类型编号等
	 */
	//public int scoreEvalItem(EvalAchievement achieve);

	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	void Test();

}
