package com.dsep.dao.dsepmeta.expert.evaluation.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.entity.expert.EvalPaper;

public class EvalPaperDaoImpl extends DsepMetaDaoImpl<EvalPaper, String>
		implements EvalPaperDao {

	/**
	 * 打分试卷表
	 * 
	 * @return
	 */
	private String getEvalPaperTableName() {
		return super.getTableName("X", "EVAL_PAPER");
	}

	/**
	 * 学科门类和学科对应表表名
	 */
	private String getCatGeoryDiscTableName() {
		return super.getTableName("X", "CAT_DISC");
	}

	@Override
	public String getDiscCategoryByDiscCode(String discCode) {
		String sql = "select CAT_ID from " + getCatGeoryDiscTableName()
				+ " where DISC_ID = '" + discCode + "'";
		return super.GetShadowResult(sql).get(0);
	}

	/*
	 * @Override public EvalPaper getEvalPaper(String expertType, String
	 * discCategory) { String sql = "select * from " + getEvalPaperTableName();
	 * List<Object> params = new LinkedList<Object>(); List<String>
	 * conditionColumns = new LinkedList<String>();
	 * if(!StringUtils.isBlank(expertType)) { params.add(expertType); //参数
	 * conditionColumns.add("EXPERT_TYPE_ID");// 查询条件 }
	 * if(!StringUtils.isBlank(discCategory)){ params.add(discCategory); //参数
	 * conditionColumns.add("DISC_CAT_ID");//查询条件 } sql +=
	 * super.sqlAndConditon(conditionColumns); return
	 * super.sqlFind(sql,params).get(0); return super.GetShadowResult(sql); }
	 */

	@Override
	public void deleteMetaPapersForOneBatch(String batchId) {
		String hql = "delete from EvalPaper as paper where paper.evalBatch.id='"
				+ batchId + "' and paper.isMeta=1" ;
		super.hqlBulkUpdate(hql);
	}

	@Override
	public EvalPaper getEvalPaper(String expertType, String discCatId,
			String batchId) {
		// 这里需要改变
		String sql = "select * from " + getEvalPaperTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (!StringUtils.isBlank(expertType)) {
			params.add(expertType); // 参数
			conditionColumns.add("EXPERT_TYPE_ID");// 查询条件
		}
		if (!StringUtils.isBlank(discCatId)) {
			params.add(discCatId); // 参数
			conditionColumns.add("DISC_CAT_ID");// 查询条件
		}
		if (!StringUtils.isBlank(batchId)) {
			params.add(batchId); // 参数
			conditionColumns.add("BATCH_ID");// 查询条件
		}
		params.add(false);
		conditionColumns.add("IS_META");

		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlFind(sql, params).get(0);
	}
}
