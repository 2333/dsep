package com.dsep.dao.base.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.dao.base.DisciplineDao;
import com.dsep.dao.common.Dao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.Discipline;
import com.dsep.entity.Unit;

public class DisciplineDaoImpl extends DaoImpl<Discipline , String> implements DisciplineDao{

	@Override
	public Map<String, String> getDisciplineNames() {
		// TODO Auto-generated method stub
		String sqlString = String
				//.format("select ID,NAME  from DSEP_BASE_DISCIPLINE order by nlssort(NAME ,'NLS_SORT=SCHINESE_PINYIN_M')");
				.format("select ID,NAME  from DSEP_BASE_DISCIPLINE");
		List<Object[]> colleges = super.sqlScalarResults(sqlString,
				new String[] { "ID", "NAME" });
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Object[] o : colleges) {
			map.put((String) o[0], ((String) o[1]).trim());
		}
		return map;
	}

	@Override
	public List<Discipline> getDisciplinesByUnitId(String unitId) {
		String sql = "select * from DSEP_BASE_DISCIPLINE where ID in (select DISC_ID from DSEP_BASE_UNIT_DISC where UNIT_ID = ?)";
		List<Discipline> list=super.sqlFind(sql, new Object[]{unitId});
		return list;
	}

}
