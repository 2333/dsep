package com.dsep.service.datacalculate;

import java.util.List;








import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DataCalculateConfig;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;
import com.dsep.entity.dsepmeta.IndexScore;
import com.dsep.util.datacalculate.DiscLastIndexValueVM;
import com.dsep.vm.PageVM;
@Transactional(propagation=Propagation.SUPPORTS)
public interface DataCalculateService {
	
	
	/**
	 * 获取参评学科结果计算配置信息
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public PageVM<DataCalculateConfig> showDataCalculateConfig(User user,
			String orderPropName, boolean asc, int pageIndex, int pageSize);
	/**
	 * 计算
	 * @param discIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void dataCalculate(User user, List<String> discIds) throws Exception;
	
	/**
	 * 聚类
	 * @param discIds
	 * @param limit
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void dataCluster(List<String> discIds,double limit);
	/**
	 * 导出折算系数最后平均计算结果
	 * 
	 */
	public String getExportIndex(String rootPath);

	/**
	 * 导出指标权重最后平均计算结果
	 * 
	 */
	public String getExportWeight(String rootPath);


	/**
	 * 计算指标权重和折算系数
	 * 按照一级学科和指标体系两种途径来计算
	 * @param calType
	 */
	public boolean calWeightAndFactor(String calType);
	
	/**
	 * 获取分数计算结果
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract PageVM<DiscLastIndexValueVM> showDataCalculateResults(String discId);
	
	/////////////////////////计算结果展示的接口//////////////////////////
	
	/**
	 * 显示一张学科列表，本列表中的学科是所有已经得出计算结果的学科
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public PageVM<DataCalculateConfig> showResultConfig(String orderPropName,
			boolean asc, int pageIndex, int pageSize);

	/**
	 * 显示某个学科的具体计算结果
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public PageVM<CalResult> showResult(String discID,
			String orderPropName, boolean asc, int pageIndex, int pageSize);
	/**
	 * 显示某个学科某个学校的具体指标项数值
	 * @param discId
	 * @param unitId
	 * @param orderPropName
	 * @param asc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageVM<IndexScore> showDetial(String discId,String unitId,String orderPropName, boolean asc, int pageIndex, int pageSize);
	
	/**
	 * 显示某个学校的计算结果
	 * @param unit	学校ID或者是学校名称
	 * @param orderName
	 * @param asc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageVM<CalResult> showUnitResult(String unit,String orderName,boolean asc,int pageIndex,int pageSize);
	
	/**
	 * 
	 * @param discIds
	 * @param path
	 * @return
	 */
	public String getExportData(String path);
	
}
	
