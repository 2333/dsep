package com.dsep.controller.survey;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.service.survey.SurveyService;
import com.dsep.service.survey.SurveyUserEmailRegisterValidationService;
import com.dsep.service.survey.SurveyUserService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.email.EmailReplyType;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("survey")
public class SurveyUserController {
	@Resource(name = "surveyUserService")
	private SurveyUserService surveyUserService;

	@Resource(name = "surveyService")
	private SurveyService surveyService;

	@Resource(name = "surveyUserEmailRegisterValidationService")
	private SurveyUserEmailRegisterValidationService surveyUserEmailRegisterValidationService;

	@RequestMapping("user_list")
	@ResponseBody
	public String list(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String sord = request.getParameter("sord");// 排序方式
		String sidx = request.getParameter("sidx");// 排序字段
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<SurveyUser> quesVM = surveyUserService.retriveUsers(null, null,
				page, pageSize, order_flag, sidx);

		@SuppressWarnings("rawtypes")
		Map m = quesVM.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}

	// 群发邮件, service中会获取所有已选专家
	@RequestMapping("userMassEmailing")
	public String massEmailing(HttpServletRequest request) {
		@SuppressWarnings("deprecation")
		String basePath = request.getRealPath("/");

		int status = 1;
		try {
			status = surveyUserEmailRegisterValidationService
					.massSendingInvitationEmails(basePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (0 == status) {
			return "success";
		} else {
			return "fail";
		}
	}

	@RequestMapping("surveyReply/{surveyUserId}/{email}/{validateCode}")
	public String userRelpy(
			@PathVariable(value = "surveyUserId") String surveyUserId,
			@PathVariable(value = "email") String email,
			@PathVariable(value = "validateCode") String validateCode,
			Model model) {
		Map<Integer, String> info = surveyUserEmailRegisterValidationService
				.processActivateAndDoInitJobs(surveyUserId, email, validateCode);
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
}
