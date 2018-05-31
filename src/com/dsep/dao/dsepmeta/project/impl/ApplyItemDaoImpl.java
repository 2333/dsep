package com.dsep.dao.dsepmeta.project.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.ApplyItemDao;
import com.dsep.entity.project.ApplyItem;
import com.dsep.util.project.ApplyItemStatus;

public class ApplyItemDaoImpl extends DsepMetaDaoImpl<ApplyItem, String>
		implements ApplyItemDao {

	private String getTableName() {
		return super.getTableName("P", "APPLY_ITEM");
	}

	private String getProjectTableName() {
		return super.getTableName("P", "PROJECT");
	}
	
	private String getMidTableName() {
		return super.getTableName("P", "ITEM_TEACHER");
	}

	@Override
	public List<ApplyItem> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}

	@Override
	public List<ApplyItem> retrive(String projectId, ApplyItemStatus status,
			String unitId, String discId, String userId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		StringBuilder hql = new StringBuilder("from ApplyItem applyItem ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(projectId)) {
			params.add(projectId);
			conditionColumns.add("applyItem.schoolProject.id");
		}
		if (status != null) {
			params.add(status.toInt());
			conditionColumns.add("applyItem.currentState");
		}
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("applyItem.unitId");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("applyItem.discId");
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("applyItem.userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlPage(hql.toString(), pageIndex, pageSize, desc,
				orderProperName, params.toArray());
	}

	@Override
	public List<ApplyItem> getApplyItemsByProjectId(String projectId) {
		String sql = "select * from " + getTableName() + " item left join "
				+ getProjectTableName()
				+ " project on item.PROJECT_ID = project.ID where project.ID='"
				+ projectId + "'";
		return super.sqlFind(sql);
	}

	@Override
	public int count(String projectId, ApplyItemStatus status, String unitId,
			String discId, String userId) {
		StringBuilder hql = new StringBuilder(
				"select count(*) from ApplyItem applyItem");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(projectId)) {
			params.add(projectId);
			conditionColumns.add("applyItem.schoolProject.id");
		}
		if (status != null) {
			params.add(status.toInt());
			conditionColumns.add("applyItem.currentState");
		}
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("applyItem.unitId");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("applyItem.discId");
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("applyItem.userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));

		return super.hqlCount(hql.toString(), params.toArray());
	}

	@Override
	public List<ApplyItem> retrive2Status(String projectId,
			ApplyItemStatus status1, ApplyItemStatus status2, String unitId,
			String discId, String userId, int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub

		List<ApplyItem> list = new ArrayList<ApplyItem>();
		List<ApplyItem> list1 = this.retrive(projectId, status1, unitId,
				discId, userId, pageIndex, pageSize, desc, orderProperName);
		List<ApplyItem> list2 = this.retrive(projectId, status2, unitId,
				discId, userId, pageIndex, pageSize, desc, orderProperName);
		for (ApplyItem item : list1) {
			list.add(item);
		}
		for (ApplyItem item : list2) {
			list.add(item);
		}
		return list;
	}

	@Override
	public int updateApplyItemStatus(String id, ApplyItemStatus status) {
		String hql = "update ApplyItem as item set item.currentState = "
				+ status.toInt() + " where item.id='" + id + "'";
		return super.hqlBulkUpdate(hql);
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
	public void deleteById(String itemId) {
		String sql  = "delete from " + getTableName() + " where ID='" + itemId + "'";
		String sql2 = "delete from " + getMidTableName() + " where ITEM_ID='" + itemId + "'"; 
		super.sqlBulkUpdate(sql);
		super.sqlBulkUpdate(sql2);
	}
}
