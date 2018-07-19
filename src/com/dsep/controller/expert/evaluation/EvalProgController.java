package com.dsep.controller.expert.evaluation;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.expert.evaluation.EvalProgService;

@Controller
@RequestMapping("evaluation")
public class EvalProgController {
	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;

	@RequestMapping("progress")
	public String progress() {
		return "Expert/eval_prog";
	}

	@RequestMapping("progressGetData/{currentBatchId}/{expertId}")
	@ResponseBody
	public String progressGetData(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			@PathVariable(value = "expertId") String expertId) {
		return evalProgService.getQPreview(currentBatchId, expertId);
	}
}
