package com.dsep.dao.dsepmeta.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.MessageDao;
import com.dsep.entity.MessageEntity;

public class MessageDaoImpl extends DsepMetaDaoImpl<MessageEntity, String>
		implements MessageDao {

	@Override
	public String getStatusByIdentifier(String _identifier) {
		// TODO Auto-generated method stub
		Object result = this
				.sqlUniqueResult(
						"SELECT STATUS FROM (SELECT STATUS FROM DSEP_BASE_MESSAGE WHERE RELATED_ID=? ORDER BY CREATED_AT DESC) WHERE ROWNUM=1 ORDER BY ROWNUM ASC",
						new Object[] { _identifier });
		return (String) result;
	}

	@Override
	public void updateColumnBySql(String id, HashMap<String, Object> columns) {
		StringBuilder updateSql = new StringBuilder("update DSEP_BASE_MESSAGE "
				+ " set STATUS = '" + columns.get("status")+"'");
		updateSql.append(" where ID = '" + id + "'");
		super.sqlBulkUpdate(updateSql.toString());
	}
}
