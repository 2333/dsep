package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验一般年月日信息
 * @author lubin
 *
 */
public class CheckDateGeneralYearMonthDay implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String date = (String) rowData.get(dateCol);
		return LogicDateRulesUtil.checkDateGeneralYearMonthDay(date);
	}
}
