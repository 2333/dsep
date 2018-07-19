package com.dsep.service.datacalculate.RuleEngine.commander;

import java.util.List;
import java.util.Map;
import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;

public class Number implements ComputeCommander{

	@Override
	public double compute(List<Map<String,String>> data) {
		
		double result=0.0;

		Map<String,String> commParams=data.get(0);
		
		String field=commParams.get(DataCode.FIELD);
		if(data.size()==2){
			result=Double.parseDouble(data.get(1).get(field));
		}
		return result;
	}

}
