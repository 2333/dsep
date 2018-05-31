package com.dsep.service.expert.evaluation;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.EvalResult;
import com.dsep.vm.expert.EvalQuestionVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalService {
	/**
	 *  固定化试卷(多少学科门类参评是在这里固定的)
	 *  第一次群发专家打分邀请邮件时调用该方法
	 *  会利用数据库中的metaPaper生成一组非metaPaper
	 *  并根据参评学科初始化非metaPaper的学科门类
	 *  注1：原metaPaper依然保留
	 *  注2：此方法被com.dsep.service.expert.email.impl.
	 *  ExpertEmailRegisterValidationServiceImpl.
	 *  massSendingInvitationEmailsAndSolidifyPapersAndQuestions()调用并管理事务，
	 *  此方法不用声明事务
	 */
	public abstract void solidifyPapers(EvalBatch evalBatch);

	
	/**
	 *  固定化题目(比如多少个学科、学校参评、subQuestionId是在这里固定的)
	 *  第一次群发专家打分邀请邮件时调用该方法
	 *  会利用数据库中的metaPaper对应的metaQ生成一组非metaQ
	 *  并根据参评学科、参评学校、指标体系等初始化非metaPaper的信息
	 *  注1：原metaQ依然保留，并作为专家预览任务信息查询的Q
	 *  注2：此方法被com.dsep.service.expert.email.impl.
	 *  ExpertEmailRegisterValidationServiceImpl.
	 *  massSendingInvitationEmailsAndSolidifyPapersAndQuestions()调用并管理事务，
	 *  此方法不用声明事务
	 */
	public abstract void solidifyQuestions(EvalBatch evalBatch);

	
	/**
	 * 通过专家的登录ID(即邮箱)和专家的批次(如果是多批次需要专家手动选择)
	 * 来初始化专家的一些信息：
	 * ExpertSelected实例
	 * 专家本批次的学科码
	 * 专家本批次学科码所对应的学科门类
	 */
	public abstract CurrentBatchExpertInfo initCurrentBatchExpertInfo(
			String expertLoginId, String batchId);

	/**
	 * 通过CurrentBatchExpertInfo的
	 * 1、专家类别（如：行业专家or学术专家）
	 * 2、学科门类（如：JSJ计算机，通过专家的discId获得） 
	 * 3、题目类型（如：指标打分、成果打分或声誉打分等，即scoreType）
	 * 来获取该专家打分题目的题干信息
	 * 具体对指标体系、指标权重、科学成果、学科声誉的区分在impl中实现
	 * 注意！！如果scoreType不是Achievement，那么subQuestionId传null
	 * 0830在用
	 */
	public abstract List<EvalQuestionVM> getQs(CurrentBatchExpertInfo info,
			int scoreType, String subQId);

	/**
	 * 存储功能：存储打分结果
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void saveResults(List<EvalResult> results);

	/**
	 * 提交功能：当满足条件时，将某个专家的某个任务下的所有打分项全部置为提交状态
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void submitResults(CurrentBatchExpertInfo info);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 获得所有参评学校（的unitId）
	 
	public abstract List<String> getAttendUnits(String discId, int pageIndex,
			int pageSize);*/

	/*	
		 * 查询某个专家给针对某个学科的某个题目的所有参评学校打分结果
		 * 比如查询A专家给0835学科中“高水平论文”题目的55个参评学校的打分结果
		 
		public abstract PageVM<EvalRepuVM> getResult(
				String expertId, String questionId, String discId, int pageIndex,
				int pageSize, boolean asc, String orderProperName);*/

	/*
	 * 查询某道主观打分题（所谓主观打分题，就是专家要对每个参评学校都进行打分，如：高水平论文、学科声誉、学科排名）的完成率
	 * 如果有10个学校参评，专家打了5个学校，那么完成率就是50%
	 
	public abstract Double getSubjectiveQuestionFinishedPCT(String expertId,
			String questionId, Integer questionType, String discId);

	public abstract Integer getSubjectiveQuestionFinishedNumber(
			String expertId, String questionId, Integer questionType,
			String discId);

	
	 * 查询指标体系完成结果，已知questionType
	 
	public abstract Integer getIndicatorIndexFinishedNumber(String expertId,
			String discId);

	
	 * 查询指标权重完成结果，指标权重的存储方式是d,d,d,d的形式存储的，已知questionType
	 
	public abstract Integer getIndicatorWeightFinishedNumber(String expertId,
			String discId);*/
	
	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void saveEvalIndexItem(String discId, String score,
			String indexItemId, String resultId, String expertId, String state,
			EvalQuestionVM question);*/

	/*public abstract PageVM<IndexMapVM2> getIndexMapTree(CurrentBatchExpertInfo info);*/
}
