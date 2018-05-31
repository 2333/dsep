package com.dsep.controller.expert.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.EvalQuestion;
import com.dsep.entity.dsepmeta.EvalResult;
import com.dsep.service.expert.evaluation.EvalIndicatorWeightService;
import com.dsep.service.expert.evaluation.EvalProgressService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalIndicatorWeightVM;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 学科成果打分
 * 
 * 路由1(/progress/achievement/{questionId}):打开Expert/evaluate_achievement.jsp
 * 的学科成果评价页面，并显示有哪些学科成果（如专家、团队）需要打分
 * 
 * 路由2(/progress/achievement_getUnits/):学科成果评价页面有展现参评学校的jqGrid，
 * 		这个路由会为jqGrid加载参评的学校
 * 
 * 路由3(/progress/achievement_initGrid/{entityId}):通过配置信息，获得某个成果的表格展现形式，
 * 		如点击论文tab，每一个学校的子jqGrid展示的是论文的表格
 * 
 * 路由4(/progress/achievement_collectionData/{entityId}/{unitId}/{discId}):点击某个
 * 		学校的打分成果，传递给后台采集项id，学校id和学科id，会展示该参评学校的学科的采集成果，
 * 		从而作为专家打分的依据
 * 
 * 路由5:(/progress/achievement_saveSingleResult/):保存或更新单条打分结果
 * 
 * 路由6:(/progress/achievement_saveMultiResults/):保存或更新多条打分结果
 * 
 * 路由7:
 * 路由8:
 */
//@Scope("prototype")
@Controller
@RequestMapping("evaluation")
public class EvalIndicatorWeightController {

	@Resource(name = "evalIndicatorWeightService")
	private EvalIndicatorWeightService evalIndicatorWeightService;

	@Resource(name = "evalService")
	private EvalService evalService;

	@Resource(name = "evalProgressService")
	private EvalProgressService evalProgressService;

	/**
	 */
	@RequestMapping("/progress/indicatorWeight/")
	public String indicatorWeightPage(Model model, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String userId = user.getId();
		// 专家用户的的用户实体Id就是expertId
		String expertId = userId;
		String discId = user.getDiscId();
		List<EvalQuestionVM> questions = null;
		questions = null;
				//evalService.getQuestions(expertId,
				//Integer.valueOf(QuestionType.INDICATOR_WEIGHT.toString()));
		session.setAttribute("indicatorWeightQuestions", questions);
		
		/*String prevQuestionRoute = evalProgressService.getPrevQuestionRoute(
				expertId, null, QType.INDICATOR_WEIGHT);
		String nextQuestionRoute = evalProgressService.getNextQuestionRoute(
				expertId, null, QType.INDICATOR_WEIGHT.toInt());*/
		// 部署的时候要改一下！
		String homeRoute = "/DSEP/evaluation/progress/";
		
		/*model.addAttribute("prevQuestionRoute", prevQuestionRoute);
		model.addAttribute("nextQuestionRoute", nextQuestionRoute);*/
		model.addAttribute("homeRoute", homeRoute);
		
		return "Expert/evaluate_indicatorWeight";
	}

	@RequestMapping(value = "/progress/getUnits/")
	//, method = RequestMethod.POST)
	@ResponseBody
	public String getIndicatorWeightItems(HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String userId = user.getId();
		// 专家用户的的用户实体Id就是expertId
		String expertId = userId;
		String discId = user.getDiscId();
		PageVM<EvalIndicatorWeightVM> vmList = this.evalIndicatorWeightService
				.getEvalItems(expertId, discId);
		String unitsJSON = JsonConvertor.obj2JSON(vmList.getGridData());
		System.out.println(unitsJSON);
		return unitsJSON;
	}

	/**
	 * 路由6
	 * 保存或更新多条打分结果
	 */
	@RequestMapping(value = "/progress/indicatorWeight_saveMultiResults")
	@ResponseBody
	public String saveResult(
			@RequestBody List<IndicatorWeightResultFromJSP> results,
			HttpSession session) {

		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);

		@SuppressWarnings("unchecked")
		List<EvalQuestionVM> questions = (List<EvalQuestionVM>) session
				.getAttribute("indicatorWeightQuestions");

		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String userId = user.getId();
		// 专家用户的的用户实体Id就是expertId
		String expertId = userId;
		String discId = user.getDiscId();
		Integer alteredAnswerNumber = getAlteredAnswerNumber(results);

