package com.dsep.service.flow.impl;

import com.dsep.service.flow.DsepMainFlowService;
import com.dsep.util.Configurations;
import com.dsep.util.Dictionaries;
import com.meta.dao.MetaDomainDao;
import com.meta.entity.MetaDomain;

public class DsepMainFlowServiceImpl implements DsepMainFlowService{
	private MetaDomainDao metaDomainDao;
	@Override
	public String getCurrentState() {
		// TODO Auto-generated method stub
		MetaDomain metaDomain = metaDomainDao.get(Configurations.getCurrentDomainId());
		if("1".equals(metaDomain.getState())){
			return Dictionaries.getMainFlowType(metaDomain.getInnerState());
		}else{
			return "本轮评估已过期！";
		}
	}

	@Override
	public String getInnerState() {
		// TODO Auto-generated method stub
		MetaDomain metaDomain = metaDomainDao.get(Configurations.getCurrentDomainId());
		if("1".equals(metaDomain.getState())){
			return metaDomain.getInnerState();
		}else{
			return null;
		}
	}
	@Override
	public boolean updateState(String state) {
		// TODO Auto-generated method stub
		return metaDomainDao.updateState(Configurations.getCurrentDomainId(), state);
	}

	@Override
	public boolean updateInnerState(String innerState) {
		// TODO Auto-generated method stub
		return metaDomainDao.updateInnerState(Configurations.getCurrentDomainId(), innerState);
	}
	@Override
	public MetaDomain getCurrentMetaDomain() {
		// TODO Auto-generated method stub
		String domainId = Configurations.getCurrentDomainId();
		return metaDomainDao.get(domainId);
	}
	
	public MetaDomainDao getMetaDomainDao() {
		return metaDomainDao;
	}

	public void setMetaDomainDao(MetaDomainDao metaDomainDao) {
		this.metaDomainDao = metaDomainDao;
	}

	
}
