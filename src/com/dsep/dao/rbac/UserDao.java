package com.dsep.dao.rbac;
import java.util.Date;
import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;


public interface  UserDao extends Dao<User,String> 
{	
	/**
	 * 检验用户信息
	 * @param id
	 * @param password
	 * @return
	 */
	public abstract boolean validatorUser(String id,String password);
	/**
	 * 通过loginId获取用户
	 * @param loginId
	 * @return
	 */
	public abstract User getByLoginId(String loginId);
	
	/**
	 * 根据用户类型返回特定pageindex，pageSize的用户列表
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @param userType
	 * @return
	 */
	public abstract List<User> getPageUsersByUserType(int pageIndex, int pageSize, Boolean desc,String orderProperName, String userType);
	/**
	 * 根据用户类型返回所有此类型的用户
	 * @param userType
	 * @return
	 */
	/**
	 * 返回本学校的所有学科
	 * @param userType
	 * @return
	 */
	public abstract List<User> getDisciplineUsersByUnitId(String unitId);
	
	public abstract List<User> getSearchUsers(String loginId, String unitId,String discId,
			String name,String userType, int page,int pageSize);
	
	public abstract int getSearchCount(String loginId, String unitId,String discId,
			String name,String userType);
	
	
	public abstract List<User> getUsersByUserType(String userType);
	/**
	 * 根据学校ID返回本学校学科用户
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @param unitId
	 * @return
	 */
	public abstract List<User> getDisciplineUsersByUnitId(int pageIndex, int pageSize, Boolean desc,String orderProperName, String unitId);
	/**
	 * 更新登录信息
	 * @param loginIp
	 * @param loginTime
	 */
	public abstract void updateLoginInfo(String loginIp, Date loginTime, String id);
	
	/**
	 * 更新用户密码
	 */
	
	public abstract void updateUserPassword(String password, String id);

	/**
	 * 根据用户来源删除用户，目前只用于删除专家用户
	 * @param 用户来源  9是专家用户
	 */
	public abstract int deleteUserBySource(int source);
	/**
	 * 获取user ids 通过学校和学科
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract List<String> getUserIdsByUnitIdAndDiscId(String unitId,String discId);
	
	/**
	 * 通过学校Id,一级学科Id,二级学科ID,对用户信息与“教师学科表”进行左连接查询
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @return
	 */
	public List<User> getUserByDisc12(String teachLoginId,String teachName,String unitId, String discId1,
			String discId2,int pageIndex,int pageSize,String orderProperName,Boolean desc);
	
	/**
	 * 获取某个学校、一级教师学科、二级教师的人数
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @return
	 */
	public int getCount12(String teachLoginId,String teachName,String unitId,String discId1,String discId2);
	
	
}
