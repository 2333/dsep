package com.dsep.service.rbac;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.UserIpHeart;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface UserIpHeartService {
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void save(UserIpHeart userIpHeart);
	
	public abstract PageVM<UserIpHeart> userIpHeartQuery(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	/**
	 * 获得5分钟都没有心跳的数据，心跳数据结构为
	 * 
	 * ```java
		String heartBeat = "[{
		    "loginId": "zhangsan",
		    "machineId": "zhangsanNo1",
			"useIp":"[58.216.61.82, 58.216.61.81, 58.216.61.83]"
		}]"
		```
	 */
	public abstract List<UserIpHeart> getNoHeartData();
}
