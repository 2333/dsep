package com.dsep.controller.expert.evaluation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.service.expert.evaluation.EvalRankService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.util.expert.eval.GUIDConvertor;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalQuestionVM;
import com.dsep.vm.expert.EvalRepuVM;

@Controller
@RequestMapping("evaluation")
public class EvalRankController {
	@Resource(name = "evalService")
	private EvalService evalService;
	
	@Resource(name = "evalRankService")
	private EvalRankService evalRankService;
	
	@RequestMapping("ranking")
	public String ranking(Model model, HttpSession session, HttpServletResponse response) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String userId =  user.getId();
		// 专家用户的的用户实体Id就是expertId
		String expertId = userId;
		session.setAttribute("expertId", expertId);
		model.addAttribute("expertId", expertId);
		
		// 一位专家只对应一道学科排名打分题目(针对多个学校有多个答案),此处是get(0)
		EvalQuestionVM question = null;
				//evalService.getQuestions(expertId,
				//Integer.valueOf(QuestionType.RANKING.toString())).get(0);
		session.setAttribute("ranking_question", question);
		
		String rankingQuestionId = GUIDConvertor.expertEvalGUIDEncode(question.getQuestionId());
		model.addAttribute("rankingQuestionId", rankingQuestionId);
		
		model.addAttribute("discId", user.getDiscId());
		
		return "Expert/evaluate_ranking";
	}
	
	@RequestMapping("/progress/rankingGetUnitList/{questionId}")
	@ResponseBody
	public String getUnitList(@PathVariable(value = "questionId") String questionId,
			HttpSession session) {
		String expertId = (String) session.getAttribute("expertId");
		// 把前台参数解码
		questionId = GUIDConvertor.expertEvalGUIDDecode(questionId);
		
		PageVM<EvalRepuVM> vmList = evalRankService
				.getAllUnitsRanking(questionId, expertId);

		String unitsJSON = JsonConvertor.obj2JSON(vmList.getGridData());
		System.out.println(unitsJSON);
		return unitsJSON;
	}
	
	@RequestMapping("progress/rankingUpdate/{questionId}/{resultId}/{oldPosition}/{newPosition}")
	public String update(@PathVariable(value = "questionId") String questionId,
			@PathVariable(value = "resultId") String resultId,
			@PathVariable(value = "oldPosition") String oldPosition,
			@PathVariable(value = "newPosition") String newPosition,
			HttpSession session) {
		String expertId = (String) session.getAttribute("expertId");
		// 把前台参数解码
		questionId = GUIDConvertor.expertEvalGUIDDecode(questionId);
		System.out.println("resultId:" + resultId);
		System.out.println(oldPosition + newPosition);
		evalRankService.updateAUnitRanking(questionId, resultId, expertId, oldPosition, newPosition);
		return "success";
	}
	
}




/*
 @RequestMapping("ranking")
public String ranking(Model model) {
	Map<String,String> collegeMap = new HashMap<String,String>();
	collegeMap.put("1", "北京大学");
	collegeMap.put("2", "清华大学");
	collegeMap.put("3", "北航");
	Map<String,String> disciplineMap = new HashMap<String,String>();
	disciplineMap.put("1", "软件学院");
	disciplineMap.put("2", "计算机学院");
	disciplineMap.put("3", "自动化学院");
	model.addAttribute("colleges", collegeMap);
	model.addAttribute("disciplines", disciplineMap);
	return "Expert/evaluate_ranking";
}

 * private static List<Ranking> listRepu = new ArrayList<Ranking>();

private static Ranking[] dataSource = new Ranking[50];

private void setDataSource(){
	for(int i=0;i < dataSource.length;i++){
		dataSource[i] = new Ranking();
		dataSource[i].setSchoolId("1000"+i);
		dataSource[i].setSchoolName("北京大学"+i);
		dataSource[i].setDisciplineId("083"+i);
		dataSource[i].setDisciplineName("软件学院"+i);
		dataSource[i].setBrief("简介"+i);
		dataSource[i].setEvaluate(i+"");
	}
}
@RequestMapping("ranking_getUnitList")
	@ResponseBody
	public String getUnitList(HttpServletRequest request) {
		setDataSource();
		for(int i=0;i < dataSource.length;i++){
			listRepu.add(dataSource[i]);
		}
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int count = dataSource.length;
		PageVM<Ranking> repuVM = new PageVM<Ranking>(page,count,pageSize,listRepu);
	    Map m = repuVM.getGridData();
	    String json = JsonConvertor.obj2JSON(m);
		return json;
	}
*/

class Ranking {
	private String schoolId;
	private String schoolName;
	private String disciplineId;
	private String disciplineName;
	private String evaluate;
	private String brief;
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getDisciplineId() {
		return disciplineId;
	}
	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}
	public String getDisciplineName() {
		return disciplineName;
	}
	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
}
