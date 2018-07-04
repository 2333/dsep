package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公共库比对DMService
 * @author Monar
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public interface DMPublicLibService {
	
	/**
	 * 开始公共库比对
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void startPubCompare(String userId);

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
	public abstract List<Map<String, String>> getLocalDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize);
	
}
