package com.dsep.controller.expert.evaluation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalBatchVM;

@Controller
@RequestMapping("evaluation")
public class EvalBatchController {
	@Resource(name = "evalBatchService")
	private EvalBatchService evalBatchService;

	@RequestMapping("progressShowBatches")
	@ResponseBody
	public String showBatches(HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String expertLoginId = user.getLoginId();

		// 查询打分的学科、学校以及分数
		PageVM<EvalBatchVM> vmList = evalBatchService
				.showEvalBatches(expertLoginId);

		String json = JsonConvertor.obj2JSON(vmList.getGridData());
		return json;
	}
}
