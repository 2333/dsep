package com.dsep.dao.dsepmeta.survey.impl;

import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.survey.QuestionnaireDao;
import com.dsep.entity.survey.Questionnaire;

public class QuestionnaireDaoImpl extends
		DsepMetaDaoImpl<Questionnaire, String> implements QuestionnaireDao {
	private String getTableName() {
		return "DSEP_Q_STORAGE";
	}

	@Override
	public List<Questionnaire> page(Integer qNRStatus, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {

		String sql = "select * from " + getTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (qNRStatus != null) {
			params.add(qNRStatus); // 参数
			conditionColumns.add("STATUS");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object[] array = params.toArray(new Object[params.size()]);

		return super.sqlPage(sql, pageIndex, pageSize, desc, orderProperName,
				array);
	}

	@Override
	public int count(Integer qNRStatus) {
		String sql = "select count(*) from " + getTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (qNRStatus != null) {
			params.add(qNRStatus); // 参数
			conditionColumns.add("STATUS");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object[] array = params.toArray(new Object[params.size()]);

		return super.sqlCount(sql, array);
	}

	@Override
	public void updateQNRStatus(String qNRId, Integer qNRStatus) {
		String sql = "update " + getTableName() + " set STATUS=" + qNRStatus;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (qNRId != null) {
			params.add(qNRId); // 参数
			conditionColumns.add("ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object[] array = params.toArray(new Object[params.size()]);
		super.sqlBulkUpdate(sql, array);
	}

}
