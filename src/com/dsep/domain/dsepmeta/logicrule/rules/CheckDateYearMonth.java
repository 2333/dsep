package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 
 * CheckRuleParamValue 第0个参数为period列名
 * 校验年月
 * @author lubin
 *
 */
public class CheckDateYearMonth implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		
		String dateCol = metaAttr.getColumnName();
		String date = rowData.get(dateCol);
		int startDate = Integer.parseInt(metaAttr.getCheckRuleParamValue()[0]);
		int endDate = Integer.parseInt(metaAttr.getCheckRuleParamValue()[1]);
		return LogicDateRulesUtil.checkDateYearMonth(date,startDate,endDate);
	}
}
