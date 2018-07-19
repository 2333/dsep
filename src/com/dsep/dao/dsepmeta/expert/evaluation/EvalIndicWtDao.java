package com.dsep.dao.dsepmeta.expert.evaluation;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalIndicWt;

public interface EvalIndicWtDao extends
		DsepMetaDao<EvalIndicWt, String> {
	public List<EvalIndicWt> getByPaperId(String paperId);

	/**
	 * discCatId表示学科大类，如JSJ表示计算机
	 * 数据库中有一个字段标明
	 * 如果该字段是ALL，表明适用于所有学科
	 * 如果该字段是JSJ，表明适用于计算机学科
	 */
	public List<EvalIndicWt> getIndicatorWeightItemByDiscCatId(
			String discCatId);
}
