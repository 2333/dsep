package com.dsep.dao.dsepmeta.check;

import java.util.Date;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.LogicCheckIdentityResult;

public interface LogicCheckIdentityResultDao extends
		DsepMetaDao<LogicCheckIdentityResult, String> {

	public abstract boolean setNewIdentityId(String id, String userId, Date newDate);

	public abstract boolean whetherExistGUID(String userId);

	public abstract String getGUID(String userId);
	
	public abstract String getDate(String userId);
	
	public abstract void deleteByUserId(String userId);
}
