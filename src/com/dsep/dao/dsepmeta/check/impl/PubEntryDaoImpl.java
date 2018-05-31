package com.dsep.dao.dsepmeta.check.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.PubEntryDao;
import com.dsep.entity.dsepmeta.PubEntry;

public class PubEntryDaoImpl extends DsepMetaDaoImpl<PubEntry, String>
		implements PubEntryDao{

	@Override
	public void updatePubEntryState(String publibId, String userId) {
		
		PubEntry pe = super.get(publibId);
		pe.setCheckDate(new Date());
		pe.setCheckUser(userId);
		pe.setIsChecked('1');
		super.saveOrUpdate(pe);
		
	}

	@Override
	public PubEntry getPUBLIB_CHS_NAME(String publibID) {
		
		String tableName = super.getTableName("D", "pub_entry");
		
		StringBuilder sqlString = new StringBuilder("select * from " + tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		if (StringUtils.isNotBlank(publibID)) {
			params.add(publibID);
			conditionColumns.add("PUBLIB_ID");
		}
		
		sqlString.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<PubEntry> data = super.sqlFind(sqlString.toString(), values);
		
		PubEntry result=data.get(0);
		return result;
	}

	@Override
	public List<String> getCheckedPublib_ID() {
		
		String tableName = super.getTableName("D", "pub_entry");
		
		StringBuilder sqlString = new StringBuilder("select PUBLIB_ID from " + tableName);
		
        List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add("1");
		conditionColumns.add("IS_CHECKED");
		
		sqlString.append(super.sqlAndConditon(conditionColumns));
		
		Object[] values = params.toArray();
		
		List<String> result = super.GetShadowResult(sqlString.toString(), values);
		
		
		return result;
	}
}
