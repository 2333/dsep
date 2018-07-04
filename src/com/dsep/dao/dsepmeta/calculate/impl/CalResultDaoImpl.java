package com.dsep.dao.dsepmeta.calculate.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.CalResultDao;
import com.dsep.entity.dsepmeta.CalResult;

public class CalResultDaoImpl extends 
DsepMetaDaoImpl<CalResult, String> implements CalResultDao {
	
	private String tableName=super.getTableName("O", "CAL_TOTALSCORE");
	
	@Override
	public List<CalResult> getResult(String unitId, String discId) {
/*		StringBuilder strBud=new StringBuilder("select * from "+tableName);
		List<Object> params=new LinkedList<Object>();
		List<String> conditions=new LinkedList<String>();
		
		if(StringUtils.isBlank(unitId)){
			conditions.add("UNIT_ID");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			conditions.add("DISC_ID");
			params.add(discId);
		}
		
		strBud.append(super.sqlAndConditon(conditions));
		
		Object[] values=params.toArray();
		
		List<CalResult> list=super.sqlFind(strBud.toString(), values);*/
		
		
		String sql= "Select * from "+tableName
				+" where UNIT_ID='" + unitId+"' and DISC_ID='"+discId+"'";
		List<CalResult> list=super.sqlFind(sql);
		
		return list;
	}

	@Override
	public void deleteResult(String unitId, String discId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CalResult> getResult(String discId, String unitId,int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		
		StringBuilder sql=new StringBuilder("select * from " +tableName);
		List<Object> params=new LinkedList<Object>();
		List<String> conditions=new LinkedList<String>();
		if(StringUtils.isNotBlank(discId)){
			params.add(discId);
			conditions.add("DISC_ID");
		}
		
		if(StringUtils.isNotBlank(unitId)){
			params.add(unitId);
			conditions.add("UNIT_ID");
		}
		String condionStr=super.sqlAndConditon(conditions);
		sql.append(condionStr);
		
		Object[] values=params.toArray();
		
		List<CalResult> result = super.sqlPage(sql.toString(), pageIndex, pageSize, desc, orderProperName, values);
		
		return result;
	}

}
