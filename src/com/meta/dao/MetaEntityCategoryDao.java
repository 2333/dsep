package com.meta.dao;
import java.util.List;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaEntityCategory;

public interface MetaEntityCategoryDao extends Dao<MetaEntityCategory, String>{
	/**
	 * 通过分类的使用场合，得到某个该场合下所有的分类信息
	 * @param occassion 分类的使用场合
	 * @return 适合于该使用场合的顶层分类，各子分类可以通过顶层分类的子类获得
	 */
	public abstract List<MetaEntityCategory> getByOccassion(String occassion);
	
	/**
	 * 通过分类使用场合，得到某个父类的分类列表
	 * @param occassion 分类的使用场合
	 * @param parentId 父分类号，""则为顶层分类，null则返回所有层次的分类
	 * @return 该父类对应的子分类列表
	 */
	public abstract List<MetaEntityCategory> getByOccassion(String occassion, String parentId);
}
