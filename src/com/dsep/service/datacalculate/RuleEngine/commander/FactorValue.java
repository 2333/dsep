package com.dsep.service.datacalculate.RuleEngine.commander;

import java.util.List;
import java.util.Map;
import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;

public class FactorValue implements ComputeCommander{
	
	@Override
	public double compute(List<Map<String,String>> data) {
		
		double result=0.0; 

		Map<String,String> commParams=data.get(0);
		
		String field=commParams.get(DataCode.INDEX_CONT);
		
		result=Double.parseDouble(data.get(1).get(DataCode.INDEX_FACTOR));
		
		return result;
	}

}
