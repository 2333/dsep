package com.dsep.dao.dsepmeta.check;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.PubEntry;

/**
 * 公共库比对入口实体的DAO
 * @author Monar
 *
 */
public interface PubEntryDao extends DsepMetaDao<PubEntry, String>{
	/**
	 * 更改比对状态
	 * @param publibId
	 */
	abstract public void updatePubEntryState(String publibId, String userId);
	
	/**
	 * 返回公共库中文名字的
	 * @param publibIDs 公共库ID的
	 * @return
	 */
	abstract public PubEntry getPUBLIB_CHS_NAME(String publibID);
	/**
	 * 获得已经比对的公共库ID的list
	 * @return
	 */
	abstract public List<String> getCheckedPublib_ID();
}
