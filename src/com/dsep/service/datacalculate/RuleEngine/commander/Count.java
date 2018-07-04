package com.dsep.service.datacalculate.RuleEngine.commander;


import java.util.List;
import java.util.Map;

public class Count implements ComputeCommander{

	@Override
	public double compute(List<Map<String,String>> data) {
		
		double countNumber=data.size()-1.0;   //data数据第一行存储的是关于命令的一些参数
		
		return countNumber;
	}

}
