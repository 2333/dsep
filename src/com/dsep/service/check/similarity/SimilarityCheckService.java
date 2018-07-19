package com.dsep.service.check.similarity;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SimilarityEntry;
import com.dsep.entity.dsepmeta.SimilarityResult;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface SimilarityCheckService{

	/*
	 * 获取查重入口
	 */
	abstract public PageVM<SimilarityEntry> getSimilarityEntry(User user,
			String orderPropName, boolean asc, int pageIndex, int pageSize);

	/*
	 * 初始化入口表
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void initSimilarityEntry(User user);
	/*
	 * 查重完成后更新入口表
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void updateSimilarityEntry(List<String> entityIds, String userId);

	/*
	 * 开始查重
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void startCheck(List<String> entityIds, String userId, String unitId, String discId);

	/*
	 * 返回查重结果
	 */
	abstract public PageVM<SimilarityResult> getSimilarityResults(
			String entityId, String userId, String unitId, String discId, String orderPropName, boolean asc, int pageIndex, int pageSize);
	
	/*
	 * 获取本地数据的数据表配置
	 */
	abstract public ViewConfig getLocalDataConfig(String entityId);

	/*
	 * 获取查重分组详情
	 */
	abstract public JqgridVM getSimilarityGroupDetail(String entityId,
			String dataId, String simIds, String sidx, boolean order_flag,
			int page, int pageSize, String unitId);
	
	/*
	 * 获取查重导出信息
	 */
	abstract public String getSimilarityExport(User user, String rootPath);
	
}
