package com.dsep.dao.dsepmeta.publicity.objection;

import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.OriginalObjection;

public interface OriginalObjectionDao extends
		DsepMetaDao<OriginalObjection, String> {



	/**
	 * 学校提交所有异议
	 * @param publicityRoundId 公示轮次ID
	 * @param unitId 学校ID
	 */
	public int submitObjectionByUnit(String publicityRoundId,String unitId);

	/**
	 * 学位中心处理完所有异议
	 * @param publicityRoundId
	 * @return
	 */
	public int processAllObjection(String publicityRoundId);
	
}
