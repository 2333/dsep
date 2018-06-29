package com.dsep.controller.expert.evaluation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalService;

@Controller
@RequestMapping("evaluation")
public class EvalSubmitController {
	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;
	@Resource(name = "evalService")
	private EvalService evalService;

	@RequestMapping("progressSubmit")
	public String progress(HttpSession session, Model model) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		// 部署的时候要改一下！
		String homeRoute = UtilDeploymentDescriptor.homeRoute;
		model.addAttribute("homeRoute", homeRoute);
		model.addAttribute("currentBatchId", info.getCurrentBatchId());
		model.addAttribute("expertId", info.getExpertId());
		return "Expert/eval_submit";
	}

	@RequestMapping("progressSubmitGetData")
	@ResponseBody
	public String progressSubmitGetData(HttpSession session) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		return evalProgService.getQPreview(info.getCurrentBatchId(),
				info.getExpertId());
	}
	
	@RequestMapping("progressSubmitClickSubmit")
	@ResponseBody
	public Boolean progressSubmitClickSubmit(HttpSession session) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		evalService.submitResults(info);
		return true;
	}
}
