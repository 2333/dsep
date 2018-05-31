package com.dsep.controller.expert.evaluation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 几个不同打分类型的controller公用的util方法合集，包含方法
 * 1、extractEvalResult()
 * 2、setCurrentBatchExpertInfoIntoSession()
 * 3、getPrevAndNextRoutes()
 *
 */
public class UtilCommon {
	// 提取从前台传递的打分结果，将其转换为UtilEvalResultFromJSP类型
	public static EvalResult extractEvalResult(
			UtilEvalResultFromJSP resultFromJSP,
			List<EvalQuestionVM> questions, Date date) {
		EvalResult result = new EvalResult();

		// 谁(expertId决定)给哪个记录(resultId决定)打的多少分(value决定)
		result.setId(resultFromJSP.getResultId());
		result.setEvalValue(resultFromJSP.getValue());

		if ("".equals(result.getId())) {
			result.setFirstEvalTime(date);
			result.setLastEvalTime(date);
		} else {
			result.setLastEvalTime(date);
		}

		for (EvalQuestionVM q : questions) {
			System.out.println(q.getQuestionId());
		}
		System.out.println("=====================================");
		System.out.println(resultFromJSP.getQuestionId());
		System.out.println("=====================================");
		for (EvalQuestionVM q : questions) {
			if (resultFromJSP.getQuestionId().equals(q.getQuestionId())) {
				result.setEvalQuestion(q.getQuestion());
				break;
			}
		}
		return result;
	}

	// 根据前台专家选择的任务，判断是否需要更改session中的info信息
	public static HttpSession setCurrentBatchExpertInfoIntoSession(
			HttpSession session, EvalProgService evalProgService,
			String batchId, String expertId) {
		// 如果session的info为空
		if (null == session.getAttribute("info")) {
			CurrentBatchExpertInfo info = getNewInfo(batchId, expertId,
					evalProgService);
			session.setAttribute("info", info);
		} else {
			CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
					.getAttribute("info");
			// 如果session的info不为空，但专家已经改变了batch信息
			// 则重置session中的info
			if (!info.getCurrentBatchId().equals(batchId)) {
				info = getNewInfo(batchId, expertId, evalProgService);
				session.setAttribute("info", info);
			}
		}
		return session;
	}

	private static CurrentBatchExpertInfo getNewInfo(String batchId,
			String expertId, EvalProgService evalProgService) {
		CurrentBatchExpertInfo info = new CurrentBatchExpertInfo();
		info.setExpertId(expertId);
		info.setCurrentBatchId(batchId);
		info.setRoutes(evalProgService.getRoutes(info));
		return info;
	}
	
	public static String[] getPrevAndNextRoutes(List<String> routes, String currentRoute) {
		String [] prevAndNextRoutes = new String[2];
		String prevQuestionRoute = null, nextQuestionRoute = null;
		// 逻辑是：假设routesList里下标0~5有值
		// 那么1~4的路由都有前后项(0 < idx < size - 1)
		// 0的只有后项(idx)
		// 5的只有前项
		for (int i = 0; i < routes.size(); i++) {
			if (routes.get(i).equals(currentRoute)) {
				if (i > 0) {
					prevQuestionRoute = routes.get(i - 1);
				}
				if (i < routes.size() - 1) {
					nextQuestionRoute = routes.get(i + 1);
				} 
			}
		}
		prevAndNextRoutes[0] = prevQuestionRoute;
		prevAndNextRoutes[1] = nextQuestionRoute;
		return prevAndNextRoutes;
	}
}
