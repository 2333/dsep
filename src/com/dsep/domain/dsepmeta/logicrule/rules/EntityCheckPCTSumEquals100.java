package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaEntityCheck;
import com.meta.domain.MetaEntityDomain;

public class EntityCheckPCTSumEquals100 implements MetaEntityCheck {

	@Override
	public String entityCheck(MetaEntityDomain entity, String categoryId,
			int size, List<Map<String, String>> datas) {


		if (size < 5)
			return "该表中应该有5条记录";
		Float num1, num2, num3, num4; 
		try {
			num1 = Float.valueOf(datas.get(0).get("BL").replace("%", "").replace(" ", ""));
			num2 = Float.valueOf(datas.get(1).get("BL").replace("%", "").replace(" ", ""));
			num3 = Float.valueOf(datas.get(2).get("BL").replace("%", "").replace(" ", ""));
			num4 = Float.valueOf(datas.get(3).get("BL").replace("%", "").replace(" ", ""));
		} catch (Exception e) {
			// 因为num出错会在attr检查中查出，entity检查不管
			return "PASSED";
		}
		
		if (100.0 != (num1 + num2 + num3 + num4))
			return "ERROR学缘结构比例字段前四行和应该为100%";
		
		if ((num1 >= num2) && (num2 >= num3))
			return "PASSED";	
		else
			return "ERROR学缘结构比例字段前三行应该降序排列";
			
		}
	
}
