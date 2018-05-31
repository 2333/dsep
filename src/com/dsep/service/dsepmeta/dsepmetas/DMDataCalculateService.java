package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;

public interface DMDataCalculateService {
	
	/**
	 * 开始计算参评学科数据
	 * @throws Exception 
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String calLastIndex(List<String> discIds) throws Exception;
	/**
	 * 线性插值，得到每个末级指标的百分制得分
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void convertHundredMark(List<String> discIds);
	
	/**
	 * 计算每个学校的某学科总分
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void calTotalScore(List<String> discIds);
	
	/**
	 * 计算一二级指标的得分
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void calIndexScore(List<String> discIds);
	
	/**
	 * 扣总分，例如末级指标博士论文抽查
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void deductScore(List<String> discIds);
	
	/**
	 * 得出每个学科下学校总分的排名
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void sortUnits(List<String> discIds);
	
	/**
	 * 聚类
	 * @param discIds
	 * @param limit 由学位中心设置的阈值
	 */
	public void cluster(List<String> discIds,double limit);

}
