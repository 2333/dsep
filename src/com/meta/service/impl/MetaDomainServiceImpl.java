package com.meta.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.meta.dao.MetaAttributeDao;
import com.meta.dao.MetaDomainDao;
import com.meta.dao.MetaEntityDao;
import com.meta.entity.MetaDomain;
import com.meta.entity.MetaDomainState;
import com.meta.service.MetaDomainService;

public class MetaDomainServiceImpl implements MetaDomainService {

	private MetaDomainDao metaDomainDao;
	private MetaEntityDao metaEntityDao;
	private MetaAttributeDao metaAttributeDao;

	//@Cacheable(value ="metaDomainCache",key="#occasion")
	public MetaDomain getAvailDomain(String occasion) {
		List<MetaDomain> list = getAllAvailDomain(occasion);
		if ( list!=null && list.size()>0) return list.get(0);
		else return null;
	}
	
	public String getEntityId(MetaDomain domain, String category, int seqNo){
		return "E" + domain.getPostfix() + category.toString().toUpperCase() + String.format("%03d", seqNo);
	}
	

	@Override
	public MetaDomain getById(String domainId) {
		return metaDomainDao.get(domainId);
	}
	
	public List<MetaDomain> getAllAvailDomain(String occasion) {	
		return metaDomainDao.getAllAvailDomain(occasion, MetaDomainState.NEW);
	}
	
	public boolean buildDomainEntities(MetaDomain domain) {		
		return true;
	}
	
	public MetaDomainDao getMetaDomainDao() {
		return metaDomainDao;
	}

	public void setMetaDomainDao(MetaDomainDao metaDomainDao) {
		this.metaDomainDao = metaDomainDao;
	}

	public MetaEntityDao getMetaEntityDao() {
		return metaEntityDao;
	}

	public void setMetaEntityDao(MetaEntityDao metaEntityDao) {
		this.metaEntityDao = metaEntityDao;
	}

	public MetaAttributeDao getMetaAttributeDao() {
		return metaAttributeDao;
	}

	public void setMetaAttributeDao(MetaAttributeDao metaAttributeDao) {
		this.metaAttributeDao = metaAttributeDao;
	}


}
