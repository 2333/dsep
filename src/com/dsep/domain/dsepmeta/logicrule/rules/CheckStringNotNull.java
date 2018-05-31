package com.dsep.domain.dsepmeta.logicrule.rules;


import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicNumberRulesUtil;
import com.dsep.util.datacheck.LogicStringRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验字符串是否为空
 * @author lubin
 */
public class CheckStringNotNull implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String data = rowData.get(dateCol);
		return LogicStringRulesUtil.checkStringIsNotNull(data);
	}
}
