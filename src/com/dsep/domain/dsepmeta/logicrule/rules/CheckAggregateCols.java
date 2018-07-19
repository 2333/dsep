package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicLogisticRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckAggregateCols implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String params = metaAttr.getCheckRuleParamValue()[0];
		String []rules = params.split(";");
		List<Integer> list = new ArrayList<Integer>();
		for (String rule : rules) {
			String date = rowData.get(rule);
			list.add(Integer.valueOf(date));
		}
		
		String dateCol = metaAttr.getColumnName();
		String data = rowData.get(dateCol);
		list.add(Integer.valueOf(data));
		return LogicLogisticRulesUtil.checkAggregateCols(list);
	}
}