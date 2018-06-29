package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalIndicWtDao;
import com.dsep.entity.expert.EvalIndicWt;
import com.dsep.util.expert.eval.QType;

public class EvalIndicWtDaoImpl extends
		DsepMetaDaoImpl<EvalIndicWt, String> implements EvalIndicWtDao {
	/**
	 */
	private String getEvalPaperTableName() {
		return super.getTableName("X", "EVAL_PAPER");
	}

	/**
	 */
	private String getEvalQuestionTableName() {
		return super.getTableName("X", "EVAL_QUESTION");
	}

	private String getEvalIndicWtTableName() {
		return super.getTableName("E", "INDIC_WT");

	}

	private String getPaperAndQuestionMiddleTable() {
		return super.getTableName("X", "PAPER_QUES");
	}

	@Override
	public List<EvalIndicWt> getByPaperId(String paperId) {
		/**
		 * 一个正确的sql应该如下所示
		 * select * from dsep_e_indic_wight_2013
		 * left join dsep_x_eval_question_2013 
		 * on dsep_x_eval_question_2013.index_item_id = dsep_e_indic_wight_2013.id 
		 * left join dsep_x_paper_ques_2013 
		 * on dsep_x_eval_question_2013.ID = dsep_x_paper_ques_2013.question_id 
		 * left join dsep_x_eval_paper_2013
		 * on dsep_x_eval_paper_2013.ID = dsep_x_paper_ques_2013.paper_id 
		 * where dsep_x_eval_paper_2013.ID = '376625E760E14DE6B853FF0128F82A43' and dsep_x_eval_question_2013.Q_TYPE='4' ;
		 */
		String sql = "select * from " + getEvalIndicWtTableName()
				+ " left join " + getEvalQuestionTableName() + " on "
				+ getEvalQuestionTableName() + ".SUB_QUESTION_ID = "
				+ getEvalIndicWtTableName() + ".id left join "
				+ getPaperAndQuestionMiddleTable() + " on "
				+ getEvalQuestionTableName() + ".ID = "
				+ getPaperAndQuestionMiddleTable() + ".question_id left join "
				+ getEvalPaperTableName() + " on " + getEvalPaperTableName()
				+ ".id = " + getPaperAndQuestionMiddleTable()
				+ ".paper_id where " + getEvalPaperTableName() + ".ID = '"
				+ paperId + "' and " + getEvalQuestionTableName()
				+ ".Q_TYPE = " + QType.INDIC_WT.toInt();
		return super.sqlFind(sql);
	}

	@Override
	public List<EvalIndicWt> getIndicatorWeightItemByDiscCatId(
			String discCatId) {
		//不用管PaperId，直接在Weight表中取出所有需要的值即可
		String sql = "select * from " + getEvalIndicWtTableName() + " where "
				+ getEvalIndicWtTableName() + ".DISC_CAT_ID = '" + discCatId
				+ "'";
		return super.sqlFind(sql);
	}

}
