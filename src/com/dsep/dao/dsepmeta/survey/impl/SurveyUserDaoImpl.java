package com.dsep.dao.dsepmeta.survey.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.survey.SurveyUserDao;
import com.dsep.entity.dsepmeta.SurveyUser;

public class SurveyUserDaoImpl extends DsepMetaDaoImpl<SurveyUser, String>
		implements SurveyUserDao {
	private String getServeyUserTableName() {
		return super.getTableName("Q", "SURVEY_USER");
	}

	public List<SurveyUser> retriveUsers(String unitId, String discId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {
		String sql = "select * from " + getServeyUserTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (!StringUtils.isBlank(unitId)) {
			params.add(unitId); // 参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if (!StringUtils.isBlank(discId)) {
			params.add(unitId); // 参数
			conditionColumns.add("DISC_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);

		Object[] array = params.toArray(new Object[params.size()]);
		return super.sqlPage(sql, pageIndex, pageSize, desc, orderProperName,
				array);
	}
}
