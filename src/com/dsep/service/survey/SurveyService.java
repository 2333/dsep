package com.dsep.service.survey;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.survey.LogicArr;
import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.entity.User;
import com.dsep.entity.survey.Answer;
import com.dsep.entity.survey.Questionnaire;
import com.dsep.vm.PageVM;
import com.dsep.vm.survey.QuestionnaireVM;

// CRUD
@Transactional(propagation = Propagation.SUPPORTS)
public interface SurveyService {

	/**
	 * 
	 * @param QNRID
	 * @param list
	 * @param paperName
	 * @param paperIntro
	 * @param type
	 * @param projPath 系统的路径，用于获得一些问卷题目的模板Snippet
	 * @param FTPPath 存储的路径，用于存放最终生成的问卷和问卷模板等
	 * @param logicArr
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void createQNR(String QNRID, List<SurveyXMLQuestion> list,
			String paperName, String paperIntro, String type, String projPath,
			String FTPPath, List<LogicArr> logicArr) throws Exception;

	public abstract PageVM<QuestionnaireVM> retriveQNRs(Integer qNRStatus,
			int pageIndex, int pageSize, Boolean desc, String orderProperName);

	public abstract Questionnaire retriveQNR(String id);

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void saveAnswer(List<Answer> answerList);

	
	public List<Answer> getAnswers(String qNRId, String userId);
	/**
	 * 
	 * @param qNRId
	 * @param name
	 * @param path
	 * @param type
	 * @param projPath 系统的路径，用于获得一些问卷题目的模板Snippet
	 * @param FTPPath 存储的路径，用于存放最终生成的问卷和问卷模板等
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void createTemplate(String qNRId, String name, String path,
			String type, String projPath, String FTPPath) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract Boolean delQNR(String path, String id);
	
	// 获取问卷用户列表，这次只是模拟
	public abstract PageVM<User> getQNRUsers();
}
