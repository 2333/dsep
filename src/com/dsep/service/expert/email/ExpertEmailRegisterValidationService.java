package com.dsep.service.expert.email;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.expert.Expert;
import com.dsep.util.expert.email.EmailType;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ExpertEmailRegisterValidationService {
	
	/**
	 * 为所有专家群发邮件，如果没有生成题目，则生成题目
	 * @param batchId
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract int massSendingInvitationEmailsAndSolidifyPapersAndQuestions(
			String batchId, String basePath) throws Exception;
	
	/**
	 * 为每一个专家单独发送邮件，如果没有生成题目，则生成题目
	 * @param expertId
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract int SendingInvitationEmailsSeparatelyAndSolidifyPapersAndQuestions(
			String expertId, String basePath) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract int sendEmail(String basePath, Expert expert,
			EmailType type, String emailAddr, String validateCode)
			throws Exception;

	public abstract void processRegister(String email);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract Map<Integer, String> processActivateAndDoInitJobs(
			String expertId, String email, String validationCode);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract Map<Integer, String> processPreRefuse(String expertId,
			String email, String validationCode);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void processRefuse(String expertId, String content);
}
