package com.dsep.dao.dsepmeta.base;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.DiscCategory;

public interface DiscCategoryDao extends DsepMetaDao<DiscCategory,String>{
	/**
	 * 根据学科门类编号获取该门类下所有学科
	 * @param catId
	 * @return
	 */
	public List<String> getDiscByCatId(String catId);
	
	/**
	 * 根据学科编号获取所有门类
	 * @param discId
	 * @return
	 */
	public String getCatByDiscId(String discId);
}
