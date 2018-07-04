package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.Expert;
import com.dsep.util.expert.eval.QType;

public class EvalQuestionDaoImpl extends DsepMetaDaoImpl<EvalQuestion, String>
		implements EvalQuestionDao {
	/**
	 * 打分题干表
	 */
	private String getEvalQuestionTableName() {
		return super.getTableName("X", "EVAL_QUESTION");
	}

	/**
	 * 打分试卷表
	 */
	private String getEvalPaperTableName() {
		return super.getTableName("X", "EVAL_PAPER");
	}

	/**
	 * 获得打分卷子表和打分题干表的中间表
	 */
	private String getPaperAndQuestionMiddleTable() {
		return super.getTableName("X", "PAPER_QUES");
	}

	/**
	 * 用sql不能做表连接的翻页，改用hql
	 * 
	 * 一个正确的sql应该如下所示 select * from dsep_x_eval_question_2013 q left join
	 * dsep_x_paper_ques_2013 pq on q.ID = pq.question_id left join
	 * dsep_x_eval_paper_2013 p on p.ID = pq.paper_id where p.ID =
	 * '376625E760E14DE6B853FF0128F82A43';
	 */
	private String hqlLeftJoinQAndPQAndP() {
		return "from EvalQuestion q inner join fetch q.evalPapers p";
	}

	@Override
	public void deleteMetaQuestionsForOneBatch(String batchId) {
		String hql = "delete from EvalQuestion as q inner join q.evalPapers paper where paper.evalBatch.id='"
				+ batchId + "' and q.isMeta=1";
		super.hqlBulkUpdate(hql);
	}

	@Override
	public EvalQuestion getEvalQuestionById(String id) {
		return super.get(id);
	}

	@Override
	public List<EvalQuestion> getIndicIdxQs(String paperId, String discId,
			Boolean includeMetaQ) {
		String hql = hqlLeftJoinQAndPQAndP();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != paperId) {
			params.add(paperId);
			conditionColumns.add("p.id");
		}
		if (null != discId) {
			params.add(discId);
			conditionColumns.add("q.discId");
		}
		if (null != includeMetaQ) {
			params.add(includeMetaQ);
			conditionColumns.add("q.isMeta");
		}
		params.add(QType.INDIC_IDX.toInt());
		conditionColumns.add("q.QType");
		hql += super.hqlAndCondtion(conditionColumns);

		return super.hqlFind(hql, params.toArray());
	}

	@Override
	public List<EvalQuestion> getIndicWtQs(String paperId, String discId,
			Boolean includeMetaQ) {
		String hql = hqlLeftJoinQAndPQAndP();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != paperId) {
			params.add(paperId);
			conditionColumns.add("p.id");
		}
		if (null != discId) {
			params.add(discId);
			conditionColumns.add("q.discId");
		}
		if (null != includeMetaQ) {
			params.add(includeMetaQ);
			conditionColumns.add("q.isMeta");
		}
		params.add(QType.INDIC_WT.toInt());
		conditionColumns.add("q.QType");
		hql += super.hqlAndCondtion(conditionColumns);

		return super.hqlFind(hql, params.toArray());
	}

	@Override
	public List<EvalQuestion> getRepuQs(String paperId, String discId,
			Boolean includeMetaQ) {
		String hql = hqlLeftJoinQAndPQAndP();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != paperId) {
			params.add(paperId);
			conditionColumns.add("p.id");
		}
		if (null != discId) {
			params.add(discId);
			conditionColumns.add("q.discId");
		}
		if (null != includeMetaQ) {
			params.add(includeMetaQ);
			conditionColumns.add("q.isMeta");
		}
		params.add(QType.REPU.toInt());
		conditionColumns.add("q.QType");
		hql += super.hqlAndCondtion(conditionColumns);

		return super.hqlFind(hql, params.toArray());
	}

	@Override
	public List<EvalQuestion> getIndexQuestions(String expertId,
			List<String> indexItemIds) {
		String sql = " select * from " + getEvalQuestionTableName() + " where ";
		for (int i = 0; i < indexItemIds.size(); i++) {
			sql += " SUB_QUESTION_ID = '" + indexItemIds.get(i) + "'";
			sql += (i < indexItemIds.size() - 1) ? " or " : "";
		}
		return super.sqlFind(sql);
	}

	public EvalQuestion getQuestionByIndexItemId(String indexItemId,
			String expertTypeId) {
		String sql = "select * from " + getEvalQuestionTableName()
				+ " left join " + getPaperAndQuestionMiddleTable() + " on "
				+ getEvalQuestionTableName() + ".ID = "
				+ getPaperAndQuestionMiddleTable() + ".QUESTION_ID "
				+ " left join " + getEvalPaperTableName() + " on "
				+ getEvalPaperTableName() + ".ID = "
				+ getPaperAndQuestionMiddleTable() + ".PAPER_ID " + " where "
				+ getEvalQuestionTableName() + ".INDEX_ITEM_ID = '"
				+ indexItemId + "'" + " and " + getEvalPaperTableName()
				+ ".EXPERT_TYPE_ID = '" + expertTypeId + "'";
		List<EvalQuestion> questions = super.sqlFind(sql);
		if (questions.size() != 0) {
			return questions.get(0);
		} else
			return null;

	}

	@Override
	public List<EvalQuestion> getDistinctQuestionsByPaper(EvalPaper evalPaper,
			Expert expert) {
		String discId = (expert.getDiscId() != null) ? expert.getDiscId()
				: expert.getDiscId2();
		String sql = "select * from " + getEvalQuestionTableName() + " q "
				+ " left join " + getPaperAndQuestionMiddleTable() + " on "
				+ getEvalQuestionTableName() + ".id = "
				+ getPaperAndQuestionMiddleTable() + ".question_id "
				+ " left join " + getEvalPaperTableName() + " on "
				+ getEvalPaperTableName() + ".id="
				+ getPaperAndQuestionMiddleTable() + ".paper_id " + " where "
				+ getEvalPaperTableName() + ".BATCH_ID='"
				+ evalPaper.getEvalBatch().getId() + "'" + " and "
				+ getEvalPaperTableName() + ".DISC_ID='" + discId + "'"
				+ " and " + getEvalPaperTableName() + ".expert_type_id='"
				+ expert.getExpertType() + "'";

		return super.sqlFind(sql);
	}

	@Override
	public List<EvalQuestion> getAchvQsByPage(String paperId, String discId,
			String subQuestionId, int pageIndex, int pageSize) {
		String hql = hqlLeftJoinQAndPQAndP();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != paperId) {
			params.add(paperId);
			conditionColumns.add("p.id");
		}
		if (null != discId) {
			params.add(discId);
			conditionColumns.add("q.discId");
		}
		if (null != subQuestionId) {
			params.add(subQuestionId);
			conditionColumns.add("q.subQuestionId");
		}
		params.add(QType.ACHV.toInt());
		conditionColumns.add("q.QType");
		hql += super.hqlAndCondtion(conditionColumns);

		// 用SQL会报列名无效错误，因为底层绑定了entityClass
		return super.hqlPage(hql, pageIndex, pageSize, params.toArray());
	}

	@Override
	public List<EvalQuestion> getAllAchvQs(String paperId, String discId,
			String subQuestionId) {
		String hql = hqlLeftJoinQAndPQAndP();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (null != paperId) {
			params.add(paperId);
			conditionColumns.add("p.id");
		}
		if (null != discId) {
			params.add(discId);
			conditionColumns.add("q.discId");
		}
		if (null != subQuestionId) {
			params.add(subQuestionId);
			conditionColumns.add("q.subQuestionId");
		}
		params.add(QType.ACHV.toInt());
		conditionColumns.add("q.QType");
		hql += super.hqlAndCondtion(conditionColumns);
		return super.hqlFind(hql, params.toArray());
	}

	@Override
	public List<EvalQuestion> getQuestionsByType(List<String> scoreTypes) {
		String sql = "select * from " + getEvalQuestionTableName() + " where ";
		for (int i = 0; i < scoreTypes.size(); i++) {
			sql += " Q_TYPE = '" + scoreTypes.get(i) + "'";
			sql += (i == (scoreTypes.size() - 1)) ? "" : " or ";
		}
		return super.sqlFind(sql);
	}

	@Override
	public List<EvalQuestion> getAllMetaQuestionsByPaperId(String paperId) {
		String sql = "select * from "
				+ getEvalQuestionTableName()
				+ " left join "
				+ getPaperAndQuestionMiddleTable()
				+ " on "
				+ getEvalQuestionTableName()
				+ ".ID = "
				+ getPaperAndQuestionMiddleTable()
				+ ".question_id left join "
				+ getEvalPaperTableName()
				+ " on "
				+ getEvalPaperTableName()
				+ ".ID = "
				+ getPaperAndQuestionMiddleTable()
				+ ".paper_id where "
				+ getEvalPaperTableName()
				+ ".ID = '"
				+ paperId
				+ "' and DSEP_X_EVAL_QUESTION_2013.is_meta=1 order by Q_Type, sub_question_id asc";
		return super.sqlFind(sql);
	}

}
