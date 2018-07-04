package com.dsep.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.dao.dsepmeta.base.CategoryDao;
import com.dsep.entity.dsepmeta.Category;
import com.dsep.service.base.DiscCategoryService;

public class DiscCategoryServiceImpl implements DiscCategoryService{
	
	private CategoryDao categoryDao;
	
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Override
	public Map<String, String> getAllCategoryMap() {
		// TODO Auto-generated method stub
		List<Category> categoryList = categoryDao.getAll();
		Map<String,String> categoryMap = new HashMap<String,String>();
		for(Category theCategory:categoryList){
			categoryMap.put(theCategory.getId(), theCategory.getName());
		}
		return categoryMap;
	}
}
