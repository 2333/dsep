package com.dsep.dao.dsepmeta.project.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.PassItemDao;
import com.dsep.entity.project.PassItem;

public class PassItemDaoImpl extends DsepMetaDaoImpl<PassItem, String>
		implements PassItemDao {

	private String getTableName() {
		return super.getTableName("P", "PASS_ITEM");
	}

	private String getProjectTableName() {
		return super.getTableName("P", "PROJECT");
	}

	@Override
	public List<PassItem> getPassItemByProjectId(String projectId) {
		// TODO Auto-generated method stub
		String sql = "select * from " + getTableName() + " where PROJECT_ID ='"
				+ projectId + "'";
		return super.sqlFind(sql);
	}

	@Override
	public List<PassItem> retrive(String projectId, Integer status,
			String unitId, String discId, String principalId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		StringBuilder hql = new StringBuilder("from PassItem passItem ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(projectId)) {
			params.add(projectId);
			conditionColumns.add("passItem.schoolProject.id");
		}
		if (status != null) {
			params.add(status);
			conditionColumns.add("passItem.itemState");
		}
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("passItem.unitId");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("passItem.discId");
		}
		if (StringUtils.isNotBlank(principalId)) {
			params.add(principalId);
			conditionColumns.add("passItem.userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlPage(hql.toString(), pageIndex, pageSize, desc,
				orderProperName, params.toArray());
	}

	@Override
	public Integer count(String projectId, Integer status, String unitId,
			String discId, String principalId) {
		StringBuilder hql = new StringBuilder(
				"select count(*) from PassItem passItem ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(projectId)) {
			params.add(projectId);
			conditionColumns.add("passItem.schoolProject.id");
		}
		if (status != null) {
			params.add(status);
			conditionColumns.add("passItem.itemState");
		}
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("passItem.unitId");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("passItem.discId");
		}
		if (StringUtils.isNotBlank(principalId)) {
			params.add(principalId);
			conditionColumns.add("passItem.userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlCount(hql.toString(), params.toArray());
	}

	@Override
	public PassItem getPassItemById(String passItemId) {
		// TODO Auto-generated method stub
		String sql = "select * from " + getTableName() + " where ID ='"
				+ passItemId + "'";
		return super.sqlFind(sql).get(0);
	}

}
