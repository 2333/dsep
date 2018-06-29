package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicLogisticRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckLessThanOneCol implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String str1 = rowData.get(dateCol);
		
		String dateCol2 = metaAttr.getCheckRuleParamValue()[0];
		String str2 = rowData.get(dateCol2);
		return LogicLogisticRulesUtil.checkLessThanOneCol(str1, str2);
	}
}