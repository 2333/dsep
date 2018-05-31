package com.dsep.dao.dsepmeta.check.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.LogicCheckAttrResultDao;
import com.dsep.entity.dsepmeta.LogicCheckAttrResult;
import com.dsep.util.GUID;

public class LogicCheckAttrResultDaoImpl extends
		DsepMetaDaoImpl<LogicCheckAttrResult, String> implements
		LogicCheckAttrResultDao {

	private String getTableName(){
		return super.getTableName("D", "logic_attr_result");
	}
	//private String tableName = super.getTableName("D", "logic_attr_result");

	@Override
	public List<LogicCheckAttrResult> getLogicCheckAttrResults(String unitId,
			String discId, String entityId, String userId, int pageIndex,
			int pageSize, boolean asc, String orderPropName) {
		StringBuilder sql = new StringBuilder("select * from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("unit_Id");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("disc_Id");
		}
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("entity_Id");
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("user_Id");
		}
		sql.append(super.sqlAndConditon(conditionColumns));
		return super.sqlPage(sql.toString(), pageIndex, pageSize, asc,
				orderPropName, params.toArray());
	}

	@Override
	public int getCount(String unitId, String discId, String entityId,
			String userId) {
		StringBuilder hql = new StringBuilder(
				"select count(*) from LogicCheckAttrResult ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("unitId");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("discId");
		}
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("entityId");
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlCount(hql.toString(), params.toArray());
	}

	@Override
	public void deleteCheckResultData(String unitId, String discId,
			String entityId, String userId) {
		StringBuilder hql = new StringBuilder("delete  from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("DISC_ID");
		}
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("ENTITY_ID");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		super.sqlBulkUpdate(hql.toString(), params.toArray());
	}

	public void newResultRow(Map<String, String> data) {
		String insertSql = this.buildInsertSql(getTableName(), data);
		super.sqlBulkUpdate(insertSql);
	}

	private String buildInsertSql(String entityName, Map<String, String> data) {
		StringBuilder valueSql = new StringBuilder("insert into " + entityName
				+ " values (");
		valueSql.append("'" + GUID.get() + "',");
		valueSql.append("'" + data.get("UNIT_ID") + "',");
		valueSql.append("'" + data.get("DISC_ID") + "',");
		valueSql.append("'" + data.get("ENTITY_ID") + "',");
		valueSql.append("'" + data.get("DATA_ID") + "',");
		valueSql.append("'" + data.get("SEQ_NO") + "',");
		valueSql.append("'" + data.get("ATTR_ID") + "',");
		valueSql.append("'" + data.get("ERROR_NO") + "',");
		valueSql.append("'" + data.get("ERROR") + "',");
		valueSql.append("'" + data.get("ENTITY_CHS_NAME") + "',");
		valueSql.append("'" + data.get("ATTR_CHS_NAME") + "'");
		valueSql.append(")");
		return valueSql.toString();
	}

	@Override
	public void saveLogicCheckAttrResult(LogicCheckAttrResult result) {
		super.save(result);
	}

	@Override
	public void saveLogicCheckAttrResults(List<LogicCheckAttrResult> results) {
		for (LogicCheckAttrResult result : results) {
			StringBuilder sql = new StringBuilder("insert into " + getTableName()
					+ " values (");
			sql.append("'" + GUID.get() + "',");
			sql.append("'" + result.getUnitId() + "',");
			sql.append("'" + result.getDiscId() + "',");
			sql.append("'" + result.getEntityId() + "',");
			sql.append("'" + result.getDataId() + "',");
			sql.append("'" + result.getSeqNo() + "',");
			sql.append("'" + result.getAttrId() + "',");
			sql.append("'" + result.getErrorType() + "',");
			sql.append("'" + result.getError() + "',");
			sql.append("'" + result.getEntityChsName() + "',");
			sql.append("'" + result.getAttrChsName() + "',");
			sql.append("'" + result.getUserId() + "')");
			super.sqlBulkUpdate(sql.toString());
		}
	}

	@Override
	public void deleteLogicCheckAttrResultsByUserId(String unitId,
			String discId, String userId) {
		StringBuilder hql = new StringBuilder("delete  from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("UNIT_ID");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("DISC_ID");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		super.sqlBulkUpdate(hql.toString(), params.toArray());
	}

	@Override
	public List<LogicCheckAttrResult> getExportResultsOfError(String unitId,
			String discId, String entityId, String userId) {
		StringBuilder sql = new StringBuilder(" select * from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId);
			conditionColumns.add("unit_Id");
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId);
			conditionColumns.add("disc_Id");
		}
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId);
			conditionColumns.add("entity_Id");
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("user_Id");
		}
		sql.append(super.sqlAndConditon(conditionColumns));
		String orderNames = "unit_Id asc, disc_Id asc, entity_Chs_Name asc, seq_No";
		return super.sqlPage(sql.toString(), 0, 0, false, orderNames,
				params.toArray());
	}
}
