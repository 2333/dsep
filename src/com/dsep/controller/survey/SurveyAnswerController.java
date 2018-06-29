package com.dsep.controller.survey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.entity.User;
import com.dsep.entity.survey.Answer;
import com.dsep.entity.survey.Questionnaire;
import com.dsep.service.survey.SurveyService;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.util.survey.QNRStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.survey.QuestionnaireVM;

@Controller
@RequestMapping("survey")
public class SurveyAnswerController {
	@Resource(name = "surveyService")
	private SurveyService surveyService;

	@RequestMapping("answer_list")
	@ResponseBody
	public String list(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		String sord = request.getParameter("sord");// 排序方式
		String sidx = request.getParameter("sidx");// 排序字段
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<QuestionnaireVM> quesVM = surveyService.retriveQNRs(
				QNRStatus.PUBLISHED.toInt(), page, pageSize, order_flag, sidx);

		
		@SuppressWarnings("rawtypes")
		Map m = quesVM.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}

	@RequestMapping("answer_save")
	@ResponseBody
	public Boolean saveAnswer(@RequestBody List<Answer> answerList,
			HttpSession session) {
		// System.out.println(answer);
		try {
			surveyService.saveAnswer(answerList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@RequestMapping("answer_preview/{id}")
	public void preview(@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		String path = surveyService.retriveQNR(id).getPath();
		BufferedReader br = null;
		String line = null;
		String questionContent = "";
		String sep = File.separator;
		path = path + sep + "real.html";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					path), "utf-8"));
			line = br.readLine();
			while (line != null) {
				questionContent += line;
				line = br.readLine();
			}
			PrintWriter writer = response.getWriter();
			writer.print(questionContent);
			writer.close();
			br.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("answer_getAnswersByQNRIdAndUserId/{id}/{userId}")
	@ResponseBody
	public String getAnswers(@PathVariable(value = "id") String id,
			@PathVariable(value = "userId") String userId, HttpSession session) {
		List<Answer> list = surveyService.getAnswers(id, userId);
		String json = JsonConvertor.obj2JSON(list);
		return json;
	}
	
	@RequestMapping("home_getUserList")
	@ResponseBody
	public String getUsers(HttpSession session) {
		/*List<Answer> list = surveyService.getAnswers(id, userId);
		String json = JsonConvertor.obj2JSON(list);
		return json;*/
		PageVM<User> list = surveyService.getQNRUsers();

		Map m = list.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		System.out.println(json);
		return json;
	}
	

}
