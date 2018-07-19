package com.dsep.service.expert.select.util;

import java.lang.reflect.Field;
import java.util.List;

import com.dsep.entity.expert.ExpertSelectionRuleMeta;
import com.dsep.service.expert.rule.RuleMetaService;

/**
 * 将数据库中设置的优先条件参数赋值给
 * DiscAndUnitDivisionSelectionStrategyServiceImpl对象X
 * ！采用约定大于配置的方式：
 * 对象X中优先条件参数必须以数据库中的reflectionSign开头，Weight结尾！
 * 
 * 注意：并非所有的优先条件都会用上，还需要依据用户在前台的勾选
 */
public class SelectionRuleWeightInjectionValve {

	public static void call(Object obj, RuleMetaService ruleMetaService)
			{

		Field[] fields = obj.getClass().getDeclaredFields();

		List<ExpertSelectionRuleMeta> metas = ruleMetaService.getAll();

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			// 字段名  
			String field = fields[i].getName();
			for (ExpertSelectionRuleMeta m : metas) {
				// 反射标志符(代码中以该标识符开头的字段可以反射赋值)
				String reflectionSign = m.getReflectSymbol();
				//System.out.println(field);
				if (field.startsWith(reflectionSign)
						&& field.endsWith("Weight")) {
					int weight = Integer.valueOf(m.getWeight());
					try {
						fields[i].set(obj, weight);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					continue;
				}
			}

		}
	}
}
