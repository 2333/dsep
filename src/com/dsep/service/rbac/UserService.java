package com.dsep.service.rbac;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.MenuTreeNode;
import com.dsep.entity.Right;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.entity.expert.Expert;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserIpOnLineVM;
import com.dsep.vm.UserVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface UserService {

	/** 获取user实体
	 * @param id
	 * @return
	 */
	public abstract User getUser(Integer userId);

	/** 新建user
	 * @param user
	 * @param userRoleIds
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void newUser(User user, List<String> userRoleIds);
	
	/** 更新User,包括该用户的角色
	 * @param user
	 * @param 用户所有的角色id
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void UpdateUser(User user,List<String> userRoleIds);
	
	
	/** 更新User,包括该用户的ip
	 * @param user，其中user中已经set了ip set
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void UpdateUserAndIps(User user);
	
	/** 验证user用户名密码是否正确
	 * @param user
	 * @return
	 */
	public  abstract boolean validateUser(String name,String password);
	
	/** 分页取用户
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderStr
	 * @return
	 */
	public abstract PageVM<UserVM> getUsers(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	/**
	 * 查询获取用户
	 */
	public abstract PageVM<UserVM> getSearchUsers(String loginId, String unitId,String discId,String name,String userType,int page,int pageSize);
	/**
	 * 分页根据用户类型取用户
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @param userType
	 * @return
	 */
	public abstract PageVM<UserVM> getUsersByUserType(int pageIndex,int pageSize,Boolean desc,String orderProperName, String userType);
	
	
	/**
	 * 分页根据用户类型取用户
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @param userType
	 * @return
	 */
	public abstract PageVM<UserIpOnLineVM> userIpQuery(int pageIndex,int pageSize,Boolean desc,String orderProperName, String userType);
	
	
	/** 删除用户
	 * @param userId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void deleteUser(Integer userId);
	
	/** 取得用户的角色权限树，用于加载登陆后的菜单。
	 * @param userId
	 * @return Map<权限ID，权限树节点>
	 */
	public abstract List<MenuTreeNode> getUserRightTree(String userId);
	/**
	 * 通过LoginId获取用户
	 * @param loginId
	 * @return user实体
	 */
	public abstract User getUserByLoginId(String loginId);
	
	/**
	 * 获取所有学校用户列表
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 */
	public abstract PageVM<UserVM> getSchoolUsers(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	/**
	 * 获取所有学科用户列表
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 */
	public abstract PageVM<UserVM> getDisciplineUsers(int pageIndex,int pageSize,Boolean desc,String orderProperName, String unitId);
	
	/**
	 * 添加一个学校用户
	 * @param user
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void newSchoolUser(User user);
	
	/**
	 * 添加一个学科用户
	 * @param user
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void newDisciplineUser(User user);
	
	/**
	 * 将遴选的专家插入到用户表中
	 * @param expert 遴选的专家
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void insertByExpert(Expert expert, String email);
	
	/**
	 * 将问卷调查用户插入到用户表中
	 * @param surveyUser 问卷调查用户
	 * @param email
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void insertBySurveyUser(SurveyUser surveyUser, String email);

	/**
	 * 将基础数据的学校用户和学科用户插入到用户表中
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract int insertUnitAndDiscFromRbac();
	

	
	/**
	 * 更新用户登录信息
	 * @param loginIp
	 * @param loginTime
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void updateLoginInfo(String loginIp, Date loginTime, Integer id);
	
	/**
	 * 根据权限类别获取用户当前的权限列表
	 * @param userId
	 * @param type：1菜单权限，2拦截权限
	 * @return
	 */
	public abstract List<Right> getUserRightsByType(Integer userId, String type);
	/**
	 * 通过学校Id 和学科Id 获取用户id
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract List<String> getUserIdsByUnitIdAndDiscId(String unitId,String discId);
	
	/**
	 * 通过学校ID,一级学科ID,二级学科ID，做连接教师学科表
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 */
	public abstract List<User> getTeachDiscByDisc12(String teachLoginId,String teachName,String unitId,String discId1,String discId2,int pageIndex,int pageSize,Boolean desc,String orderProperName);
	/**
	 * 获取所属某学校 and 一级学科 or 二级学科的用户信息
	 * @param unitId
	 * @param discId1（null 一级学科不作为筛选条件）
	 * @param discId2 （null 二级学科不作为筛选条件）
	 * @return
	 */
	public abstract int getUserCount(String teachLoginId,String teachName,String unitId,String discId1,String discId2);
	
	/**
	 * 为用户设置角色、权限
	 */
	public abstract void setUserRole();
	
	/**
	 * 设置新密码
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void updateUserPassword(String password, Integer id);

	
}