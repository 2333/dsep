package com.dsep.service.rbac;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.Ip;
import com.dsep.vm.CheckTreeVM;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IpService {
	
	public abstract Set<Ip> getUserIps(Integer userId);
	
	/** 取得某用户的ip键值对
	 * @param userId
	 * @return Map<Ip,该User是否有该角色（没有0，有1）>
	 */
	public abstract List<CheckTreeVM> getIpTreeVMs(Integer userId);
	public abstract List<CheckTreeVM> getUserIpsTree(Integer userId);

	public abstract PageVM<Ip> getIps(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	public abstract Ip getIp(Integer ipId);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean deleteIp(Integer ipId);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Integer saveIp(Ip ip);
	
	public abstract Ip getIpByIpValue(String ipValue);
	
}
