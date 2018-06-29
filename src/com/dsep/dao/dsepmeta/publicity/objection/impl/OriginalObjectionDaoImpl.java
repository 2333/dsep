package com.dsep.dao.dsepmeta.publicity.objection.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.objection.OriginalObjectionDao;
import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.publicity.CenterObjectStatus;
import com.dsep.entity.enumeration.publicity.UnitObjectStatus;
import com.dsep.util.DateProcess;

public class OriginalObjectionDaoImpl extends
		DsepMetaDaoImpl<OriginalObjection, String> implements
		OriginalObjectionDao {

	private String tableName = super.getTableName("F", "original_objection");

	
	@Override
	public int submitObjectionByUnit(String publicityRoundId,String unitId) {
		Date newDate = new Date();
		StringBuilder updateSql = new StringBuilder("update " + tableName
				+ " set CENTER_OBJECT_TYPE = UNIT_OBJECT_TYPE,"
				+ "CENTER_OBJECT_CONTENT = UNIT_OBJECT_CONTENT,"
				+ "CENTER_OBJECT_ATTR_ID = UNIT_OBJECT_ATTR_ID,"
				+ "CENTER_OBJECT_ATTR_NAME = UNIT_OBJECT_ATTR_NAME,"
				+ "CENTER_OBJECT_ATTR_VALUE = UNIT_OBJECT_ATTR_VALUE,"
				+ "UNIT_STATUS = '" + UnitObjectStatus.SUBMIT.getStatus() + 
				"',CHECK_TIME = " + DateProcess.getDataBaseDate(newDate) + 
				",CENTER_STATUS = '"+ CenterObjectStatus.UNITSUBMIT.getStatus()+"'");
		updateSql.append(" where OBJECT_UNIT_ID = '" + unitId + "'");
		updateSql.append(" and CURRENT_PUBLIC_ROUND_ID ='"+publicityRoundId+"'");
		updateSql.append(" and UNIT_STATUS = '"+UnitObjectStatus.NOTSUBMIT.getStatus()+"'");
		String sql = updateSql.toString();
		return super.sqlBulkUpdate(sql);
	}

	@Override
	public int processAllObjection(String publicityRoundId) {
		// TODO Auto-generated method stub
		Date newDate = new Date();
		String updateSql = "update "+ this.tableName + " "
			+" set CENTER_STATUS = '" + CenterObjectStatus.PROCESSED.getStatus()
			+"',CHECK_TIME = " + DateProcess.getDataBaseDate(newDate)
			+" where CURRENT_PUBLIC_ROUND_ID = '" + publicityRoundId
			+"' and CENTER_STATUS = '" + CenterObjectStatus.UNITSUBMIT.getStatus()+"'";
		return super.sqlBulkUpdate(updateSql);
	}
}
