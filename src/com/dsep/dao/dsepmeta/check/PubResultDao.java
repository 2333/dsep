package com.dsep.dao.dsepmeta.check;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.PubResult;

/**
 * 公共库比对结果表实体Dao
 * @author Monar
 *
 */
public interface PubResultDao extends DsepMetaDao<PubResult, String>{
	
	/**
	 * 删除特定公共库的比对结果
	 * @param pubLibId
	 */
	abstract public void deletePubResultByPubId(String pubLibId);
	
	/**
	 * 查询特定公共库是否已经由比对结果
	 * @param pubLibId
	 * @return
	 */
	abstract public boolean hasPubResultByPubId(String pubLibId);
	
	/**
	 * 根据比对结果类型返回特定公共库比对结果
	 * @param pubLibId
	 * @param type
	 * @return
	 */
	abstract public List<PubResult> getPubResults(String pubLibId, String type,int pageIndex, int pageSize,
			boolean desc, String orderProperName);
	
	/**
	 * 将比对结果存入数据库
	 * @param results
	 */
	abstract public void saveCompareResult(String publibId, String entityId, List<Map<String, String>> results);
	
}
