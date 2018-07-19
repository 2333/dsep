package com.dsep.dao.dsepmeta.project.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.SchoolProjectDao;
import com.dsep.entity.project.SchoolProject;

public class SchoolProjectDaoImpl extends
		DsepMetaDaoImpl<SchoolProject, String> implements SchoolProjectDao {

	private String getTableName() {
		return super.getTableName("P", "PROJECT");
	}

	@Override
	public List<SchoolProject> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}

	@Override
	public SchoolProject retrive(Object conditions) {
		// empty function

		return null;
	}

	@Override
	public void deleteAttachment(String projectId) {
		// TODO Auto-generated method stub
		String attachmentId = "";
		String sql = "update " + getTableName()
				+ " set attachment_id = ? where id = ?";
		super.sqlBulkUpdate(sql, new Object[]{attachmentId , projectId});
	}

	@Override
	public String getAttachmentId(String projectId) {
		// TODO Auto-generated method stub
		String sql = "select attachment_id from " + getTableName()
				+ " where id = '" + projectId + "'";
		return (String) super.sqlUniqueResult(sql);
	}

	@Override
	public List<String> getUniqueProjectTypes(String unitId) {
		String sql = "select distinct project_type from " + getTableName();
		if (StringUtils.isNotBlank(unitId)) {
			sql += " where UNIT_ID='" + unitId + "'";
		}
		return super.GetShadowResult(sql);
	}

	@Override
	public List<SchoolProject> pageFind(String unitId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		String hql = " from SchoolProject where unit_id = ?";
		Object[] values = new Object[] { unitId };
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName,
				values);
	}

	@Override
	public List<SchoolProject> pageFind(String unitId, String projectType,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {
		StringBuilder sql = new StringBuilder("select * from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		if (StringUtils.isNotBlank(projectType)) {
			params.add(projectType);
			conditionColumns.add("PROJECT_TYPE");
		}
		sql.append(super.sqlAndConditon(conditionColumns));
		return super.sqlPage(sql.toString(), pageIndex, pageSize, desc,
				orderProperName, params.toArray());
	}

	@Override
	public int count(String unitId, String projectType) {
		StringBuilder sql = new StringBuilder("select count(*) from "
				+ getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		if (StringUtils.isNotBlank(projectType)) {
			params.add(projectType);
			conditionColumns.add("PROJECT_TYPE");
		}
		sql.append(super.sqlAndConditon(conditionColumns));

		return super.sqlCount(sql.toString(), params.toArray());
	}

	@Override
	public Boolean closeProject(String projectId) {
		// TODO Auto-generated method stub
		String sql = "update " + getTableName()
				+ " set commit_state = '否' where id = '" + projectId + "'";
		return super.sqlBulkUpdate(sql) > 0 ? true : false;
	}

	@Override
	public Boolean restartProject(String projectId) {
		// TODO Auto-generated method stub
		String sql = "update " + getTableName()
				+ " set commit_state = '是' where id = '" + projectId + "'";
		return super.sqlBulkUpdate(sql) > 0 ? true : false;
	}

	@Override
	public List<SchoolProject> pageFind(String unitId, int pageIndex, int pageSize, boolean desc, String orderProperName,
			String projectName, String projectType, int currentState) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		if (StringUtils.isNotBlank(projectType) && !projectType.equals("全部")) {
			params.add(projectType);
			conditionColumns.add("PROJECT_TYPE");
		}
		if (StringUtils.isNotBlank(projectName)) {
			params.add(projectName);
			conditionColumns.add("PROJECT_NAME");
		}
		if(currentState!=9)
		{
			params.add(currentState);
			conditionColumns.add("CURRENT_STATE");
		}
		sql.append(super.sqlAndConditon(conditionColumns));
		return super.sqlPage(sql.toString(), pageIndex, pageSize, desc,
				orderProperName, params.toArray());
	}

	@Override
	public int setProjectState(String projectId, int i) {
		// TODO Auto-generated method stub
		String sql =  "update " + getTableName()
				+ " set current_state = "+i+" where id = '" + projectId + "'";
		return super.sqlBulkUpdate(sql);
	}
}
