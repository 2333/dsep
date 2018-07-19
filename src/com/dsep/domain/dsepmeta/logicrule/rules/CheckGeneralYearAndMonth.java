package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckGeneralYearAndMonth implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String yearCol = metaAttr.getColumnName();
		String year = (String) rowData.get(yearCol);
		return LogicDateRulesUtil.checkGeneralYearAndMonth(year);
	}
}
