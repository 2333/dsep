package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 *  CheckRuleParamValue 第0个参数为开始时间列名 ,第1个参数为period
 * 校验结束年月日信息
 * @author lubin
 *
 */
public class CheckDateFinishYearMonthDay implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr,  Map<String, String> rowData) {
		// TODO Auto-generated method stub
		String startDateCol = metaAttr.getCheckRuleParamValue()[0];
		String startDate = (String) rowData.get(startDateCol);
		String periodCol = metaAttr.getCheckRuleParamValue()[1];
		String period = (String) rowData.get(periodCol);
		String endDateCol = metaAttr.getColumnName();
		String endDate = (String) rowData.get(endDateCol);
		return LogicDateRulesUtil.checkDateFinishYearMonthDay(startDate, endDate, Integer.parseInt(period));
	}
}
