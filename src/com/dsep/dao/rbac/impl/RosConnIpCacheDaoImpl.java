package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.RosConnIpCacheDao;
import com.dsep.entity.Ip;
import com.dsep.entity.RosConnIpCache;



public class RosConnIpCacheDaoImpl extends DaoImpl< RosConnIpCache,Integer> implements RosConnIpCacheDao{
		

	@Override
	public List<RosConnIpCache> getAllIpsByLocation(String rosLocation) {
		// TODO Auto-generated method stub
		String sql = "select * from dsep_rbac_rosconnipcache where roslocation=?";
		List<RosConnIpCache> list = super.sqlFind(sql, new Object[]{rosLocation});
		//return list;
		return list;
	}

	@Override
	public void updateCacheByLocation(RosConnIpCache cache, String rosLocation) {
		String sql = "update dsep_rbac_rosconnipcache where id in"
				+ "(select ip_id from dsep_rbac_user_ip where user_id=?)";
		List<RosConnIpCache> list = super.sqlFind(sql, new Object[]{rosLocation});
		//return list;
	}

	@Override
	public RosConnIpCache getRosConnIpCacheByIpValue(String ipValue) {
		String sql = "select * from dsep_rbac_rosconnipcache where IPVALUE =?";
		List<RosConnIpCache> list = super.sqlFind(sql, new Object[]{ipValue});
		if (null == list || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public RosConnIpCache getRosConnIpCacheByPppoeAndLocation(String pppoe,
			String location) {
		String sql = "select * from dsep_rbac_rosconnipcache where PPPOENAME=? AND ROSLOCATION =?";
		List<RosConnIpCache> list = super.sqlFind(sql, new Object[]{pppoe, location});
		if (null == list || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	

	
}
