package com.dsep.dao.dsepmeta.check.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.LogicCheckEntityResultDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.LogicCheckEntityResult;
import com.dsep.util.GUID;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class LogicCheckEntityResultDaoImpl extends
		DsepMetaDaoImpl<LogicCheckEntityResult, String> implements
		LogicCheckEntityResultDao {

	private String getTableName() {
		return super.getTableName("D", "logic_ent_result");
	}


	@Override
	public List<Map<String, String>> getResultForCenter(
			String inputUnitId, User user, int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (StringUtils.isBlank(inputUnitId)) {
			String sql = "select distinct UNIT_ID from " + getTableName()
					+ " where USER_ID='" + user.getId() + "'";
			if (desc) {
				sql += " order by UNIT_ID desc";
			} else {
				sql += " order by UNIT_ID asc";
			}
			List<String> unitData = super.GetShadowResult(sql);
			List<String> unitData2 = new ArrayList<String>();
			// 翻页功能
			int beginCursor = (pageIndex-1) * pageSize;
			for (int i = beginCursor; i < beginCursor + pageSize; i++) {
				if (i >= unitData.size()) break;
				unitData2.add(unitData.get(i));
			}
			
			for (String unit : unitData2) {
				Map<String, String> row = new LinkedHashMap<String, String>();
				row.put("UNIT_ID", unit);

				// 第三个参数true表示检查DSEP_C_ENT_RESULT里面有警告的数据
				row.put("hasWarn", this.hasWarn(unit, null, user.getId(), true,
						getTableName()));
				row.put("hasError", this.hasError(unit, null, user.getId(),
						true, getTableName()));
				result.add(row);
			}
			return result;
		} else {
			Map<String, String> row = new LinkedHashMap<String, String>();
			row.put("UNIT_ID", inputUnitId);

			// 第三个参数true表示检查DSEP_C_ENT_RESULT里面有警告的数据
			row.put("hasWarn", this.hasWarn(inputUnitId, null, user.getId(),
					true, getTableName()));
			row.put("hasError", this.hasError(inputUnitId, null, user.getId(),
					true, getTableName()));
			result.add(row);
			return result;
		}
	}

	@Override
	public List<Map<String, String>> getResultForUnit(
			String inputDiscId, User user, int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (StringUtils.isBlank(inputDiscId)) {
			String sql = "select distinct DISC_ID from " + getTableName()
					+ " where UNIT_ID = '" + user.getUnitId() + "'"
					+ " and USER_ID ='" + user.getId() + "'";
			if (desc) {
				sql += " order by DISC_ID desc";
			} else {
				sql += " order by DISC_ID asc";
			}
			List<String> discData = super.GetShadowResult(sql);
			
			List<String> discData2 = new ArrayList<String>();
			// 翻页功能
			int beginCursor = (pageIndex-1) * pageSize;
			for (int i = beginCursor; i < beginCursor + pageSize; i++) {
				if (i >= discData.size()) break;
				discData2.add(discData.get(i));
			}
			for (String disc : discData2) {
				Map<String, String> row = new LinkedHashMap<String, String>();
				row.put("DISC_ID", disc);
				// row.put("PASSED", this.getPass(unitId, null, tableName));
				// 第三个参数true表示检查DSEP_C_ENT_RESULT里面有警告的数据
				row.put("hasWarn", this.hasWarn(user.getUnitId(), disc,
						user.getId(), true, getTableName()));
				row.put("hasError", this.hasError(user.getUnitId(), disc,
						user.getId(), true, getTableName()));
				result.add(row);
			}
			return result;
		} else {
			Map<String, String> row = new LinkedHashMap<String, String>();
			row.put("DISC_ID", inputDiscId);
			row.put("hasWarn", this.hasWarn(user.getUnitId(), inputDiscId,
					user.getId(), true, getTableName()));
			row.put("hasError", this.hasError(user.getUnitId(), inputDiscId,
					user.getId(), true, getTableName()));
			result.add(row);
			return result;
		}
	}

	@Override
	public List<LogicCheckEntityResult> getResultForDisc(
			String unitId, String discId, String userId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		if (StringUtils.isNotBlank(discId) && StringUtils.isNotBlank(unitId)) {
			String sql = "select * from " + getTableName()
					+ " where UNIT_ID=? and DISC_ID=? and USER_ID=?";
			List<LogicCheckEntityResult> result = super.sqlPage(sql, pageIndex,
					pageSize, desc, orderProperName, new Object[] { unitId,
							discId, userId });
			return result;
		} else {
			return null;
		}
	}


	private String hasError(String unitId, String discId, String userId,
			Boolean hasError, String checkEntityName) {
		String sql = "select count(*) from " + checkEntityName;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
		}

		params.add(hasError); // 参数
		conditionColumns.add("HAS_ERROR");// 查询条件

		// 最后拼接的sql是select count(*) from xxx where unit_id = 'xxx'
		// and disc_id = 'xxx' and has_error = '1'
		sql += super.sqlAndConditon(conditionColumns);
		int counts = super.sqlCount(sql, params.toArray());
		return counts > 0 ? "1" : "0";
	}

	private String hasWarn(String unitId, String discId, String userId,
			Boolean hasWarn, String checkEntityName) {
		String sql = "select count(*) from " + checkEntityName;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
		}

		params.add(hasWarn); // 参数
		conditionColumns.add("HAS_WARN");// 查询条件

		// 最后拼接的sql是select count(*) from xxx where unit_id = 'xxx'
		// and disc_id = 'xxx' and has_warn = '1'
		sql += super.sqlAndConditon(conditionColumns);
		int counts = super.sqlCount(sql, params.toArray());
		return counts > 0 ? "1" : "0";
	}

	public int getPassInfo(String unitId, String discId, String passInfo,
			String checkEntityName) {
		String sql = "select count(*) from " + checkEntityName;
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(passInfo)) {
			params.add(passInfo); // 参数
			conditionColumns.add("PASSED");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		int counts = super.sqlCount(sql, params.toArray());
		return counts;
	}

	@Override
	public int getCheckCount(String unitId, String discId, String userId) {
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(discId) && StringUtils.isNotBlank(unitId)) {
			String sql = "select count( distinct ENTITY_ID ) from "
					+ getTableName();
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
			params.add(discId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
			sql += super.sqlAndConditon(conditionColumns);
			return super.sqlCount(sql, params.toArray());
		} else if (StringUtils.isNotBlank(unitId)
				&& StringUtils.isBlank(discId)) {
			String sql = "select count( distinct DISC_ID ) from "
					+ getTableName();
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
			sql += super.sqlAndConditon(conditionColumns);
			return super.sqlCount(sql, params.toArray());
		} else if (StringUtils.isBlank(discId) && StringUtils.isBlank(unitId)) {
			String sql = "select count( distinct UNIT_ID ) from "
					+ getTableName();
			params.add(userId); // 参数
			conditionColumns.add("USER_ID");// 查询条件
			sql += super.sqlAndConditon(conditionColumns);
			return super.sqlCount(sql, params.toArray());
		}
		return -1;
	}

	@Override
	public List<LogicCheckEntityResult> getWarnAndErrorData(String unitId,
			String discId, String entityId, String userId, int pageIndex,
			int pageSize, boolean asc, String orderProName) {
		StringBuilder hql = new StringBuilder(" from LogicCheckEntityResult ");
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
		params.add(true);
		conditionColumns.add("hasWarn");

		params.add(false);
		conditionColumns.add("hasError");
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlPage(hql.toString(), pageIndex, pageSize, asc,
				orderProName, params.toArray());
	}

	@Override
	public List<LogicCheckEntityResult> getWarnData(String unitId,
			String discId, String entityId, String userId, int pageIndex,
			int pageSize, boolean asc, String orderProName) {
		StringBuilder hql = new StringBuilder(" from LogicCheckEntityResult ");
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
		params.add(true);
		conditionColumns.add("hasWarn");

		// params.add(false);
		// conditionColumns.add("hasError");

		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlPage(hql.toString(), pageIndex, pageSize, asc,
				orderProName, params.toArray());
	}

	@Override
	public int getCheckWarnCount(String unitId, String discId, String entityId,
			String userId) {
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		String sql = "select count(*) from " + getTableName();
		if (StringUtils.isNotBlank(unitId)) {
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(discId)) {
			params.add(discId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(entityId)) {
			params.add(entityId); // 参数
			conditionColumns.add("ENTITY_ID");// 查询条件
		}
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_Id");
		}
		params.add(true);
		conditionColumns.add("HAS_WARN");
		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlCount(sql, params.toArray());
	}

	@Override
	public void deleteAllByUserId(String userId) {
		StringBuilder hql = new StringBuilder("delete  from " + getTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("USER_ID");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		super.sqlBulkUpdate(hql.toString(), params.toArray());
	}

	@Override
	public void deleteAllOfTheLastData(String unitId, String discId,
			String userId) {
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
		this.flush();
		super.sqlBulkUpdate(hql.toString(), params.toArray());
	}

	@Override
	public void updatePassInfo(String unitId, String discId,
			Map<String, String> data) {
		StringBuilder updateSql = new StringBuilder("update " + getTableName()
				+ " set USER_ID = " + "'" + data.get("USER_ID") + "',"
				+ "CHECK_DATE = to_date ( '" + data.get("CHECK_DATE")
				+ "','yyyy-mm-dd hh24:mi:ss')," + "CONCLUSION = '"
				+ data.get("CONCLUSION") + "'," + "PASSED = '"
				+ data.get("PASSED") + "'");
		updateSql.append(" where UNIT_ID = '" + unitId + "' and DISC_ID = '"
				+ discId + "' and ENTITY_ID = '" + data.get("ENTITY_ID") + "'");
		String sql = updateSql.toString();
		super.sqlBulkUpdate(sql);
	}

	@Override
	public List<LogicCheckEntityResult> getExportResultsOfWarn(String unitId,
			String discId, String entityId, String userId) {
		StringBuilder sql = new StringBuilder(" select * from "
				+ getTableName());
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
		params.add(true);
		conditionColumns.add("has_Warn");
		sql.append(super.sqlAndConditon(conditionColumns));
		String orderNames = "unit_Id asc, disc_Id asc, entity_Chs_Name asc, conclusion";
		return super.sqlPage(sql.toString(), 0, 0, false, orderNames,
				params.toArray());
	}

	@Override
	public void saveLogicCheckEntityResults(List<LogicCheckEntityResult> results) {
		for (LogicCheckEntityResult result : results) {
			super.save(result);
		}
	}

}
