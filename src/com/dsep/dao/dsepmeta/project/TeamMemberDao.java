package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.TeamMember;

public interface TeamMemberDao extends DsepMetaDao<TeamMember, String> {
	public abstract List<TeamMember> page(String applyItemId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract int Count(String applyItemId);
}
