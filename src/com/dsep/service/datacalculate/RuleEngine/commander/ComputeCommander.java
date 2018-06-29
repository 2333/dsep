package com.dsep.service.datacalculate.RuleEngine.commander;

import java.util.List;
import java.util.Map;

public interface ComputeCommander {

	/**
	 * 命令的具体执行，返回本命令计算的结果数值
	 * @param data 要计算的数据
	 * @return
	 */
	 
	public double compute(List<Map<String,String>> data);

}
