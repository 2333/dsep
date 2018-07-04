package com.dsep.controller.expert.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.email.EmailSendService;
import com.dsep.service.expert.email.ExpertEmailRegisterValidationService;
import com.dsep.util.TextProcess;
import com.dsep.util.expert.email.EmailReplyType;

/**
 * 
 * @author p_next
 * 现在模拟注册 
 * 邮箱从dsep001@mail.163~dsep100@mail.163
 * 密码patent123
 */
@Controller
@RequestMapping("expert")
public class ExpertEmailController {
	@Resource(name = "emailSendService")
	private EmailSendService emailSendService;

	@Resource(name = "expertEmailRegisterValidationService")
	private ExpertEmailRegisterValidationService expertEmailRegisterValidationService;

	// 群发邮件, service中会获取所有已选专家
	@RequestMapping("selectExpertMassEmailing/{currentBatchId}")
	@ResponseBody
	public String massEmailing(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			HttpServletRequest request) {
		@SuppressWarnings("deprecation")
		String basePath = request.getRealPath("/");

		int status = 1;
		try {
			status = expertEmailRegisterValidationService
					.massSendingInvitationEmailsAndSolidifyPapersAndQuestions(
							currentBatchId, basePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (0 == status) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	// 群发邮件, service中会获取所有已选专家
		@RequestMapping("selectExpertEmailingSeparately/{expertId}")
		@ResponseBody
		public String emailingSeparately(
				@PathVariable(value = "expertId") String expertId,
				HttpServletRequest request) {
			@SuppressWarnings("deprecation")
			String basePath = request.getRealPath("/");

			int status = 1;
			try {
				status = expertEmailRegisterValidationService
						.SendingInvitationEmailsSeparatelyAndSolidifyPapersAndQuestions(
								expertId, basePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (0 == status) {
				return "success";
			} else {
				return "fail";
			}
		}

	@RequestMapping("selectExpertSendEmail")
	public void sendEmail(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody List<String> emailList) {
		String content;
		try {
			@SuppressWarnings("deprecation")
			String basePath = request.getRealPath("/");

			content = TextProcess.getContentByAbsolutePath(basePath
					+ "EmailModule/expert.jsp");

			/*emailSendService.sendMail(emailList,"experts",content);*/

			List<String> list = new ArrayList<String>();
			list.add("287180993@qq.com");

			emailSendService.sendMail(list, "experts", content);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("expertReply/{expertId}/{email}/{validateCode}")
	public String expertReplyEmail(
			@PathVariable(value = "expertId") String expertId,
			@PathVariable(value = "email") String email,
			@PathVariable(value = "validateCode") String validateCode,
			Model model) {
		Map<Integer, String> info = expertEmailRegisterValidationService
				.processActivateAndDoInitJobs(expertId, email, validateCode);
		Integer emailReplyType = info.keySet().iterator().next();
		if (emailReplyType == EmailReplyType.OK.toInt()) {
			return "/rbac/logout";
		} else if (emailReplyType == EmailReplyType.AlreadyRegisted.toInt()) {
			model.addAttribute("email", info.get(emailReplyType));
			return "Expert/emailValidateStatus/AlreadyRegisted";
		} else if (emailReplyType == EmailReplyType.ValidateException.toInt()) {
			return "/Expert/emailValidateStatus/ValidateException";
		} else if (emailReplyType == EmailReplyType.OutOfData.toInt()) {
			return "/Expert/emailValidateStatus/OutOfData";
		} else if (emailReplyType == EmailReplyType.AlreadyRefused.toInt()) {
			return "/rbac/emailValidateStatus/AlreadyRefused";
		} else {
			return "/rbac/logout";
		}
	}

	@RequestMapping("expertRefuse/{expertId}/{email}/{validateCode}")
	public String expertRefuseEmail(
			@PathVariable(value = "expertId") String expertId,
			@PathVariable(value = "email") String email,
			@PathVariable(value = "validateCode") String validateCode,
			Model model) {
		Map<Integer, String> info = expertEmailRegisterValidationService
				.processPreRefuse(expertId, email, validateCode);
		Integer emailReplyType = info.keySet().iterator().next();
		if (emailReplyType == EmailReplyType.OK.toInt()) {
			model.addAttribute("expertId", expertId);
			return "/Expert/emailValidateStatus/Refuse";
		} else if (emailReplyType == EmailReplyType.AlreadyRegisted.toInt()) {
			return "/Expert/emailValidateStatus/AlreadyRegisted";
		} else if (emailReplyType == EmailReplyType.ValidateException.toInt()) {
			return "/Expert/emailValidateStatus/ValidateException";
		} else if (emailReplyType == EmailReplyType.OutOfData.toInt()) {
			return "/Expert/emailValidateStatus/OutOfData";
		} else if (emailReplyType == EmailReplyType.AlreadyRefused.toInt()) {
			return "/Expert/emailValidateStatus/AlreadyRefused";
		} else {
			return "/rbac/logout";
		}
	}

	@RequestMapping("expertRefuseReason")
	@ResponseBody
	public String expertRefuseReason(@RequestBody Reason reason) {
		expertEmailRegisterValidationService.processRefuse(
				reason.getExpertId(), reason.getContent());
		return null;
	}
}

class Reason {
	private String expertId;
	private String content;

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
