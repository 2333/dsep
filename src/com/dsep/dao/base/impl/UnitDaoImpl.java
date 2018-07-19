package com.dsep.dao.base.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.dao.base.UnitDao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.Unit;
import com.dsep.entity.User;

public class UnitDaoImpl extends DaoImpl<Unit , String> implements UnitDao{

	@Override
	public Map<String, String> getUnitNames() {
		String sqlString=String
				//.format("select ID,Name from DSEP_BASE_UNIT order by nlssort(Name,'NLS_SORT=SCHINESE_PINYIN_M')");
				.format("select ID,Name from DSEP_BASE_UNIT");
		List<Object[]> colleges = super.sqlScalarResults(sqlString, new String[]{"ID","Name"});
		Map<String,String> map = new LinkedHashMap<String,String>();
		for(Object[] o: colleges)
		{
			map.put((String)o[0], ((String)o[1]).trim());
		}
		return map;
	}

	@Override
	public List<Unit> getUnitsByDiscId(String discId) {
		String sql = "select * from DSEP_BASE_UNIT where ID in (select UNIT_ID from DSEP_BASE_UNIT_DISC where DISC_ID = ?)";
		List<Unit> list=super.sqlFind(sql, new Object[]{discId});
		return list;
	}
	
}
