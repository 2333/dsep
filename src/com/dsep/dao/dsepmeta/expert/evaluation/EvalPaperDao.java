package com.dsep.dao.dsepmeta.expert.evaluation;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalPaper;

/**
 * 专家打分项配置信息的数据操作
 * @author pangeneral
 *
 */
public interface EvalPaperDao extends DsepMetaDao<EvalPaper,String>{
	
	/**
	 * 删除某个批次下所有的元paper，此接口用于生成实际Paper使用
	 * 当生成实际Paper之后，应该删除元Paper
	 */
	public void deleteMetaPapersForOneBatch(String batchId);
	/**
	 * 根据学科门类和专家编号获取相应的打分项
	 * @param expertType 专家类型
	 * @param discCategory 学科门类编号
	 * @return 
	 */
	/*public EvalPaper getEvalPaper(String expertType, String discCategory);*/
	
	/**
	 * 根据专家类型、专家所评学科id和批次获得Paper
	 */
	public EvalPaper getEvalPaper(String expertType, String discCatId, String batchId);
	
	/**
	 * 根据学科号获取学科门类编号
	 * @param discCode 学科号
	 * @return 学科门类编号
	 */
	public String getDiscCategoryByDiscCode(String discCode);
}
