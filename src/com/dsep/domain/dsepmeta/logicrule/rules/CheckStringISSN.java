package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.meta.domain.MetaAttrDomain;

public class CheckStringISSN implements MetaAttrCheck {

	@Deprecated
	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		String dateCol = metaAttr.getColumnName();
		String str = rowData.get(dateCol);

		return null;
	}
}
