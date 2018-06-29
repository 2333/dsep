package com.dsep.service.check.paperInspect;

import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.SpotResult;
import com.dsep.vm.PageVM;
@Transactional(propagation=Propagation.SUPPORTS)
public interface PaperInspectionService {
	/**
	 * 开始抽查
	 * @param unit_disc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void startSpot();
	
	/**
	 * 获得抽查清单
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public PageVM<SpotResult> getSpotList(int pageIndex, int pageSize,
			boolean desc, String orderProperName);
	
	/**
	 * 导出抽查清单
	 * @param rootPath
	 * @return
	 */
	public String getExportSpotList(String rootPath);
}
