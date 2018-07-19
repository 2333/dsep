package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicStringRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckDataLength implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String param = metaAttr.getCheckRuleParamValue()[0];
		Integer standardLength = Integer.valueOf(param);

		String dateCol = metaAttr.getColumnName();
		String inputStr = rowData.get(dateCol);

		return LogicStringRulesUtil.checkDataLenght(inputStr, standardLength);

	}
}
