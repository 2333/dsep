package com.dsep.dao.dsepmeta.check.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.PubResultDao;
import com.dsep.entity.dsepmeta.PubResult;
import com.dsep.util.GUID;

public class PubResultDaoImpl extends DsepMetaDaoImpl<PubResult, String>
		implements PubResultDao {

	@Override
	public void deletePubResultByPubId(String pubLibId) {

		String tableName = super.getTableName("D", "pub_result");

		StringBuilder hql = new StringBuilder("delete from " + tableName);

		List<Object> params = new LinkedList<Object>();

		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(pubLibId)) {
			params.add(pubLibId);
			conditionColumns.add("PUBLIB_ID");
		}

		hql.append(super.hqlAndCondtion(conditionColumns));

		super.sqlBulkUpdate(hql.toString(), params.toArray());
	}

	@Override
	public boolean hasPubResultByPubId(String pubLibId) {

		String tableName = super.getTableName("D", "pub_result");

		StringBuilder sqlString = new StringBuilder("select ID from "
				+ tableName);

		List<Object> params = new LinkedList<Object>();

		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(pubLibId)) {
			params.add(pubLibId);
			conditionColumns.add("PUBLIB_ID");
		}

		sqlString.append(super.sqlAndConditon(conditionColumns));

		Object[] values = params.toArray();

		List<String> result = super.GetShadowResult(sqlString.toString(),
				values);

		int count = result.size();

		return (count > 0);
	}

	@Override
	public List<PubResult> getPubResults(String pubLibId, String type, 
			int pageIndex, int pageSize,
			boolean desc, String orderProperName
			) {

		String tableName = super.getTableName("D", "pub_result");

		StringBuilder sqlString = new StringBuilder("select * from "
				+ tableName);

		List<Object> params = new LinkedList<Object>();

		List<String> conditionColumns = new LinkedList<String>();

		if (StringUtils.isNotBlank(pubLibId)) {
			params.add(pubLibId);
			conditionColumns.add("PUBLIB_ID");
		}

		if (StringUtils.isNotBlank(type)) {
			params.add(type);
			conditionColumns.add("COMPARE_RESULT");
		}

		sqlString.append(super.sqlAndConditon(conditionColumns));

		Object[] values = params.toArray();
		return super.sqlPage(sqlString.toString(), pageIndex, pageSize, desc, orderProperName, values);
	}

	@Override
	public void saveCompareResult(String publibId, String entityId,
			List<Map<String, String>> results) {
		for (Map<String, String> result : results) {
			PubResult pr = new PubResult();
			pr.setId(GUID.get());
			pr.setUnitId(result.get("unitId").toString());
			pr.setDiscId(result.get("discId").toString());
			pr.setEntityId(entityId);
			pr.setEntityChsName(result.get("entityChsName").toString());
			pr.setSeqNo(Integer.parseInt(result.get("seqNo").toString()));
			pr.setLocalItemId(result.get("localItemId").toString());
			pr.setLocalField(result.get("localField").toString());
			pr.setLocalChsField(result.get("localChsField").toString());
			pr.setLocalValue(result.get("localValue").toString());
			pr.setFlagField(result.get("flagField").toString());
			pr.setPublibId(publibId);
			pr.setPubItemId("");// 用处不大，暂时为空
			pr.setPubValue(result.get("pubValue") == null ? "" : result.get(
					"pubValue").toString());
			pr.setCompareResult(result.get("compareResult").toString()
					.charAt(0));
			super.saveOrUpdate(pr);
		}
	}

}
