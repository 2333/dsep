package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckYearMonthBetweenTwoDates implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String[] params = metaAttr.getCheckRuleParamValue();
		String beginTimeStr = params[0];
		String endTimeStr = params[1];
		
		String dateCol = metaAttr.getColumnName();
		String inputTimeStr = rowData.get(dateCol);
		
		return LogicDateRulesUtil.checkYearMonthBetweenTwoDates(inputTimeStr, beginTimeStr, endTimeStr);
		
	}
}



