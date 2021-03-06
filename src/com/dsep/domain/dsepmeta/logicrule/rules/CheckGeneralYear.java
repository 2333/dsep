package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckGeneralYear implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String date = rowData.get(dateCol);
		return LogicDateRulesUtil.checkGeneralYear(date);
	}
}
