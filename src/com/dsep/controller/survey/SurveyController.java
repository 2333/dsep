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
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.entity.survey.Answer;
import com.dsep.entity.survey.Questionnaire;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.survey.SurveyService;
import com.dsep.util.FileOperate;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.survey.QuestionnaireVM;

@Controller
@RequestMapping("survey")
public class SurveyController {
	@Resource(name = "surveyService")
	private SurveyService surveyService;
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	/**
	 * 跳转主页
	 */
	@RequestMapping("home")
	public String qview() {
		return "survey/home";
	}

	@RequestMapping("home_list")
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
		PageVM<QuestionnaireVM> quesVM = surveyService.retriveQNRs(null, page,
				pageSize, order_flag, sidx);

		@SuppressWarnings("rawtypes")
		Map m = quesVM.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}

	/**
	 * 跳转新建问卷页面
	 */
	@RequestMapping("create")
	public String qedit(Model model) {
		// 规定即将创建的问卷的GUID
		model.addAttribute("guid", GUID.get());
		return "survey/create";
	}
	
	/**
	 * 跳转用户结果查看页面
	 */
	@RequestMapping("home_showUserAnswers/{qNRId}")
	public String getUser(Model model, @PathVariable(value = "qNRId") String qNRId) {
		// 规定即将创建的问卷的GUID
		model.addAttribute("qNRId", qNRId);
		Questionnaire QNR = surveyService.retriveQNR(qNRId);
		model.addAttribute("qNRName", QNR.getName());
		model.addAttribute("qNRType", QNR.getType());
		return "survey/userAnswers";
	}

	/**
	 * 跳转结果统计页面
	 */
	@RequestMapping("statistics")
	public String qstatistics() {
		return "survey/statistics";
	}

	/**
	 * 跳转问卷模板管理页面
	 */
	@RequestMapping("template")
	public String qcreate() {
		return "survey/create_questionaire";
	}

	/**
	 * 跳转问卷发布页面
	 */
	@RequestMapping("publish")
	public String qpublish() {
		return "survey/publish";
	}

	/**
	 * 跳转问卷回答页面
	 */
	@RequestMapping("answer")
	public String answer(HttpSession session, Model model) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();

		model.addAttribute("userId", user.getId());
		return "survey/answer";
	}

	/*
	 * @RequestMapping("qeditaddquestion") public String qaddquestion() { return
	 * "survey/add_question"; }
	 */

	@RequestMapping("qcreate")
	public String qcreateuestion() {
		return "survey/create_questionaire";
	}

	@RequestMapping("qcreate_blank")
	public String qblank() {
		return "survey/select_blank";
	}

	@RequestMapping("qcreate_template")
	public String qtemplate() {
		return "survey/select_template";
	}

	@RequestMapping("home_saveAnswer")
	public String saveAnswer(@RequestBody List<Answer> answerList,
			HttpSession session) {
		// System.out.println(answer);
		try {
			surveyService.saveAnswer(answerList);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}

	}

	@RequestMapping("create/setLogicDialog")
	public String setLogicDialog() {
		return "survey/setLogicDialog";
	}

	@RequestMapping("user")
	public String user() {
		return "survey/survey_user";
	}

	@RequestMapping("create/save/{id}")
	@ResponseBody
	public Boolean save(@PathVariable(value = "id") String id,
			@RequestBody UtilDataFromJSP data, HttpSession session) {
		Questionnaire qnr = surveyService.retriveQNR(id);
		String path = null;
		if (null != qnr) {
			path = qnr.getPath();
			surveyService.delQNR(path, id);
		}

		List<QInfo> qInfo = Arrays.asList(data.getqInfo());
		String paperName = data.getPaperName();
		String paperIntro = data.getPaperIntro();
		String type = data.getType();
		List<SurveyXMLQuestion> qList = UtilCommon.extract(qInfo, paperName,
				paperIntro);
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		AttachmentHelper ah = attachmentService.getQuestionnairHelper(id, user.getId(), AttachmentType.SURVEY);
		
		String projPath = session.getServletContext().getRealPath("/");
		String FTPPath = ah.getPath();
		System.out.println(FTPPath);
		try {
			surveyService.createQNR(id, qList, paperName, paperIntro, type,
					projPath, projPath, Arrays.asList(data.getLogicArr()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@RequestMapping("create/preview/{id}")
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

	@RequestMapping("create/delete/{id}")
	@ResponseBody
	public Boolean delete(@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		String path = surveyService.retriveQNR(id).getPath();
		return this.surveyService.delQNR(path, id);
	}

	/**
	 * 想要修改一个问卷，需要以下步骤 1、获得一个和编辑问卷一模一样的modify_question.jsp
	 * 2、modify_questoin.jsp会发送一个请求，获得后台问卷对应modify.jsp的数据
	 * 3、modify_question.jsp会发送第二个请求，获得后台问卷对应的题目和简介
	 * 4、modify_question.jsp通过js吧第2步和第3步的数据拼接到前台
	 */
	@RequestMapping("create/update/{id}")
	public String update(@PathVariable(value = "id") String id, Model model,
			HttpServletResponse response, HttpSession session) {
		// 规定即将创建的问卷的GUID
		model.addAttribute("guid", id);
		return "survey/create";
	}

	@RequestMapping("create/publishing/{id}")
	@ResponseBody
	public Boolean publishing(@PathVariable(value = "id") String id,
			HttpServletResponse response, HttpSession session) {
		String quesPath = surveyService.retriveQNR(id).getPath();
		String name = surveyService.retriveQNR(id).getName();
		String type = surveyService.retriveQNR(id).getType();
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		AttachmentHelper ah = attachmentService.getQuestionnairHelper(id, user.getId(), AttachmentType.SURVEY);
		
		String projPath = session.getServletContext().getRealPath("/");
		String FTPPath = ah.getPath();
		System.out.println(FTPPath);
		try {
			surveyService.createTemplate(id, name, quesPath, type, projPath,
					FTPPath);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@RequestMapping("create/updateGetContent/{id}")
	public void updateGetContent(@PathVariable(value = "id") String id,
			Model model, HttpServletResponse response, HttpSession session) {
		// 规定即将创建的问卷的GUID
		model.addAttribute("guid", id);
		String projPath = session.getServletContext().getRealPath("/");
		String sep = File.separator;
		/*String filePath = projPath + "WEB-INF" + sep + "classes" + sep + "com"
				+ sep + "dsep" + sep + "util" + sep + "survey" + sep + id + sep
				+ "update.jsp";*/
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		AttachmentHelper ah = attachmentService.getQuestionnairHelper(id, user.getId(), AttachmentType.SURVEY);
		String FTPPath = ah.getPath();
		String filePath = FTPPath + sep + id + sep + "update.jsp";
		BufferedReader br = null;
		String line = null;
		String questionContent = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					filePath), "utf-8"));
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
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	@RequestMapping("create/updateGetPaperNameAndTitle/{guid}")
	@ResponseBody
	public String previewGetPaperNameAndTitle(
			@PathVariable(value = "guid") String guid, Model model,
			HttpServletResponse response, HttpSession session) {
		// 规定即将创建的问卷的GUID
		model.addAttribute("guid", guid);
		String projPath = session.getServletContext().getRealPath("/");
		String sep = File.separator;
		/*String paperNameFilePath = projPath + "WEB-INF" + sep + "classes" + sep
				+ "com" + sep + "dsep" + sep + "util" + sep + "survey" + sep
				+ guid + sep + "paperNameForUpdate.dat";

		String paperIntroFilePath = projPath + "WEB-INF" + sep + "classes"
				+ sep + "com" + sep + "dsep" + sep + "util" + sep + "survey"
				+ sep + guid + sep + "paperIntroForUpdate.dat";*/
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		AttachmentHelper ah = attachmentService.getQuestionnairHelper(guid, user.getId(), AttachmentType.SURVEY);
		String FTPPath = ah.getPath();
		String paperNameFilePath = FTPPath + sep + guid + sep + "paperNameForUpdate.jsp";
		String paperIntroFilePath = FTPPath + sep + guid + sep + "paperIntroForUpdate.jsp";

		BufferedReader paperNameBr = null, paperIntroBr = null;
		String paperNameLine = null, paperIntroLine = null;
		String paperName = "", paperIntro = "";
		try {
			// paperNameBr读取问卷题目开始
			paperNameBr = new BufferedReader(new InputStreamReader(
					new FileInputStream(paperNameFilePath), "utf-8"));
			paperNameLine = paperNameBr.readLine();
			while (paperNameLine != null) {
				paperName += paperNameLine;
				paperNameLine = paperNameBr.readLine();
			}
			paperNameBr.close();
			// paperNameBr读取问卷题目结束

			// paperNameBr读取问卷说明开始
			paperIntroBr = new BufferedReader(new InputStreamReader(
					new FileInputStream(paperIntroFilePath), "utf-8"));
			paperIntroLine = paperIntroBr.readLine();
			while (paperIntroLine != null) {
				paperIntro += paperIntroLine;
				paperIntroLine = paperIntroBr.readLine();
			}
			paperIntroBr.close();
			// paperNameBr读取问卷说明结束

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

		System.out.println("{'paperName':" + paperName + ",'paperIntro':"
				+ paperIntro + "}");
		return paperName + "|||" + paperIntro;
	}

	@RequestMapping("test")
	public String test() {
		return "survey/test";
	}
	
	@RequestMapping("home_review/{id}")
	public void review(@PathVariable(value = "id") String id,
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
	
	@RequestMapping("home_getAnswersByQNRIdAndUserId/{id}/{userId}")
	@ResponseBody
	public String getAnswers(@PathVariable(value = "id") String id,
			@PathVariable(value = "userId") String userId, HttpSession session) {
		List<Answer> list = surveyService.getAnswers(id, userId);
		String json = JsonConvertor.obj2JSON(list);
		return json;
	}

}
