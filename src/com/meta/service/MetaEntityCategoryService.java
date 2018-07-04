package com.meta.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.meta.entity.MetaEntityCategory;

/**
 * 元数据中分类接口
 * @author thbin
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public interface MetaEntityCategoryService {
	/**
	 * 根据使用场合获得所有的分类（分类中所有的实体类也同步获得）
	 * @param occassion 使用场合，数据采集为"C"
	 * @return
	 */
	public abstract List<MetaEntityCategory> getEntityCategories(String occassion);
}
