package com.dsep.service.survey.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.survey.AnswerDao;
import com.dsep.dao.dsepmeta.survey.QuestionnaireDao;
import com.dsep.dao.dsepmeta.survey.TemplateDao;
import com.dsep.dao.rbac.UserDao;
import com.dsep.domain.dsepmeta.survey.LogicArr;
import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.entity.User;
import com.dsep.entity.survey.Answer;
import com.dsep.entity.survey.QuesTemplate;
import com.dsep.entity.survey.Questionnaire;
import com.dsep.service.survey.SurveyService;
import com.dsep.util.GUID;
import com.dsep.util.survey.DeploymentDescriptor;
import com.dsep.util.survey.QNRStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.survey.QuestionnaireVM;

public class SurveyServiceImpl implements SurveyService {

	private QuestionnaireDao questionnaireDao;
	private AnswerDao answerDao;
	private TemplateDao templateDao;
	private UserDao userDao;

	@Override
	public void createQNR(String QNRId, List<SurveyXMLQuestion> list,
			String paperName, String paperIntro, String type, String projPath,
			String FTPPath, List<LogicArr> logicArr) throws Exception {
		// projPath是项目部署的路径
		String surveyTemplatePath = projPath
				+ DeploymentDescriptor.surveyTemplatePath;
		
		String surveyStoragePath = FTPPath
				+ DeploymentDescriptor.surveyStoragePath;
		Questionnaire QNR = new Questionnaire();
		//try {
			HTMLAndXMLGenerator generator = HTMLAndXMLGenerator
					.getSingleFreeMarker(surveyStoragePath, surveyTemplatePath);
			// 加上了该问卷GUID的存储路径
			String savePath = generator.generateXMLAndHTMLFiles(list, QNRId,
					logicArr);
			if (null == QNR.getId()) {
				QNR.setId(QNRId);
			}
			QNR.setName(paperName);
			QNR.setPath(savePath);
			QNR.setStartDate(null);
			QNR.setEndDate(null);
			QNR.setType(type);
			QNR.setStatus(QNRStatus.NOT_PUBLISHED.toInt());
			questionnaireDao.save(QNR);
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public PageVM<QuestionnaireVM> retriveQNRs(Integer qNRStatus,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {

		List<Questionnaire> list = questionnaireDao.page(qNRStatus, pageIndex,
				pageSize, desc, orderProperName);

		int totalCount = questionnaireDao.count(qNRStatus);
		List<QuestionnaireVM> vmList = new ArrayList<QuestionnaireVM>();
		for (Questionnaire u : list) {
			QuestionnaireVM vm = new QuestionnaireVM();
			vm.setQues(u);
			vmList.add(vm);
		}
		PageVM<QuestionnaireVM> result = new PageVM<QuestionnaireVM>(pageIndex,
				totalCount, pageSize, vmList);
		return result;
	}
	
	@Override
	public List<Answer> getAnswers(String qNRId, String userId) {
		return answerDao.getAnswers(qNRId, userId);
	}

	@Override
	public Questionnaire retriveQNR(String id) {
		return this.questionnaireDao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean delQNR(String path, String id) {
		this.questionnaireDao.deleteByKey(id);
		deleteFiles(new File(path));
		return true;
	}

	private void deleteFiles(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) { //目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) { //遍历目录所有文件
					this.deleteFiles(files[i]); //递归
				}
			}
			file.delete();
		} else {
			System.out.println("文件路径不存在");
		}
	}

	public QuestionnaireDao getQuestionnaireDao() {
		return questionnaireDao;
	}

	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}

	@Override
	public void saveAnswer(List<Answer> answerList) {
		if (answerList.size() == 0) {
			return;
		}
		// 如果answerList不为空，那么先删除该用户的该问卷原来的答案
		else {
			Answer answer = answerList.get(0);
			answerDao.deleteAnswers(answer.getQid(), answer.getUserid());
		}
		// 再保存新答案
		for (Answer answer : answerList) {
			answer.setGuid(GUID.get());
			answerDao.save(answer);
		}
	}

	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void createTemplate(String qNRId, String name, String path,
			String type, String projPath, String FTPPath) throws Exception {
		//模板存储的路径
		String surveyQNRTempaltePath = FTPPath
				+ DeploymentDescriptor.surveyQNRTempaltePath;

		QuesTemplate quesTemplate = new QuesTemplate();
		quesTemplate.setId(GUID.get());
		quesTemplate.setTitle(name);
		quesTemplate.setType(type);
		String resultPath = surveyQNRTempaltePath + File.separator
				+ quesTemplate.getId();
		File templateFile = new File(resultPath);
		if (!templateFile.exists() && !templateFile.isDirectory()) {
			templateFile.mkdirs();
		} else {
			System.out.println("目录存在！");
		}
		quesTemplate.setPath(resultPath);
		templateDao.save(quesTemplate);

		genereteTemplateJSP(path, resultPath);

		questionnaireDao.updateQNRStatus(qNRId, QNRStatus.PUBLISHED.toInt());
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public void genereteTemplateJSP(String path, String resultPath) {

		String line = null;

		BufferedReader br = null;
		BufferedWriter bw = null;

		path = path + File.separator + "real.html";

		//将问卷jsp复制到模板文件夹下的template.jsp中，作为模板
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultPath + File.separator
							+ "template.jsp"), "utf-8"));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					path), "utf-8"));
			line = br.readLine();
			while (line != null) {
				bw.write(line);
				bw.newLine();
				line = br.readLine();
			}
			br.close();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public PageVM<User> getQNRUsers() {
		// 模拟代码
		List<User> list = new ArrayList<User>();
		list.add(this.userDao.getByLoginId("1111@test.com"));
		list.add(this.userDao.getByLoginId("1112@test.com"));
		list.add(this.userDao.getByLoginId("1113@test.com"));

		int totalCount = 4;
		PageVM<User> result = new PageVM<User>(1, totalCount, 10, list);
		return result;
	}

}
