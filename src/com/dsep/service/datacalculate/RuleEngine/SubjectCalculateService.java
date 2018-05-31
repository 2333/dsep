package com.dsep.service.datacalculate.RuleEngine;

public interface SubjectCalculateService {
	/**
	 * 获得主观打分项打分结果（经过线性差值求平均）
	 * @param indexId
	 * @param discId
	 * @param unitId
	 * @return
	 */
	public abstract double getSubjectValue(String indexId,String discId,String unitId) throws Exception;
}
