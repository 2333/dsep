package com.dsep.service.datacalculate.RuleEngine;

public interface ObjectCalculateService {
	
	/**
	 * 传入公式，计算该客观末级指标
	 * @param formular 计算公式
	 * @param indexItemId  指标项ID
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @return
	 * @throws Exception
	 */
	public double calculate(String formular,String indexItemId,
			String unitId,String discId) throws Exception;
}
