package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验年月期限区间是否合理
 * @author lubin
 *
 */
public class CheckDatePeriod implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		// TODO Auto-generated method stub
		String dateCol = metaAttr.getColumnName();
		String date = (String) rowData.get(dateCol);
		return LogicDateRulesUtil.checkDatePeriod(date);
	}
}
