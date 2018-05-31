package com.dsep.service.datacalculate.FactorAndWeight;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IndexWeightService {
	/**
	 * 计算同一门类所有末级指标项权重的平均分
	 * @param catId
	 */
	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void calculateAllIndexByCat(String catId);*/
	
	/**
	 * 计算同一学科所有末级指标项权重的平均分并进行归一化处理
	 * @param discId
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void calculateAllIndexByDisc(String discId) throws Exception;
	
	/**
	 * 导出指标权重excel
	 * @param discId
	 * @param rootPath
	 * @return
	 * @throws Exception
	 */
	public abstract String exportWeightExcel(String discId,String rootPath) throws Exception;
}
