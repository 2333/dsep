package com.dsep.controller.rbac;

import java.util.ArrayList;
import java.util.List;

import com.dsep.entity.RosConnIpCache;
import com.dsep.entity.User;
import com.dsep.entity.UserIpHeart;
import com.dsep.service.rbac.RosConnIpCacheService;
import com.dsep.service.rbac.UserIpHeartService;
import com.dsep.service.rbac.UserService;

public class RollingHeart {
	public void rolling(RosConnIpCacheService rosConnIpCacheService, UserIpHeartService userIpHeartService, UserService userService) {
		List<UserIpHeart> list = userIpHeartService.getNoHeartData();
		List<User> userList = new ArrayList<User>();
		for (UserIpHeart heart : list) {
			String loginId = heart.getLoginId();
			String ips = heart.getUseIp();
			if (ips == null || ips.length() == 0) {
				
			} else {
				User user = userService.getUserByLoginId(loginId);
				String[] ipsSpliteArr = ips.split(",");
				
				for (String useIp : ipsSpliteArr) {
					RosConnIpCache rosConnIpCache = rosConnIpCacheService.getRosConnIpCacheByIpValue(useIp);
					if (rosConnIpCache == null) continue;
					String pppoeName = rosConnIpCache.getIpPppoeName();
					String userdPppoeNumber = user.getUsedPppoeNumber();
					String newNumbers = "";
					if (userdPppoeNumber == null || userdPppoeNumber.length() == 0) {
						
					} else {
						String ele = pppoeName;
						String[] numbers = userdPppoeNumber.split(",");
						
						for (String n_ele : numbers) {
							if (n_ele.equals(ele)) {
								
							} else {
								newNumbers = newNumbers + "," + n_ele;
							}
						}
					}
					user.setNownum(user.getNownum() + 1);
					user.setUsedPppoeNumber(newNumbers);
				}
				userList.add(user);
				//
			}
			userService.UpdateUserAndIps(user);
			
		}
		
		
		
		
	}
}
