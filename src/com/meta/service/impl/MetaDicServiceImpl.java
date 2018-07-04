package com.meta.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.dsep.common.exception.BusinessException;
import com.dsep.domain.dsepmeta.jqcol.dicrule.JqGridColDicRule;
import com.meta.dao.MetaDicDao;
import com.meta.domain.MetaDicDomain;
import com.meta.entity.MetaDic;
import com.meta.service.MetaDicService;

public class MetaDicServiceImpl implements MetaDicService {
	private MetaDicDao metaDicDao;
	
	@Cacheable(value ="metaDicCache",key="#id")
	public MetaDicDomain getById(String id) {
		MetaDic dic = metaDicDao.get(id);
		if(dic==null){
			throw new BusinessException("MetaDicServiceImpl, 无法找到当前字典对象："+id);
		}
		MetaDicDomain dicDomain= new MetaDicDomain(dic);
		return dicDomain;
	}
	
	public MetaDicDao getMetaDicDao() {
		return metaDicDao;
	}
	public void setMetaDicDao(MetaDicDao metaDicDao) {
		this.metaDicDao = metaDicDao;
	}

	@Override
	public MetaDicDomain getMetaDicDomain(String id,
			List<JqGridColDicRule> dicRules) {
		// TODO Auto-generated method stub
		if(dicRules==null||dicRules.size()==0){
			return getById(id);
		}else{
			
		}
		return null;
	}
	
}
