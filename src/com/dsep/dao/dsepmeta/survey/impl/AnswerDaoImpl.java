package com.dsep.dao.dsepmeta.survey.impl;

import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.survey.AnswerDao;
import com.dsep.entity.survey.Answer;

public class AnswerDaoImpl extends DsepMetaDaoImpl<Answer, String> implements AnswerDao {
	private String getTableName() {
		return "DSEP_Q_ANSWER_2013";
	}

	@Override
	public List<Answer> getAnswers(String qNRId, String userId) {
		String sql = "select * from " + getTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (qNRId != null) {
			params.add(qNRId); // 参数
			conditionColumns.add("QID");// 查询条件
		}
		if (userId != null) {
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object[] array = params.toArray(new Object[params.size()]);

		return super.sqlFind(sql, array);
	}

	@Override
	public void deleteAnswers(String qNRId, String userId) {
		String sql = "delete from " + getTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (qNRId != null) {
			params.add(qNRId); // 参数
			conditionColumns.add("QID");// 查询条件
		}
		if (userId != null) {
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object[] array = params.toArray(new Object[params.size()]);

		super.sqlBulkUpdate(sql, array);
	}

}
