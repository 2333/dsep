package com.meta.service.impl;

import java.util.List;

import com.meta.dao.MetaEntityCategoryDao;
import com.meta.entity.MetaEntityCategory;
import com.meta.service.MetaEntityCategoryService;


public class MetaEntityCategoryServiceImpl implements MetaEntityCategoryService {
	
	MetaEntityCategoryDao metaEntityCategoryDao;

	@Override
	public List<MetaEntityCategory> getEntityCategories(String occassion) {
		return metaEntityCategoryDao.getByOccassion(occassion);
	}

	public MetaEntityCategoryDao getMetaEntityCategoryDao() {
		return metaEntityCategoryDao;
	}

	public void setMetaEntityCategoryDao(MetaEntityCategoryDao metaEntityCategoryDao) {
		this.metaEntityCategoryDao = metaEntityCategoryDao;
	}

	
}
