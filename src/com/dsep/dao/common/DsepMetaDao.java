package com.dsep.dao.common;

import java.util.List;
import java.util.Map;

/**
 * 以实体为查询条件的无法处理日期型！！！
 * @author Pangeneral
 *
 * @param <T>
 * @param <PK>
 */
public interface DsepMetaDao <T, PK> extends Dao<T,PK>{
	
	/**
	 * 更新表某行中的某几列数据
	 * @param id 需要更新的行的主键值
	 * @param editRow key为类的属性名，value为类的属性值
	 * @return
	 */
	public int updateColumn(String id,Map<String, Object> editRow) throws NoSuchFieldException, SecurityException;

	
	/**
	 * 以实体为条件查询数据
	 * @param t 查询实体，实体的属性不为空的均为查询条件
	 * 例：类ClassA有三个属性B1,B2,B3，现在想根据B1、B2查询数据，则设置t的B1、B2为查询条件值，B3为空
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<T> queryByCondition(T t) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 以实体为条件删除数据
	 * @param t 查询实体，实体的属性不为空的为查询条件
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int deleteByCondition(T t) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 以实体为条件查询排序数据
	 * @param t
	 * @param orderProperty 排序的属性
	 * @param desc true为降序，false为升序
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public List<T> queryByCondition(T t,String orderProperty,boolean desc) throws IllegalArgumentException, IllegalAccessException;
	
	public T querySingleDataByCondition(T t) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 以实体为条件获取查询的分页数据
	 * @param t
	 * @param pageIndex 从0开始
	 * @param pageSize 页大小
	 * @param desc 
	 * @param orderProperName 排序的属性名
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<T> queryByCondition(T t,int pageIndex, int pageSize,
			Boolean desc, String orderProperName) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 以实体为查询条件获取数据总数
	 * @param t
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public int getCountByCondition(T t) throws IllegalArgumentException, IllegalAccessException;


	/**
	 * 以实体为条件更新某一条记录，如果t的某条属性不为空则更新该字段，为空则不处理
	 * 但不适用于自定义类型
	 * @param pk
	 * @param t
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception 
	 */
	public int updateColumn(String pk, T t) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, Exception;
	
}
