package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaEntityCheck;
import com.meta.domain.MetaEntityDomain;

public class EntityDataLimit implements MetaEntityCheck {

	@Override
	public String entityCheck(MetaEntityDomain entity, String categoryId,
			int size, List<Map<String, String>> datas) {
		String params = entity.getCheckRuleParamValue()[0];
		String[] rules = params.split(";");
		int limit = 0;
		for (String rule : rules) {
			String key = rule.split(":")[0];
			if (key.equals(categoryId)) {
				limit = Integer.valueOf(rule.split(":")[1]);
			}
		}
		if (size == 0)
			return "该表中无任何记录";
		else if (size > limit)
			return "表中最大记录数为" + limit + ",实际为" + size + "超出" + (size - limit);
		else if (size < limit)
			return "未填满最大记录数,最大应为" + limit + "实际为" + size;
		else
			return "PASSED";
	}
}
