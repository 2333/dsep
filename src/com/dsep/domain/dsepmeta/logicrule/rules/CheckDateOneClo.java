package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

public class CheckDateOneClo implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String[] params = metaAttr.getCheckRuleParamValue();

		// HGSJ回国时间字段
		String compareTimeStrParam = params[0];
		// 距回国时间天数
		String compareNum = params[1];
		// 出国时间/回国时间汉字，目前没有用
		String chsName = params[2];
		// 运算符号，@和#分别表示+和-
		String operator = params[3];


		String dateCol = metaAttr.getColumnName();
		String currentTimeStr = rowData.get(dateCol);
		String compareTimeStr = rowData.get(compareTimeStrParam);
		return LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNum, operator);
	}
}