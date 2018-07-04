package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.util.expert.eval.QType;

public class EvalResultDaoImpl extends DsepMetaDaoImpl<EvalResult, String>
		implements EvalResultDao {

	public EvalQuestionDao evalQuestionDao;

	private String getEvalResultTableName() {
		return super.getTableName("E", "EVAL_RESULT");
	}
	
	private String getExpertTableName() {
		return super.getTableName("E", "EXPERT_SELECTED");
	}

	private String getEvalQuestionTableName() {
		return super.getTableName("X", "EVAL_QUESTION");
	}

	private String getEvalPaperTableName() {
		return super.getTableName("X", "EVAL_PAPER");
	}

	private String getPaperQuestionMiddleTableName() {
		return super.getTableName("X", "PAPER_QUES");
	}

	private String getCatDiscTableName() {
		return super.getTableName("X", "CAT_DISC");
	}
	
	private String getIndicWtTableName(){
		return super.getTableName("E", "INDIC_WT");
	}

	@Override
	public void saveOrUpdate(EvalResult result, EvalQuestion question,
			Boolean isUpdate) {
		result.setEvalQuestion(question);
		question.getEvalResults().add(result);
		if (isUpdate) {
			super.saveOrUpdate(result);
		} else {
			super.save(result);
		}
	}

	@Override
	public List<EvalResult> getResultsByQId(String expertId, String questionId) {
		String sql = "select * from " + getEvalResultTableName()
				+ " where EXPERT_ID ='" + expertId + "' and EVAL_ITEM_ID='"
				+ questionId + "'";
		return super.sqlFind(sql);
	}

	@Override
	public void updateUnitRanking(String questionId, String expertId,
			String resultId, String oldPosition, String newPosition) {
		/**
		 * update dsep_e_eval_result_2013 a1 
		 * set a1.eval_value = to_char(to_number(a1.eval_value) + 1) 
		 * where a1.id =
		 * (select a2.id from dsep_e_eval_result_2013 a2
		 * left join dsep_x_eval_question_2013 a3
		 * on a2.eval_item_id = a3.id
		 * where a3.id = '1367B46CF8DA4E2F9B1E617360137B79' 
		 * and a1.id = a2.id 
		 * and to_number(a2.eval_value) > 9)"
		 */
		String sql = "";
		if (Integer.valueOf(oldPosition) < Integer.valueOf(newPosition)) {
			sql = "update "
					+ getEvalResultTableName()
					+ " a1 set a1.eval_value = to_char(to_number(a1.eval_value) - 1) "
					+ " where a1.id=" + " (select a2.id from "
					+ getEvalResultTableName() + " a2 " + " left join "
					+ getEvalQuestionTableName() + " a3 "
					+ " on a2.eval_item_id = a3.id " + " where a3.id = '"
					+ questionId + "' and a2.expert_id = '" + expertId
					+ "' and a1.id = a2.id "
					+ " and to_number(a2.eval_value) <= " + newPosition
					+ " and to_number(a2.eval_value) > " + oldPosition + ")";
		} else if (Integer.valueOf(oldPosition) > Integer.valueOf(newPosition)) {
			sql = "update "
					+ getEvalResultTableName()
					+ " a1 set a1.eval_value = to_char(to_number(a1.eval_value) + 1) "
					+ " where a1.id=" + " (select a2.id from "
					+ getEvalResultTableName() + " a2 " + " left join "
					+ getEvalQuestionTableName() + " a3 "
					+ " on a2.eval_item_id = a3.id " + " where a3.id = '"
					+ questionId + "' and a2.expert_id = '" + expertId
					+ "' and a1.id = a2.id "
					+ " and to_number(a2.eval_value) >= " + newPosition
					+ " and to_number(a2.eval_value) < " + oldPosition + ")";
		}
		super.sqlBulkUpdate(sql);
		sql = "update " + getEvalResultTableName()
				+ " a1 set a1.eval_value = to_char(" + newPosition
				+ ") where a1.id = '" + resultId + "'";

		super.sqlBulkUpdate(sql);
	}

	@Override
	public List<EvalResult> getResultsByQType(String expertId,
			Integer scoretType) {
		String sql = "select * from " + getEvalResultTableName()
				+ " r left join " + getEvalQuestionTableName() + " q on "
				+ "r.EVAL_ITEM_ID = q.ID  where r.EXPERT_ID ='" + expertId
				+ "' and q.Q_TYPE = " + scoretType;
		return super.sqlFind(sql);
	}

	@Override
	public List<EvalResult> getAchvResultsBySubQuestionId(String expertId,
			String subQuestionId) {
		String sql = "select * from " + getEvalResultTableName()
				+ " r left join " + getEvalQuestionTableName() 
				+ " q on r.EVAL_ITEM_ID = q.ID  ";
		//where r.EXPERT_ID ='" + expertId
		//+ "' and q.Q_TYPE=" + QType.ACHV.toInt()
		//+ " and q.SUB_QUESTION_ID = '" + subQuestionId + "'";
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != expertId) {
			params.add(expertId);
			conditionColumns.add("r.EXPERT_ID");
		}
		if (null != subQuestionId) {
			params.add(subQuestionId);
			conditionColumns.add("q.SUB_QUESTION_ID");
		}
		
		params.add(QType.ACHV.toInt());
		conditionColumns.add("q.Q_TYPE");
		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlFind(sql, params.toArray());
	}
	
	@Override
	public List<EvalResult> getSameDiscOrCatValue(String discId,String catId,
			String expertType,String subQuestionId){
		String sql = " select * from " + getEvalResultTableName() + " t "
				+ " left join " + getEvalQuestionTableName() + " t1 on "
				+ " t.EVAL_ITEM_ID=t1.ID "; 
		boolean bFirst = true;
		if(expertType!=null){
			sql += " left join " + getExpertTableName() + " t2 "
					+ " on t.EXPERT_ID=t2.ID where t2.EXPERT_TYPE='"
					+ expertType + "'";
			bFirst = false;
		}	
		if(catId!=null){
			sql += (bFirst)?" where ":" and ";
			sql += " t1.DISC_ID in (select DISC_ID from " + getCatDiscTableName() 
					+ " t3 where t3.CAT_ID='" + catId + "')";
			bFirst = false;
		}			
		if(discId!=null){
			sql += (bFirst)?" where ":" and ";
			sql += " t1.DISC_ID='" + discId + "'";
			bFirst = false;
		}	
		sql += (bFirst)?" where ":" and ";
		sql += " t1.SUB_QUESTION_ID='" + subQuestionId + "'";
		return super.sqlFind(sql);
	}
	
	@Override
	public List<EvalResult> getSameDiscIndicWtOrIndexValue(String discId,String expertType,
			int qType){
		String hql = " select t from EvalResult t ";
		hql += (expertType!=null)?",ExpertSelected r "
				+ " where t.expertId=r.id and r.expertType='" + expertType
				+ "' and ":" where ";
		hql += " t.evalQuestion.QType=" + qType + " and t.evalQuestion.discId='" 
		+ discId + "'";
		return super.hqlFind(hql);
	}
	
	@Override
	public void submit(String paperId, String expertId) {
		/*String sql = "UPDATE " + getEvalResultTableName() 
				+ " r SET r.EVAL_VALUE_STATE = '2' where exists (select * from " 
				+ getEvalResultTableName() + " r1 left join " 
				+ getEvalQuestionTableName() 
				+ " q on r1.EVAL_ITEM_ID = q.ID left join " 
				+ getPaperQuestionMiddleTableName() 
				+ " pq on pq.QUESTION_ID = q.ID left join " 
				+ getEvalPaperTableName() 
				+ " p on p.ID = pq.PAPER_ID  WHERE p.ID='" + paperId 
				+ "' AND r1.EXPERT_ID='" + expertId +"')";*/
		String sql = "UPDATE " + getEvalResultTableName()
				+ " r SET r.EVAL_VALUE_STATE = '2' where r.EXPERT_ID='"
				+ expertId + "'";
		super.sqlBulkUpdate(sql);
	}
	
	@Override
	public List<EvalResult> getSameUnitValueBySubQuestionId(String subQuestionId,String discId,
			String unitId){
		String hql = " select t from EvalResult t where t.evalQuestion.discId='" 
			+ discId + "' and " + " t.evalQuestion.unitId='" + unitId + "'";
		hql += (subQuestionId.equals(""))?" and t.evalQuestion.QType=" + 3:" and "
			+ " t.evalQuestion.subQuestionId='" + subQuestionId + "'";
		return super.hqlFind(hql);
	}

	// 与业务逻辑无关的getter和setter 
	public EvalQuestionDao getEvalQuestionDao() {
		return evalQuestionDao;
	}

	public void setEvalQuestionDao(EvalQuestionDao evalQuestionDao) {
		this.evalQuestionDao = evalQuestionDao;
	}

}
