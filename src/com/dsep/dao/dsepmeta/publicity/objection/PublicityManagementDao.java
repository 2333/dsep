package com.dsep.dao.dsepmeta.publicity.objection;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.PublicityManagement;

public interface PublicityManagementDao extends DsepMetaDao<PublicityManagement,String>{
	
	/**
	 * 将最近关闭的批次的标志位置为false
	 * @return
	 */
	public boolean changeRecentClose();
	
}
