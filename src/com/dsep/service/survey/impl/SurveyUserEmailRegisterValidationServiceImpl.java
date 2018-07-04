package com.dsep.service.survey.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.dsep.dao.dsepmeta.survey.SurveyUserDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.service.email.EmailSendService;
import com.dsep.service.rbac.UserService;
import com.dsep.service.survey.SurveyUserEmailRegisterValidationService;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.EmailReplyType;
import com.dsep.util.survey.SurveyUserCurrentStatus;
import com.dsep.util.survey.email.FreeMarkerGenerateEmailForSurveyUserUtil;

public class SurveyUserEmailRegisterValidationServiceImpl implements
		SurveyUserEmailRegisterValidationService {

	private EmailSendService emailSendService;
	private SurveyUserDao surveyUserDao;
	private UserService userService;

	@Override
	public int massSendingInvitationEmails(String basePath) throws Exception {

		List<SurveyUser> users = surveyUserDao.getAll();
		for (SurveyUser user : users) {
			sendEmail(basePath, user, user.getEmail(), user.getValidateCode());
		}
		return 0;
	}

	// ！！项目部署之后，需要更改freemarker模板中的项目网址！！
	@Override
	public int sendEmail(String basePath, SurveyUser user, String emailAddr,
			String validateCode) throws Exception {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		FreeMarkerGenerateEmailForSurveyUserUtil util = FreeMarkerGenerateEmailForSurveyUserUtil
				.getSingleInstance(basePath);
		String emailContent = util.generateEmail(basePath, user);

		// 发送邮件
		List<String> list = new ArrayList<String>();
		list.add(emailAddr);
		try {
			emailSendService.sendMail(list, "教育部学位与研究生教育发展中心诚邀您为高校评估",
					emailContent);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			return 1;
		}
		// 设置专家当前状态为"已发送邮件"
		user.setCurrentStatus(SurveyUserCurrentStatus.Mailed.getIndex());
		surveyUserDao.saveOrUpdate(user);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		return 0;
	}

	@Override
	public void processRegister(String email) {

	}

	@Override
	public Map<Integer, String> processActivateAndDoInitJobs(
			String surveyUserId, String email, String validationCode) {
		HashMap<Integer, String> info = new HashMap<Integer, String>();

		SurveyUser surveyUser = surveyUserDao.get(surveyUserId);
		// 验证专家是否存在
		if (null != surveyUser) {
			// 验证专家状态
			if (ExpertEvalCurrentStatus.Mailed.toInt() == surveyUser
					.getCurrentStatus()) {
				// 不确定专家是用哪个邮箱验证的
				if ((email.equals(surveyUser.getEmail()) && validationCode
						.equals(surveyUser.getValidateCode()))) {
					// 激活成功 设置专家当前状态为"已确认参评"
					surveyUser
							.setCurrentStatus(ExpertEvalCurrentStatus.Confirmed
									.toInt());

					/*
					 * ！do some init jobs！ Including: 
					 * 1、更改问卷调查用户的通知状态
					 * 2、把该问卷调查用户添加到系统的问卷调查用户中 
					 * 3、初始化问卷调查用户的打分进度
					 */
					surveyUserDao.saveOrUpdate(surveyUser);
					if (null == userService.getUserByLoginId(email)) {
						userService.insertBySurveyUser(surveyUser, email);
					} else {
						// 此用户已经被插入过了，比如参与了另一个问卷调查
						// 什么也不做
					}
					info.put(EmailReplyType.OK.toInt(), "激活成功");
				} else {
					info.put(EmailReplyType.ValidateException.toInt(),
							"验证链接不正确");
				}
			} else if (ExpertEvalCurrentStatus.Confirmed.toInt() == surveyUser
					.getCurrentStatus()) {
				// user的id就是expert的id
				User user = userService.getUser(surveyUserId);
				String registedEmail = user.getLoginId();
				info.put(EmailReplyType.AlreadyRegisted.toInt(), "您的"
						+ registedEmail + "邮箱已激活，请使用" + registedEmail + "邮箱登录！");
			} else if (ExpertEvalCurrentStatus.Refused.toInt() == surveyUser
					.getCurrentStatus()) {
				info.put(EmailReplyType.AlreadyRefused.toInt(), "您已经拒绝参评了");
			}

		} else {
			info.put(EmailReplyType.ValidateException.toInt(), "验证链接不正确");
			// throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
		}
		return info;

		// Date currentTime = new Date();// 获取当前时间
		// 验证链接是否过期
		// currentTime.before(user.getRegisterTime());
		// if(currentTime.before(user.getLastActivateTime())) {
		// 验证激活码是否正确

		// } else { throw new ServiceException("激活码已过期！");
		// }
	}

	@Override
	public Map<Integer, String> processPreRefuse(String expertId, String email,
			String validationCode) {
		/*
		 * HashMap<Integer, String> info = new HashMap<Integer, String>();
		 * 
		 * ExpertSelected expert = expertSelectedDao.get(expertId); // 验证专家是否存在
		 * if (null != expert) { // 验证专家状态 if
		 * (ExpertEvalCurrentStatus.Mailed.getIndex() == expert
		 * .getCurrentStatus()) { // 不确定专家是用哪个邮箱验证的 if
		 * ((email.equals(expert.getExpertEmail1()) && validationCode
		 * .equals(expert.getValidateCode1())) ||
		 * (email.equals(expert.getExpertEmail2()) && validationCode
		 * .equals(expert.getValidateCode2())) ||
		 * (email.equals(expert.getExpertEmail3()) && validationCode
		 * .equals(expert.getValidateCode3()))) {
		 * info.put(EmailReplyType.OK.toInt(), ""); } else {
		 * info.put(EmailReplyType.ValidateException.toInt(), "验证链接不正确"); } }
		 * else if (ExpertEvalCurrentStatus.Confirmed.getIndex() == expert
		 * .getCurrentStatus()) { // user的id就是expert的id User user =
		 * userService.getUser(expertId); String registedEmail =
		 * user.getLoginId(); info.put(EmailReplyType.AlreadyRegisted.toInt(),
		 * "您的" + registedEmail + "邮箱已激活，请使用" + registedEmail + "邮箱登录！"); } else
		 * if (ExpertEvalCurrentStatus.Refused.getIndex() == expert
		 * .getCurrentStatus()) {
		 * info.put(EmailReplyType.AlreadyRefused.toInt(), "您已经拒绝参评了"); } } else
		 * { info.put(EmailReplyType.ValidateException.toInt(), "验证链接不正确"); //
		 * throw new ServiceException("该邮箱未注册（邮箱地址不存在）！"); } return info;
		 */
		return null;
	}

	@Override
	public void processRefuse(String expertId, String content) {
		/*
		 * ExpertSelected expert = expertSelectedDao.get(expertId);
		 * expert.setRemark(content);
		 * expert.setCurrentStatus(ExpertEvalCurrentStatus.Refused.getIndex());
		 * expertSelectedDao.saveOrUpdate(expert);
		 */
	}

	// 与业务逻辑无关的getter和setter
	public EmailSendService getEmailSendService() {
		return emailSendService;
	}

	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}

	public SurveyUserDao getSurveyUserDao() {
		return surveyUserDao;
	}

	public void setSurveyUserDao(SurveyUserDao surveyUserDao) {
		this.surveyUserDao = surveyUserDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
