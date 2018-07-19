package com.dsep.service.check.publiclib;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.PubEntry;
import com.dsep.entity.dsepmeta.PubResult;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

/**
 * 公共库比对Service
 * @author Monar
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public interface PublicLibService {

	/**
	 * 获取比对入口配置表
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public PageVM<PubEntry> getPubEntry(int pageIndex, int pageSize);
	
	/**
	 * 开始比对，并且更新配置表
	 * @param pubLibIds
	 */
	abstract public void startPubCompare(String userId);
	
	/**
	 * 根据不同的结果类型返回不同的数据
	 * @param pubLibId
	 * @param type
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public PageVM<PubResult> getResultDataByType(String pubLibId,String type, int pageIndex, int pageSize,
			boolean desc, String orderProperName);
	
	/**
	 * 获取本地数据的数据表配置
	 * @param entityId
	 * @return
	 */
	abstract public ViewConfig getLocalDataConfig(String entityId);
	
	/**
	 * 获取本地数据
	 * @param entityId
	 * @param itemId
	 * @param sidx
	 * @param order_flag
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public abstract JqgridVM getLocalDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize);
	
	/**
	 * 导出比对结果
	 * @param user
	 * @param rootPath
	 * @return
	 */
	abstract public String getExportCompareInfo(User user, String rootPath);
}
