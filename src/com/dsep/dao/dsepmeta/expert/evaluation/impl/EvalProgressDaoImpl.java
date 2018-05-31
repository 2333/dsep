package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalProgressDao;
import com.dsep.entity.expert.EvalProgress;

public class EvalProgressDaoImpl extends DsepMetaDaoImpl<EvalProgress, String>
		implements EvalProgressDao {
	private String getEvalProgressTableName() {
		return super.getTableName("E", "EVAL_PROGRESS");
	}

	@Override
	public EvalProgress getEvalProgressByQuestionId(String expertId, String questionId) {
		String sql = "select * from " + getEvalProgressTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != expertId) {
			params.add(expertId);
			conditionColumns.add("EXPERT_ID");
		}
		if (null != questionId) {
			params.add(questionId); 				//参数
			conditionColumns.add("SUB_QUESTION_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		List<EvalProgress> list = super.sqlFind(sql, params);
		if (null == list)
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<EvalProgress> getEvalProgressByQuestionType(String expertId, Integer questionType) {
		String sql = "select * from " + getEvalProgressTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != expertId) {
			params.add(expertId);
			conditionColumns.add("EXPERT_ID");
		}
		if (null != questionType) {
			params.add(questionType); 				//参数
			conditionColumns.add("QUESTION_TYPE");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlFind(sql, params);
	}

	@Override
	public Integer getEarliestQ(String expertId) {
		String sql = "select * from " + getEvalProgressTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		// 指标、声誉和排名
		if (null != expertId) {
			params.add(expertId); 				//参数
			conditionColumns.add("EXPERT_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		sql += " and FINISHED_NUMBER = TOTAL_NUMBER ORDER BY SEQU DESC";
		List<EvalProgress> list = super.sqlFind(sql, params);
		if (0 == list.size()) {
			return 1;
		} else {
			return list.get(0).getSequ() + 1;
		}
	}

	@Override
	public EvalProgress getBySequ(String expertId, Integer sequ) {
		String sql = "select * from " + getEvalProgressTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		if (null != expertId) {
			params.add(expertId); 				//参数
			conditionColumns.add("EXPERT_ID");// 查询条件
		}
		
		if (null != sequ) {
			params.add(sequ); 				//参数
			conditionColumns.add("SEQU");// 查询条件
		} 
		sql += super.sqlAndConditon(conditionColumns);
		List<EvalProgress> list = super.sqlFind(sql, params);
		if (null == list) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public void updateQuestionProgress(String expertId, String questionId,
			Integer questionType, Integer alteredNumber) {
		String sql = "update " + getEvalProgressTableName()
				+ " set FINISHED_NUMBER = FINISHED_NUMBER + "
				+ alteredNumber;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		if (null != expertId) {
			params.add(expertId);
			conditionColumns.add("EXPERT_ID");
		}
		// 指标、声誉和排名
		if (null != questionId) {
			params.add(questionId); 				//参数
			conditionColumns.add("SUB_QUESTION_ID");// 查询条件
		}
		// 学科成果
		else if (null != questionType) {
			params.add(questionType); 				//参数
			conditionColumns.add("QUESTION_TYPE");// 查询条件
		} 
		sql += super.sqlAndConditon(conditionColumns);
		super.sqlBulkUpdate(sql, params.toArray(new Object[params.size()]));
	}

	@Override
	public void flushQuestoinProgress(String expertId, String questionId,
			Integer questionType, Integer newSetNumber) {
		String sql = "update " + getEvalProgressTableName()
				+ " set FINISHED_NUMBER = " + newSetNumber;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		if (null != expertId) {
			params.add(expertId);
			conditionColumns.add("EXPERT_ID");
		}
		// 指标、声誉和排名
		if (null != questionId) {
			params.add(questionId); 				//参数
			conditionColumns.add("SUB_QUESTION_ID");// 查询条件
		}
		// 学科成果
		else if (null != questionType) {
			params.add(questionType); 				//参数
			conditionColumns.add("QUESTION_TYPE");// 查询条件
		} 
		sql += super.sqlAndConditon(conditionColumns);
		super.sqlBulkUpdate(sql, params.toArray(new Object[params.size()]));
		
	}

}
