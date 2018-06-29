package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckDateBetweenTwoDates implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String date1 = metaAttr.getCheckRuleParamValue()[0];
		String date2 = metaAttr.getCheckRuleParamValue()[1];

		String dateCol = metaAttr.getColumnName();
		String currentData = rowData.get(dateCol);
		
		return LogicDateRulesUtil.checkDateBetweenTwoDates(currentData, date1, date2);
	}
}