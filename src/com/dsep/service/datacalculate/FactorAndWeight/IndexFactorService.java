package com.dsep.service.datacalculate.FactorAndWeight;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IndexFactorService {
	
	//public abstract 
	
	public abstract Map<String,Double> getIndexFactor(String indexId,String discId);
	
	/**
	 * 计算一个门类的所有的折算系数打分的平均值（按门类口径，不包含各类奖项）
	 * @param catId 门类ID
	 */
	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void calculateIndicWtByCat(String catId);*/
	
	/**
	 * 计算一个学科的各个折算系数打分的平均值（不包含各类奖项）
	 * @param discId 学科ID
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void calculateIndicWtByDisc(String discId) throws Exception;
	
	/**
	 * 计算一个学科各类奖项折算系数打分的平均值
	 * @param discId
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void calculateAwardWtByDisc(String discId) throws Exception;
	
	/**
	 * 导出折算系数excel
	 * @param discId
	 * @param rootPath
	 * @return
	 * @throws Exception
	 */
	public abstract String exportFactorExcel(String discId,String rootPath) throws Exception;
}
