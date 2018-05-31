package com.dsep.service.survey;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.SurveyUser;

@Transactional(propagation = Propagation.SUPPORTS)
public interface SurveyUserEmailRegisterValidationService {
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract int massSendingInvitationEmails(String basePath)
			throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract int sendEmail(String basePath, SurveyUser user,
			String emailAddr, String validateCode) throws Exception;

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
