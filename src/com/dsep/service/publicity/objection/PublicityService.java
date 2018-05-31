package com.dsep.service.publicity.objection;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.publicity.PublicityMessage;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.PublicityManagementVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface PublicityService {
	
	/**
	 * 关闭当前公示轮次
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean publicityFinish() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 自动开启当前公示批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public String autoBeginPublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	
	/**
	 * 自动关闭当前公示批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public String autoClosePublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 获取最新关闭的公示批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PublicityManagement getRecentCloseRound() throws IllegalArgumentException, IllegalAccessException;

	
	/**
	 * 获取所有处于公示状态的公示轮次，包括已关闭的公示轮次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Map<String,String> getAllPublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 获取所有处于公示状态的轮次，包括已关闭的公示轮次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<PublicityManagementVM> getAllPublicityRoundList(String orderName,boolean sord) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 获取公示轮次的相关信息
	 * @return
	 */
	public PublicityMessage getPublicityMessage(String publicityRoundId);
	
	/**
	 * 是否已有开启的公示轮次处于公示状态
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean isPublicityRoundBegin() throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 编辑公示轮次信息
	 * @param roundId 公示轮次Id 
	 * @param remark 公示的备注
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public boolean editPublicity(String roundId,String remark) throws NoSuchFieldException, SecurityException;

	
	public boolean reopenRecentRound() throws IllegalArgumentException, IllegalAccessException;
	
}


