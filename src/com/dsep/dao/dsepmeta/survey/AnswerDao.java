package com.dsep.dao.dsepmeta.survey;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.survey.Answer;

public interface AnswerDao extends Dao<Answer,String>{
	public List<Answer> getAnswers(String qNRId, String userId);
	
	public void deleteAnswers(String qNRId, String userId);
}
