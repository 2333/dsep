package com.dsep.service.rbac;

import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.IpInUse;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IpInUseService {
	
	public abstract Set<IpInUse> getIpInUse(Integer userId, Integer ipId);
	
	public abstract Integer setIpInUse(Integer userId, Integer ipId);
		
}
