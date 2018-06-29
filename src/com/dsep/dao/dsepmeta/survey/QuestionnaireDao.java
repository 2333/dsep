package com.dsep.dao.dsepmeta.survey;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.survey.Questionnaire;

public interface QuestionnaireDao extends Dao<Questionnaire, String> {
	public List<Questionnaire> page(Integer qNRStatus, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public int count(Integer qNRStatus);

	public void updateQNRStatus(String qNRId, Integer qNRStatus);
}
