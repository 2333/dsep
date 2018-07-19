package com.dsep.dao.dsepmeta.check;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.PubTabConfig;

/**
 * 公共库比对表映射表实体Dao
 * @author Monar
 *
 */
public interface PubTableConfigDao extends DsepMetaDao<PubTabConfig, String>{

	/**
	 * 获取公共库pubLibId对应的表ID
	 * @param pubLibId
	 * @return
	 */
	abstract public String getId(String pubLibId);
	
}
