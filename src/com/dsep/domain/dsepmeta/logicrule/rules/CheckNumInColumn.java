package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicNumberRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckNumInColumn implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String[] params = metaAttr.getCheckRuleParamValue();

		// 总经费字段
		String param = params[0];

		String dataCol = metaAttr.getColumnName();
		String data = rowData.get(dataCol);

		String paramStr = rowData.get(param);

		return LogicNumberRulesUtil.checkNumInColumn(data, paramStr);
	}
}