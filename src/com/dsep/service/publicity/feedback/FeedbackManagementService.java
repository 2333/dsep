package com.dsep.service.publicity.feedback;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.feedback.FeedbackMessage;
import com.dsep.entity.dsepmeta.FeedbackManagement;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackManagementVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface FeedbackManagementService {
	
	/**
	 * 获取当前的反馈批次，如果没有则返回空
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public FeedbackManagement getCurrentFeedbackRound() throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 开启新的反馈批次
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean openNewFeedbackRound();
	
	/**
	 * 开始进行反馈答复
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ParseException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean beginFeedback(String feedbackName,String beginTime,String endTime,String remark) throws IllegalArgumentException, IllegalAccessException, ParseException;
	
	/**
	 * 立即反馈
	 * @return
	 */
	public String immediateFeedback() throws IllegalArgumentException, IllegalAccessException, ParseException;
	
	/**
	 * 反馈批次是否已开启
	 * @return
	 */
	public boolean isCurrentFeedbackOpen();
	
	/**
	 * 反馈是否正在进行中
	 * @return
	 */
	public boolean isCurrentFeedbackBegin();

	/**
	 * 获取反馈类型的树
	 * @return 
	 */
	public List<CollectionTreeVM> getFeedbackTypeTree();

	/**
	 * 获取所有反馈批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Map<String, String> getAllFeedbackRound() throws IllegalArgumentException, IllegalAccessException; 
	
	/**
	 * 获取反馈批次的信息
	 * @param feedbackRoundId
	 * @return
	 */
	public FeedbackMessage getFeedbackRoundMessage(String feedbackRoundId);

	/**
	 * 获取所有反馈批次的信息
	 * @param orderName
	 * @param order_flag
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<FeedbackManagementVM> getAllFeedbackRoundList(
			String orderName, boolean order_flag) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 保存对于备注的修改
	 * @param roundId
	 * @param remark
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public boolean editFeedback(String roundId, String remark) throws NoSuchFieldException, SecurityException;

	/**
	 * 结束当前开启的反馈批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean feedbackFinish() throws IllegalArgumentException, IllegalAccessException; 
	
	/**
	 * 删除某一反馈批次并批量删除备份数据，暂时作为测试时删除数据使用
	 * @param publicityRoundId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean deleteFeedbackRound(String feedbackRoundId);

	@Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public String autoCloseFeedbackRound() throws IllegalArgumentException,
			IllegalAccessException;
	
}
