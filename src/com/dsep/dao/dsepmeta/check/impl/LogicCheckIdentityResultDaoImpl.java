package com.dsep.dao.dsepmeta.check.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.LogicCheckIdentityResultDao;
import com.dsep.entity.dsepmeta.LogicCheckIdentityResult;
import com.dsep.util.datacheck.LogicCheckStatusCode;

public class LogicCheckIdentityResultDaoImpl extends
		DsepMetaDaoImpl<LogicCheckIdentityResult, String> implements
		LogicCheckIdentityResultDao {

	private String tableName = super.getTableName("D", "logic_identity");

	@Override
	public boolean setNewIdentityId(String id, String userId, Date newDate) {
		LogicCheckIdentityResult result = new LogicCheckIdentityResult();
		result.setId(id);
		result.setUserId(userId);
		result.setInputDate(newDate);
		try {
			super.save(result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean whetherExistGUID(String userId) {
		StringBuilder hql = new StringBuilder(
				"select count(*) from LogicCheckIdentityResult ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		int count = super.hqlCount(hql.toString(), params.toArray());
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getGUID(String userId) {
		StringBuilder hql = new StringBuilder("from LogicCheckIdentityResult ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		List<LogicCheckIdentityResult> list = new LinkedList<LogicCheckIdentityResult>();
		list = super.hqlPage(hql.toString(), 0, 0, false, null,
				params.toArray());
		if (list.size() != 0) {
			return list.get(0).getId();
		} else {
			return LogicCheckStatusCode.HAVE_NOT_CHECKED;
		}
	}

	@Override
	public String getDate(String userId) {
		StringBuilder hql = new StringBuilder("from LogicCheckIdentityResult ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (StringUtils.isNotBlank(userId)) {
			params.add(userId);
			conditionColumns.add("userId");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		List<LogicCheckIdentityResult> list = new LinkedList<LogicCheckIdentityResult>();
		list = super.hqlPage(hql.toString(), 0, 0, false, null,
				params.toArray());
		if (list.size() != 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
			String time = df.format(list.get(0).getInputDate());
			
			return time;
		} else {
			return LogicCheckStatusCode.HAVE_NOT_CHECKED;
		}
	}

	@Override
	public void deleteByUserId(String userId) {
		String sql = "delete from " + tableName + " where USER_ID='" + userId
				+ "'";
	}

}
