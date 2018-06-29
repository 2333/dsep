package com.dsep.service.flow;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.vm.PageVM;
import com.dsep.vm.flow.InnerStateVm;
import com.meta.entity.MetaInnerStateDetail;

public interface DsepInnerStateService {
	
	/**
	 * 获取当前轮次的内部状态信息
	 * @return
	 */
	public abstract PageVM<InnerStateVm> getCurrentInnerStates(int pageIndex,int pageSize,Boolean desc, String orderpropName);
	
	/**
	 * 通过领域Id,获取内部状态信息
	 * @param domainId
	 * @return
	 */
	public abstract PageVM<InnerStateVm> getInnerStatesByDomainId(String domainId,int pageIndex,int pageSize,Boolean desc, String orderpropName);
	
	/**
	 * 对内部流程状态进行更新
	 * @param metaInnerStateDetail
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String updateInnerStateDetail(MetaInnerStateDetail metaInnerStateDetail);

}
