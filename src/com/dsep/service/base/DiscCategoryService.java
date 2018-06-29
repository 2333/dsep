package com.dsep.service.base;

import java.util.Map;

public interface DiscCategoryService {
	/**
	 * 获取所有学科门类的集合
	 * @return key为门类ID，value为门类名称
	 */
	public Map<String,String> getAllCategoryMap();
}
