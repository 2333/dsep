package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckYearMonthBetweenTwoColumnValues implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String[] params = metaAttr.getCheckRuleParamValue();
		String col1 = params[0];
		String col2 = params[1];
		
		String anotherRestrictTimeStr = rowData.get(col2);
		
		String beginTimeStr = params[2];
		String endTimeStr = params[3];
		
		String dateCol = metaAttr.getColumnName();
		String inputTimeStr = rowData.get(dateCol);
		
		return LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		
	}
}
