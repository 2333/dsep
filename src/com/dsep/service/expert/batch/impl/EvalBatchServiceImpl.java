package com.dsep.service.expert.batch.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.dao.dsepmeta.expert.batch.EvalBatchDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalAchvDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.entity.User;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.Expert;
import com.dsep.service.email.EmailSendService;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.DateProcess;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.EmailReplyType;
import com.dsep.util.expert.email.EmailType;
import com.dsep.util.expert.email.FreeMarkerGenerateEmailUtil;
import com.dsep.util.expert.eval.EvalBatchStatus;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalBatchVM;

public abstract class EvalBatchServiceImpl implements EvalBatchService {
	private EmailSendService emailSendService;
	private ExpertDao expertDao;
	private UserService userService;
	private EvalService evalService;
	private EvalBatchDao evalBatchDao;

	public int SendingInvitationEmailsSeparatelyAndSolidifyPapersAndQuestions(
			String expertId, String basePath) throws Exception {
		Expert expert = expertDao.get(expertId);
		EvalBatch batch = expert.getEvalBatch();
		boolean needSolidifyPapersAndQuestions = true;

		// 如果该批次之前已经发送过邮件 生成了题目，那么就不需要生成题目了
		Integer status = batch.getCurrentStatus();
		if (status == null
				|| status == EvalBatchStatus.MSG_SEND_AND_QS_GENERATED.toInt()) {
			needSolidifyPapersAndQuestions = false;
		}

		if (needSolidifyPapersAndQuestions) {
			evalService.solidifyPapers(batch);
			evalService.solidifyQuestions(batch);
			evalBatchDao.modifyEvalBatchStatus(
					EvalBatchStatus.MSG_SEND_AND_QS_GENERATED.toInt(),
					batch.getId());
		}
		sendEmail(basePath, expert, EmailType.InvitationForOneExpertOneAddr,
				expert.getExpertEmail1(), expert.getValidateCode1());
		return 0;
	}
	
	public int massSendingInvitationEmailsAndSolidifyPapersAndQuestions(
			String batchId, String basePath) throws Exception {

		EvalBatch evalBatch = evalBatchDao.get(batchId);
		Set<Expert> experts = evalBatch.getExperts();

		boolean needSolidifyPapersAndQuestions = true;

		// 如果该批次之前已经发送过邮件 生成了题目，那么就不需要生成题目了
		Integer status = evalBatch.getCurrentStatus();
		if (status == null
				|| status == EvalBatchStatus.MSG_SEND_AND_QS_GENERATED.toInt()) {
			needSolidifyPapersAndQuestions = false;
		}

		if (needSolidifyPapersAndQuestions) {
			evalService.solidifyPapers(evalBatch);
			evalService.solidifyQuestions(evalBatch);
			evalBatchDao.modifyEvalBatchStatus(
					EvalBatchStatus.MSG_SEND_AND_QS_GENERATED.toInt(), batchId);
		}

		List<Expert> expertsHasMultiAddrs = new ArrayList<Expert>();
		for (Expert expert : experts) {
			// ！！模拟，需要删除！！
			if (!(expert.getExpertEmail1().equals("dsep002@163.com")
					|| expert.getExpertEmail1().equals("dsep003@163.com")
					|| expert.getExpertEmail1().equals("dsep001@163.com")
					|| expert.getExpertEmail1().equals("dsep004@163.com")
					|| expert.getExpertEmail1().equals("dsep005@163.com")
					|| expert.getExpertEmail1().equals("dsep111@163.com") || expert
					.getExpertEmail1().equals("dsep112@163.com")))
				continue;
			// 需要判断是否已经发送过邮件
			// if ()

			// 只有一个邮箱
			if ((null == expert.getExpertEmail2())
					&& (null == expert.getExpertEmail3())) {
				sendEmail(basePath, expert,
						EmailType.InvitationForOneExpertOneAddr,
						expert.getExpertEmail1(), expert.getValidateCode1());
			} else {
				expertsHasMultiAddrs.add(expert);
			}
		}

		// 在处理多个邮箱之前,FreeMarker要先置空
		FreeMarkerGenerateEmailUtil.destroy();
		// 处理多个邮箱的专家
		for (Expert expert : expertsHasMultiAddrs) {
			// 之所以要先判断email3,因为在模板中,如果email3为null会报错
			if (null != expert.getExpertEmail3()) {
				sendEmail(basePath, expert,
						EmailType.InvitationForOneExpertMultiAddrs,
						expert.getExpertEmail3(), expert.getValidateCode3());
			} else {
				expert.setExpertEmail3("");
				expert.setValidateCode3("");
			}
			sendEmail(basePath, expert,
					EmailType.InvitationForOneExpertMultiAddrs,
					expert.getExpertEmail1(), expert.getValidateCode1());
			// 如果专家有多个邮箱,则email2一定有值
			sendEmail(basePath, expert,
					EmailType.InvitationForOneExpertMultiAddrs,
					expert.getExpertEmail2(), expert.getValidateCode2());

		}
		return 0;
	}

