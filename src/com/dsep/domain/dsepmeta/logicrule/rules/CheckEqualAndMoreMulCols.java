package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicLogisticRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckEqualAndMoreMulCols implements MetaAttrCheck {

	// 废弃方法
	@Deprecated
	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String[] params = metaAttr.getCheckRuleParamValue();
		String param1 = params[0];
		String param2 = params[1];		
		
		String date = "";
		String []rules = param1.split(";");
		// 合计数组
		List<Integer> aggregationList = new ArrayList<Integer>();
		for (String rule : rules) {
			date = rowData.get(rule);
			aggregationList.add(Integer.valueOf(date));
		}
		
		String dateCol = metaAttr.getColumnName();
		date = rowData.get(dateCol);
		aggregationList.add(Integer.valueOf(date));
		String result1 = LogicLogisticRulesUtil.checkAggregateCols(aggregationList);
		
		rules = param2.split(";");
		// 比较数组，本字段的值date要大于所有相关字段
		List<Integer> comparationList = new ArrayList<Integer>();
		for (String rule : rules) {
			date = rowData.get(rule);
			comparationList.add(Integer.valueOf(date));
			
		}
		
		return result1;
	}
}