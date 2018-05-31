package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * CheckRuleParamValue 第0个参数为开始时间列名 
 * 校验结束年月信息
 * @author lubin
 *
 */
public class CheckDateFinishYearMonth implements MetaAttrCheck{


	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String startDateCol = metaAttr.getCheckRuleParamValue()[0]; //获取开始时间参数，是作为列名存储，需要自己获取具体值
		String startDate = (String) rowData.get(startDateCol); 
		String endDateCol = metaAttr.getColumnName();
		String endDate = (String) rowData.get(endDateCol);
		return LogicDateRulesUtil.checkDateFinishYearMonth(startDate, endDate);
	}
}
