package com.dsep.domain.dsepmeta.logicrule.rules;

import java.util.Map;

import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.util.datacheck.LogicLogisticRulesUtil;
import com.meta.domain.MetaAttrDomain;

/**
 * 校验参与单位数或本单位参与学科数
 * @author lubin
 * @author yuyaolin
 */
public class CheckDisciplineRank implements MetaAttrCheck{

	@Override
	public String check(MetaAttrDomain metaAttr, Map<String, String> rowData) {
		// TODO Auto-generated method stub
		String dataCol = metaAttr.getColumnName();
		String data = (String) rowData.get(dataCol);
		
		/*String test = (String) rowData.get("HJ_PROJECT");
		if (test != null && test.equals("F1赛车机载设备软件")) {
			System.out.println();
		}*/
		return LogicLogisticRulesUtil.checkDisciplineOrUnitsRank(data);
	}
}
