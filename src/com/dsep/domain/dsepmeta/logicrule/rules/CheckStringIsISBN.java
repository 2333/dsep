package com.dsep.domain.dsepmeta.logicrule.rules;


import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicNumberRulesUtil;
import com.meta.domain.MetaAttrDomain;


/**
 * 逻辑检验校验字符串是否符合ISBN编码规则
 * @author lubin
 *
 */
public class CheckStringIsISBN implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String str = rowData.get(dateCol);

		return LogicNumberRulesUtil.checkStringISBN(str);
	}


	
}