	// ！！项目部署之后，需要更改freemarker模板中的项目网址！！
	public int sendEmail(String basePath, Expert expert,
			EmailType type, String emailAddr, String validateCode)
			throws Exception {
		// 伪代码
		long startTime = System.currentTimeMillis(); // 获取开始时间

		FreeMarkerGenerateEmailUtil util = FreeMarkerGenerateEmailUtil
				.getSingleFreeMarkerGenerateEmailUtil(basePath, type.name());
		String emailContent = util.generateEmail(basePath, expert, type.name(),
				emailAddr, validateCode);

		// 发送邮件
		List<String> list = new ArrayList<String>();
		list.add(emailAddr);
		try {
			emailSendService.sendMail(list, "教育部学位与研究生教育发展中心诚邀您为高校评估",
					emailContent);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		System.out.println("发送邮件");
		// 设置专家当前状态为"已发送邮件"
		expert.setCurrentStatus(ExpertEvalCurrentStatus.Mailed.toInt());
		expertDao.saveOrUpdate(expert);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		return 0;
	}

	public void processRegister(String email) {

	}

	public Map<Integer, String> processActivateAndDoInitJobs(String expertId,
			String email, String validationCode) {
		HashMap<Integer, String> info = new HashMap<Integer, String>();

		Expert expert = expertDao.get(expertId);
		// 验证专家是否存在
		if (null != expert) {
			// 验证专家状态
			if (ExpertEvalCurrentStatus.Mailed.toInt() == expert
					.getCurrentStatus()) {
				// 不确定专家是用哪个邮箱验证的
				if ((email.equals(expert.getExpertEmail1()) && validationCode
						.equals(expert.getValidateCode1()))
						|| (email.equals(expert.getExpertEmail2()) && validationCode
								.equals(expert.getValidateCode2()))
						|| (email.equals(expert.getExpertEmail3()) && validationCode
								.equals(expert.getValidateCode3()))) {
					// 激活成功 设置专家当前状态为"已确认参评"
					expert.setCurrentStatus(ExpertEvalCurrentStatus.Confirmed
							.toInt());

					/*
					 * ！do some init jobs！ Including: 
					 * 1、更改专家的通知状态
					 * 2、把该专家添加到系统的专家用户中 
					 * 3、初始化专家的打分进度
					 */
					expertDao.saveOrUpdate(expert);
					if (null == userService.getUserByLoginId(email)) {
						userService.insertByExpert(expert, email);
					} else {
						// 此用户已经被插入过了，比如参与了前一个批次
						// 什么也不做
					}

					/*
					 * evalProgressService.initExpertEvalProgress(expert.getId(),
					 * expert.getDiscId());
					 */
					info.put(EmailReplyType.OK.toInt(), "激活成功");
				} else {
					info.put(EmailReplyType.ValidateException.toInt(),
							"验证链接不正确");
				}
			} else if (ExpertEvalCurrentStatus.Confirmed.toInt() == expert
					.getCurrentStatus()) {
				User user = userService.getUserByLoginId(email);
				String registedEmail = user.getLoginId();
				info.put(EmailReplyType.AlreadyRegisted.toInt(), "您的"
						+ registedEmail + "邮箱已激活，请使用" + registedEmail
						+ "邮箱登录！(密码是:" + user.getPassword() + ")");
			} else if (ExpertEvalCurrentStatus.Refused.toInt() == expert
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

	public Map<Integer, String> processPreRefuse(String expertId, String email,
			String validationCode) {
		HashMap<Integer, String> info = new HashMap<Integer, String>();

		Expert expert = expertDao.get(expertId);
		// 验证专家是否存在
		if (null != expert) {
			// 验证专家状态
			if (ExpertEvalCurrentStatus.Mailed.toInt() == expert
					.getCurrentStatus()) {
				// 不确定专家是用哪个邮箱验证的
				if ((email.equals(expert.getExpertEmail1()) && validationCode
						.equals(expert.getValidateCode1()))
						|| (email.equals(expert.getExpertEmail2()) && validationCode
								.equals(expert.getValidateCode2()))
						|| (email.equals(expert.getExpertEmail3()) && validationCode
								.equals(expert.getValidateCode3()))) {
					info.put(EmailReplyType.OK.toInt(), "");
				} else {
					info.put(EmailReplyType.ValidateException.toInt(),
							"验证链接不正确");
				}
			} else if (ExpertEvalCurrentStatus.Confirmed.toInt() == expert
					.getCurrentStatus()) {
				// user的id就是expert的id
				User user = userService.getUserByLoginId(email);
				String registedEmail = user.getLoginId();
				info.put(EmailReplyType.AlreadyRegisted.toInt(), "您的"
						+ registedEmail + "邮箱已激活，请使用" + registedEmail
						+ "邮箱登录！(密码是:" + user.getPassword() + ")");
			} else if (ExpertEvalCurrentStatus.Refused.toInt() == expert
					.getCurrentStatus()) {
				info.put(EmailReplyType.AlreadyRefused.toInt(), "您已经拒绝参评了");
			}
		} else {
			info.put(EmailReplyType.ValidateException.toInt(), "验证链接不正确");
			// throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
		}
		return info;
	}
	public void processRefuse(String expertId, String content) {
		Expert expert = expertDao.get(expertId);
		expert.setRemark(content);
		expert.setCurrentStatus(ExpertEvalCurrentStatus.Refused.toInt());
		expertDao.saveOrUpdate(expert);
	}

	// 与业务逻辑无关的getter和setter
	public EmailSendService getEmailSendService() {
		return emailSendService;
	}

	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}

	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public EvalService getEvalService() {
		return evalService;
	}

	public void setEvalService(EvalService evalService) {
		this.evalService = evalService;
	}

	public EvalBatchDao getEvalBatchDao() {
		return evalBatchDao;
	}

	public void setEvalBatchDao(EvalBatchDao evalBatchDao) {
		this.evalBatchDao = evalBatchDao;
	}
}