		// 从前台来的值，即将要存储到数据库中
		List<EvalResult> list = new ArrayList<EvalResult>();
		// 此for循环是在上一个for循环保证了前段数据的合法性后进行的保存
		for (IndicatorWeightResultFromJSP resultFromJSP : results) {
			resultFromJSP.setExpertId(expertId);
			resultFromJSP.setDiscId(discId);
			EvalResult result = extractEvalResult(resultFromJSP, questions);
			list.add(result);
		}

		

		String nextQuestionRoute = evalService.saveResults(list, null,
				QType.INDICATOR_WEIGHT.toInt(), alteredAnswerNumber);
		return "[{\"nextQuestionRoute\":\"" + nextQuestionRoute + "\"}]";
	}

	private Integer getAlteredAnswerNumber(
			List<IndicatorWeightResultFromJSP> results) {
		Integer totalCount = 0;
		for (IndicatorWeightResultFromJSP result : results) {
			Integer num = Integer.valueOf(result.getEffectItemNum());
			String oldVal = result.getOldEvalValue();
			String val = result.getEvalValue();
			String[] oldValArr = oldVal.split(",", -1);
			String[] valArr = val.split(",", -1);
			for (int i = 0; i < num; i++) {
				// 原来和现在某位上都没有值，do nothing

				// 在最初的时候，数据库中什么也没有，oldValArr只有一个元素，所以要比对i和oldValArr.length
				if (i >= oldValArr.length) {
					// 现在某位上也没有
					if ("".equals(valArr[i])) {
						// do nothing
					} else {
						totalCount++;
					}
				} else {
					if ("".equals(oldValArr[i]) && "".equals(valArr[i])) {
						// do nothing
					}
					// 原来某位上有，现在没有，totalCount--
					else if (!"".equals(oldValArr[i]) && "".equals(valArr[i])) {
						totalCount--;
					}
					// 原来某位上没有，现在有，totalCount++
					else if ("".equals(oldValArr[i]) && !"".equals(valArr[i])) {
						totalCount++;
					}
					// 原来有，现在也有，do nothing
					else if (!"".equals(oldValArr[i]) && !"".equals(valArr[i])) {
						//do nothing
					}
				}

			}
		}
		return totalCount;
	}

	private EvalResult extractEvalResult(
			IndicatorWeightResultFromJSP resultFromJSP,
			List<EvalQuestionVM> questions) {
		EvalResult result = new EvalResult();

		result.setId(resultFromJSP.getId());
		//result.setUnitId(resultFromJSP.getUnitId());
		//result.setDisciplineId(resultFromJSP.getDisciplineId());
		result.setExpertId(resultFromJSP.getExpertId());
		result.setEvalValue(resultFromJSP.getEvalValue());
		//result.setDisciplineId(resultFromJSP.getDiscId());

		if ("".equals(result.getId())) {
			Date date = new Date();
			result.setFirstEvalTime(date);
			result.setLastEvalTime(date);
		} else {
			result.setLastEvalTime(new Date());
		}
		for (EvalQuestionVM question : questions) {
			if (resultFromJSP.getEvalQuestionId().equals(
					question.getQuestionId())) {
				result.setEvalQuestion(question.getQuestion());
				break;
			}
		}
		return result;
	}
}

//从前台传来的专家打分结果，并包含了题干信息Id
class IndicatorWeightResultFromJSP {
	private String id;
	private String evalValue;
	private String evalQuestionId;
	private String oldEvalValue;
	private String effectItemNum;
	private String expertId;
	private String discId;
	private EvalQuestion question;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvalValue() {
		return evalValue;
	}

	public void setEvalValue(String evalValue) {
		this.evalValue = evalValue;
	}

	public String getEvalQuestionId() {
		return evalQuestionId;
	}

	public void setEvalQuestionId(String evalQuestionId) {
		this.evalQuestionId = evalQuestionId;
	}

	public String getOldEvalValue() {
		return oldEvalValue;
	}

	public void setOldEvalValue(String oldEvalValue) {
		this.oldEvalValue = oldEvalValue;
	}

	public String getEffectItemNum() {
		return effectItemNum;
	}

	public void setEffectItemNum(String effectItemNum) {
		this.effectItemNum = effectItemNum;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	
	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}
	
	public EvalQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EvalQuestion question) {
		this.question = question;
	}
}