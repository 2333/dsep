package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicDateRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验出生日期格式及逻辑性
 * @author lubin
 *
 */
public class CheckDateBirthday implements MetaAttrCheck{


	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String colName = metaAttr.getColumnName();
		String data = (String) rowData.get(colName);
		return LogicDateRulesUtil.checkDateBirthday(data);
	}

}
