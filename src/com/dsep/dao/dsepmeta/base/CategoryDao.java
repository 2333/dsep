package com.dsep.dao.dsepmeta.base;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.Category;

public interface CategoryDao extends DsepMetaDao<Category, String>{
	/**
	 * 获取一个门类
	 * @param id
	 * @return
	 */
	public abstract Category getCategoryById(String id);
	/**
	 * 获取门类name
	 * @param id
	 * @return
	 */
	public abstract String getNameById(String id);
	/**
	 * 获取门类指标体系ID
	 * @param id
	 * @return
	 */
	public abstract String getIndexIdById(String id);
	/**
	 * 获取门类的采集项Id
	 * @param id
	 * @return
	 */
	public abstract String getCollectIdById(String id);
	

}
