package com.dsep.service.rbac;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.UserIpLog;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface UserIpLogService {
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void save(UserIpLog userIpLog);
	
	
	
	public abstract PageVM<UserIpLog> userIpLogQuery(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
}
