package com.dsep.service.project.email.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.dsep.entity.project.PassItem;
import com.dsep.service.email.EmailSendService;
import com.dsep.service.project.SchoolProjectService;
import com.dsep.service.project.email.ProjectEmailService;
import com.dsep.util.project.email.FreeMarkerGenerateEmailUtil;

public class ProjectEmailServiceServiceImpl implements ProjectEmailService {

	private EmailSendService emailSendService;
	private SchoolProjectService schoolProjectService;

	// ！！项目部署之后，需要更改freemarker模板中的项目网址！！
	@Override
	public int sendEmail(String basePath, List<PassItem> items,
			String projectId, String emailTitle, String emailContentFromJSP,
			String attachementPath, String attchmentName) throws Exception {
		// 伪代码
		long startTime = System.currentTimeMillis(); // 获取开始时间

		String templateName = "";
		Integer currentState = schoolProjectService.getProjectById(projectId)
				.getCurrentState();
		if (currentState == 4) {
			templateName = "middle_check";
		} else if (currentState == 6) {
			templateName = "final_check";
		} 
		// 需要删除
		else {
			templateName = "middle_check";
		}

		FreeMarkerGenerateEmailUtil util = FreeMarkerGenerateEmailUtil
				.getSingleFreeMarkerGenerateEmailUtil(basePath, templateName,
						emailContentFromJSP);
		for (PassItem item : items) {
			String emailContent = util.generateEmail(basePath, item,
					templateName);

			// 发送邮件
			List<String> list = new ArrayList<String>();
			//list.add(item.getContactInfo());
			list.add("dsep001@163.com");
			try {
				emailSendService.sendMail(list, emailTitle, emailContent,
						attachementPath, attchmentName);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
				return 1;
			}
			System.out.println("发送邮件");
			break;
		}

		// 设置专家当前状态为"已发送邮件"

		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		
		if (currentState == 4) {
			schoolProjectService.setProjectState(projectId, currentState + 1);
		} else if (currentState == 6) {
			schoolProjectService.setProjectState(projectId, currentState + 1);
		} 
		return 0;
	}

	// 与业务逻辑无关的getter和setter
	public EmailSendService getEmailSendService() {
		return emailSendService;
	}

	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}

	public SchoolProjectService getSchoolProjectService() {
		return schoolProjectService;
	}

	public void setSchoolProjectService(
			SchoolProjectService schoolProjectService) {
		this.schoolProjectService = schoolProjectService;
	}
}
