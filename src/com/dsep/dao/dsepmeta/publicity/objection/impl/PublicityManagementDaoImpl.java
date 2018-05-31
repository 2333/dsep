package com.dsep.dao.dsepmeta.publicity.objection.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.objection.PublicityManagementDao;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.publicity.PublicityStatus;
import com.dsep.entity.enumeration.publicity.RecentClose;

public class PublicityManagementDaoImpl extends DsepMetaDaoImpl<PublicityManagement,String>
	implements PublicityManagementDao{

	@Override
	public boolean changeRecentClose() {
		// TODO Auto-generated method stub
		String hql = "update PublicityManagement set recentClose = ?  where recentClose = ?";
		Object[] values = new Object[2];
		values[0] = RecentClose.NO.getStatus();
		values[1] = RecentClose.YES.getStatus();
		int result = super.hqlBulkUpdate(hql, values);
		if( result < 0)
			return false;
		else
			return true;
	}

}
