package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalAchvDao;
import com.dsep.entity.expert.EvalAchv;
import com.dsep.util.expert.eval.QType;

public class EvalAchvDaoImpl extends DsepMetaDaoImpl<EvalAchv, String>
		implements EvalAchvDao {
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

	private String getEvalAchvTableName() {
		return super.getTableName("E", "ACHV");

	}

	private String getPaperAndQuestionMiddleTable() {
		return super.getTableName("X", "PAPER_QUES");
	}

	@Override
	public List<EvalAchv> getByPaperId(String paperId) {
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
		String sql = "select distinct * from " + getEvalAchvTableName()
				+ " left join " + getEvalQuestionTableName() + " on "
				+ getEvalQuestionTableName() + ".SUB_QUESTION_ID = "
				+ getEvalAchvTableName() + ".id left join "
				+ getPaperAndQuestionMiddleTable() + " on "
				+ getEvalQuestionTableName() + ".ID = "
				+ getPaperAndQuestionMiddleTable() + ".question_id left join "
				+ getEvalPaperTableName() + " on " + getEvalPaperTableName()
				+ ".id = " + getPaperAndQuestionMiddleTable()
				+ ".paper_id where " + getEvalPaperTableName() + ".ID = '"
				+ paperId + "' and " + getEvalQuestionTableName()
				+ ".Q_TYPE = " + QType.ACHV.toInt();
		return super.sqlFind(sql);
	}

	@Override
	public List<EvalAchv> getByDiscCatId(String discCatId) {
		String sql = "select * from " + getEvalAchvTableName() 
				+ " where DISC_CAT_ID='" + discCatId + "' order by ID asc" ;
		return super.sqlFind(sql);
	}

}
