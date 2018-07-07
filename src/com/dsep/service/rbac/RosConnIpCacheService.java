package com.dsep.service.rbac;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.RosConnIpCache;

@Transactional(propagation=Propagation.SUPPORTS)
public interface RosConnIpCacheService {
	public abstract List<RosConnIpCache> getAllIpsByRosLocation(String rosLocation);
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void updateAllConnIpByRosLocation(String rosLocation, List<RosConnIpCache> list);
	
	public abstract RosConnIpCache getRosConnIpCacheByIpValue(String ipValue);
	
	public abstract RosConnIpCache getRosConnIpCacheByPppoeAndLocation(String pppoe, String location);
		
}
