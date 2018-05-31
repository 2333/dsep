package com.dsep.dao.dsepmeta.project.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.TeamMemberDao;
import com.dsep.entity.project.TeamMember;

public class TeamMemberDaoImpl extends DsepMetaDaoImpl<TeamMember, String>
		implements TeamMemberDao {

	private String getTableName() {
		return super.getTableName("P", "APPLY_ITEM");
	}

	private String getTeamMemberTableName() {
		return super.getTableName("P", "TEAM_MEMBER");
	}

	private String getMidTableName() {
		return super.getTableName("P", "APPLY_MEM");
	}

	@Override
	public List<TeamMember> page(String applyItemId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		StringBuilder sql = new StringBuilder("select * from "
				+ getTeamMemberTableName() + " t left join "
				+ getMidTableName() + " mid on t.ID = mid.MEMBER_ID ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(applyItemId)) {
			params.add(applyItemId);
			conditionColumns.add("mid.APPLY_ID");
		}
		sql.append(super.sqlAndConditon(conditionColumns));

		return super.sqlPage(sql.toString(), pageIndex, pageSize,
				 params.toArray());
	}

	@Override
	public int Count(String applyItemId) {
		StringBuilder sql = new StringBuilder("select count(*) from "
				+ getTeamMemberTableName() + " t left join "
				+ getMidTableName() + " mid on t.ID = mid.MEMBER_ID left join "
				+ getTableName() + " a on a.ID = mid.APPLY_ID");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(applyItemId)) {
			params.add(applyItemId);
			conditionColumns.add("a.ID");
		}
		sql.append(super.sqlAndConditon(conditionColumns));

		return super.sqlCount(sql.toString(), params.toArray());
	}
}
