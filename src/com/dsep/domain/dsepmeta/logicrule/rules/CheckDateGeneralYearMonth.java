package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验一般年月信息
 * @author lubin
 *
 */
public class CheckDateGeneralYearMonth implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		// TODO Auto-generated method stub
		String yearCol = metaAttr.getColumnName();
		String year = (String) rowData.get(yearCol);
		return LogicDateRulesUtil.checkDateGeneralYearMonth(year);
	}
}
