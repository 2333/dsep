package com.dsep.domain.dsepmeta.logicrule.rules;


import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicStringRulesUtil;
import com.meta.domain.MetaAttrDomain;


/**
 * 校验字符串是否包含中文信息
 * @author lubin
 *
 */
public class CheckStringNotChinese implements MetaAttrCheck {

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dataCol = metaAttr.getColumnName();
		String data = rowData.get(dataCol);
		return LogicStringRulesUtil.checkStringIsNotChinese(data);
	}

}
