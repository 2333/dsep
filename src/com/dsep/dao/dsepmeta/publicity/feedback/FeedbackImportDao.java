package com.dsep.dao.dsepmeta.publicity.feedback;

import com.dsep.dao.common.DsepMetaDao;

public interface FeedbackImportDao extends DsepMetaDao{
	
	/**
	 * 导入某一批次的原始异议
	 * @return
	 */
	public int originalObjectionImport(String feedbackRoundId,String objectionRoundId);


	/**
	 * 导入公共库比对的错误数据
	 * @param feedbackRoundId
	 * @return
	 */
	public int pubLibraryImport(String feedbackRoundId);

	/**
	 * 导入查重的错误数据
	 * @param feedbackRoundId
	 * @return
	 */
	public int similarityImport(String feedbackRoundId);

}
