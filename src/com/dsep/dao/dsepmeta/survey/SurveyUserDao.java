package com.dsep.dao.dsepmeta.survey;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.SurveyUser;

public interface SurveyUserDao extends DsepMetaDao<SurveyUser, String> {
	public abstract List<SurveyUser> retriveUsers(String unitId, String discId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName);
}
