package com.dsep.service.project.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.project.TeamMemberDao;
import com.dsep.entity.project.TeamMember;
import com.dsep.service.project.TeamMemberService;
import com.dsep.vm.PageVM;

public class TeamMemberServiceImpl implements TeamMemberService {
	private TeamMemberDao teamMemberDao;

	@Override
	public PageVM<TeamMember> page(String applyItemId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		// 获得所有teamMembers
		List<TeamMember> teamMembers = teamMemberDao.page(applyItemId, pageIndex,
				pageSize, desc, orderProperName);
		// 总数
		int totalCount = teamMemberDao.Count(applyItemId);

		PageVM<TeamMember> result = new PageVM<TeamMember>(pageIndex,
				totalCount, pageSize, teamMembers);

		return result;
	}

	public TeamMemberDao getTeamMemberDao() {
		return teamMemberDao;
	}

	public void setTeamMemberDao(TeamMemberDao teamMemberDao) {
		this.teamMemberDao = teamMemberDao;
	}

}