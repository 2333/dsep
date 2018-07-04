package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckYearAndMonthOfBeforeNow implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		// TODO Auto-generated method stub
		String yearCol = metaAttr.getColumnName();
		String year = (String) rowData.get(yearCol);
		return LogicDateRulesUtil.checkYearAndMonthOfBeforeNow(year);
	}
}
