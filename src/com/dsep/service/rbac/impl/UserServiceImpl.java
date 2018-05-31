package com.dsep.service.rbac.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dsep.common.annotation.EnableLog;
import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.dao.rbac.UserDao;
import com.dsep.domain.MenuTreeNode;
import com.dsep.entity.Discipline;
import com.dsep.entity.News;
import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.entity.Unit;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.entity.expert.Expert;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Configurations;
import com.dsep.util.GUID;
import com.dsep.util.MD5;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserVM;


public class UserServiceImpl implements UserService{

	private UserDao userDao;
	private RightDao rightDao;
	private RoleDao roleDao;
	private UnitService unitService;
	private DisciplineService disciplineService;
	
	@EnableLog
	private Logger logger;
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}
    public void setRightDao(RightDao rightDao)
    {
    	this.rightDao=rightDao;
    }
	
    public User getUser(String id) 
	{
		return userDao.get(id);
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
	public void newUser(User user, List<String> userRoleIds) 
	{
		if (null == user.getId()) {
			user.setId(GUID.get());//添加用户ID
		}
		String orgPassword=user.getPassword();
		String md5Password=MD5.getMd5Str(orgPassword);		
		user.setPassword(md5Password);
		user.setRoles(GetRoleList(userRoleIds));
		userDao.save(user);
	}
	
	
	/*
	 *根据角色ID获取角色对象 
	 */
	private Set<Role> GetRoleList(List<String> userRoleIds)
	{
		HashSet<Role> role = new HashSet<Role>();
		for(String roleId:userRoleIds)
		{
			Role newRole = roleDao.get(roleId);
			role.add(newRole);
		}
		return role;
	}

	public boolean validateUser(String id,String password) 
	{
		//logger.info("验证用户信息！");
		String Md5Password= MD5.getMd5Str(password);
		return userDao.validatorUser(id,Md5Password);
	}
	
	public void UpdateUser(User user,List<String> userRoleIds) 
	{
			String orgPassword=user.getPassword();
			String md5Password=MD5.getMd5Str(orgPassword);
			user.setPassword(md5Password);
			user.setRoles(GetRoleList(userRoleIds));
			userDao.saveOrUpdate(user);
	}

	public PageVM<UserVM> getUsers(int pageIndex, int pageSize, Boolean desc,
			String orderProperName) 
	{
		List<User> list=userDao.page(pageIndex, pageSize,desc,orderProperName);
		
		int totalCount=userDao.Count();
		List<UserVM> vmList= new ArrayList<UserVM>();
		for(User u : list){
			UserVM vm= new UserVM();
			vm.setUser(u);
			vmList.add(vm);
		}
		PageVM<UserVM> result=new PageVM<UserVM>(pageIndex,totalCount,pageSize,vmList);
		return result;
	}

	
	@Override
	public PageVM<UserVM> getSearchUsers(String loginId, String unitId, String discId,
			String name,String userType, int page, int pageSize) {
		
		List<User> list = userDao.getSearchUsers(loginId, unitId,discId,name,userType,page,pageSize);
		int totalCount = userDao.getSearchCount(loginId, unitId, discId, name, userType);
		
		List<UserVM> vmList= new ArrayList<UserVM>();
		
		for(User u : list){
			UserVM vm= new UserVM();
			vm.setUser(u);
			vmList.add(vm);
		}
		PageVM<UserVM> result=new PageVM<UserVM>(page ,totalCount, pageSize , vmList);
		return result;
	}
	@Override
	public PageVM<UserVM> getUsersByUserType(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String userType) {
		// TODO Auto-generated method stub
		List<User> list=userDao.getPageUsersByUserType(pageIndex, pageSize,desc,orderProperName, userType);
		int totalCount=userDao.getUsersByUserType(userType).size();
		
		List<UserVM> vmList= new ArrayList<UserVM>();
		for(User u : list){
			UserVM vm= new UserVM();
			vm.setUser(u);
			vmList.add(vm);
		}
		PageVM<UserVM> result=new PageVM<UserVM>(pageIndex,totalCount,pageSize,vmList);
		return result;
	}

	public void deleteUser(String userId) 
	{
		userDao.deleteByKey(userId);
	}


	public List<MenuTreeNode> getUserRightTree(String userId) 
	{
		// TODO Auto-generated method stub
		List<Right> rights=this.rightDao.getMenuRights(userId);//获取菜单权限，倒序根据level
		List<MenuTreeNode> rightNodes = new ArrayList<MenuTreeNode>();
		
		for(Right r:rights)
        {
			MenuTreeNode tn = new MenuTreeNode();
			tn.id =r.getId();
			tn.name = r.getName(); 
			tn.relativeSeq=r.getSeqNo();
			if(r.getUrl().equals("#"))
				tn.url = '/'+ r.getUrl();
			else
				//tn.url = "/DSEP/" + r.getUrl();
				tn.url = "/"+Configurations.getUrlContextPath()+"/"+r.getUrl();
			rightNodes.add(tn);
        }

        int treeIndex = 0;
        
        for (int i = 0; i < rights.size(); i++)
        {
        	MenuTreeNode father = GetFatherNode(rightNodes, rights.get(i).getParentId());
            if (father != null)
            {
                father.addChild(rightNodes.get(treeIndex));
                rightNodes.remove(rightNodes.get(treeIndex));
                continue;
            }
            treeIndex++;
        }

		/*Map<String, MenuTreeNode> rightNodes = new HashMap<String,MenuTreeNode>();
		for(Right r:rights)
		{
			String rightId=r.getRightId();
			String rightUrl=r.getRightUrl();
			String rightName=r.getRightName();
			String fRightId=r.getFrightId();
			int rightLevel=r.getRightLevel();
			MenuTreeNode newNode=new MenuTreeNode(rightId,rightName,rightUrl);
			if(rightLevel == 0 && !rightNodes.containsKey(rightId))
					rightNodes.put(rightId, newNode);
			if(r.getRightLevel() == 1 && rightNodes.containsKey(fRightId))	//由于rights按rightlevel排序取出，此if为true时所有父节点已经加载完毕
					rightNodes.put(fRightId, (rightNodes.get(fRightId).addChild(newNode)));
		}	*/	
		return rightNodes;
	}
	
	//工具函数，获取树节点的父节点
	private MenuTreeNode GetFatherNode(List<MenuTreeNode> tree, String fatherID)
    {
        for (int i = tree.size() -1; i >= 0 ; i--)
        {
        	String nowId = tree.get(i).id;
            if(nowId.equals(fatherID)) 
            	return tree.get(i);
        }
        return null;
    }
	@Override
	public User getUserByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return userDao.getByLoginId(loginId);
	}
	
	/**
	 * 获取所有学校用户
	 */
	@Override
	public PageVM<UserVM> getSchoolUsers(int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		return getUsersByUserType(pageIndex, pageSize, desc, orderProperName, "2");
	}
	
	/**
	 * 获取学校用户为unitId的所有学科用户
	 */
	@Override
	public PageVM<UserVM> getDisciplineUsers(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String unitId) {
		// TODO Auto-generated method stub\
		List<User> list=userDao.getDisciplineUsersByUnitId(pageIndex, pageSize, desc, orderProperName, unitId);
		int totalCount=userDao.getDisciplineUsersByUnitId(unitId).size();
		List<UserVM> vmList= new ArrayList<UserVM>();
		for(User u : list){
			UserVM vm= new UserVM();
			vm.setUser(u);
			vmList.add(vm);
		}
		PageVM<UserVM> result=new PageVM<UserVM>(pageIndex,totalCount,pageSize,vmList);
		return result;
	}
	/**
	 * 添加一个学校用户，userType = 2
	 */
	@Override
	public void newSchoolUser(User user){
		
		//添加用户类型
		//System.out.println("----------"+user.getId()+"---------");
		user.setUserType("2");
		
		user.setLoginId(user.getUnitId());
		//添加学校角色给用户
		List<String> userRoleIds = new ArrayList<String>();
		userRoleIds.add("t0003");
		//新建用户
		newUser(user, userRoleIds);
	}
	
	/**
	 * 添加一个学科用户，userType = 3
	 */
	@Override
	public void newDisciplineUser(User user) {
		// TODO Auto-generated method stub
		
		//添加用户类型
		user.setUserType("3");
		
		//添加学科角色给用户
		List<String> userRoleIds = new ArrayList<String>();
		userRoleIds.add("t0002");
		//新建用户
		newUser(user, userRoleIds);
	}
	

	@Override
	public void insertByExpert(Expert expert,String email) {
		User user = new User();
		user.setId(expert.getId());
		user.setUserType("5");//5为专家类型
		user.setLoginId(email);
		user.setPassword("000000");
		user.setName(expert.getExpertName());
		user.setSource(9);
		String discId = (null != expert.getDiscId()) ? expert.getDiscId() : expert.getDiscId2(); 
		user.setDiscId(discId);
		
		//添加专家角色给用户
		List<String> userRoleIds = new ArrayList<String>();
		userRoleIds.add("t0006");
		
		newUser(user, userRoleIds);
	}
	
	@Override
	public void insertBySurveyUser(SurveyUser surveyUser, String email) {
		User user = new User();
		user.setId(surveyUser.getId());
		user.setUserType("6");//6为问卷调查用户类型
		user.setLoginId(email);
		user.setPassword("000000");
		user.setName(surveyUser.getName());
		user.setSource(0);
		user.setDiscId(surveyUser.getDiscId());
		
		//添加问卷调查角色给用户
		List<String> userRoleIds = new ArrayList<String>();
		userRoleIds.add("t0007");
		
		newUser(user, userRoleIds);
	}

	@Override
	public void updateLoginInfo(String loginIp, Date loginTime, String id) {
		userDao.updateLoginInfo(loginIp, loginTime, id);
	}
	
	@Override
	public List<Right> getUserRightsByType(String userId, String type) {
		
		List<Role> roles = roleDao.getUserRole(userId);
		
		List<Right> roleRights;
		List<Right> rights = new ArrayList<Right>();
		
		for(Role role : roles){
			roleRights = rightDao.getRoleRights(role.getId(), type);
			for(Right right:roleRights){
				rights.add(right);
			}
		}
		return rights;
	}
	@Override
	public List<String> getUserIdsByUnitIdAndDiscId(String unitId, String discId) {
		// TODO Auto-generated method stub
		return userDao.getUserIdsByUnitIdAndDiscId(unitId, discId);
	}
	@Override
	public List<User> getTeachDiscByDisc12(String teachLoginId,String teachName,String unitId, String discId1,
			String discId2, int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		// TODO Auto-generated method stub
		return userDao.getUserByDisc12(teachLoginId,teachName,unitId, discId1, discId2, pageIndex, pageSize, orderProperName, desc);
	}
	@Override
	public int getUserCount(String teachLoginId,String teachName,String unitId, String discId1, String discId2) {
		// TODO Auto-generated method stub
		return userDao.getCount12(teachLoginId,teachName,unitId, discId1, discId2);
	}
	
	
	@Override
	public int insertUnitAndDiscFromRbac() {
		// TODO Auto-generated method stub
		List<Unit> unitList = unitService.getAllUnits();
		int result = 0;
		Role theUnitRole = roleDao.get("t0003");
		Role theDiscRole = roleDao.get("t0002");
		for(Unit theUnit:unitList){
			User newUser = new User();
			newUser.setLoginId(theUnit.getId());
			newUser.setName(theUnit.getName());
			newUser.setUnitId(theUnit.getId());
			newUser.setPassword("test");
			newUser.setUserType("2");
			Set<Role> roleSet = new HashSet<Role>();
			//Role theRole = roleDao.get("t0003");
			roleSet.add(theUnitRole);
			result++;
			newUser.setRoles(roleSet);
			newUser.setId(GUID.get());
			/*if( this.getUserByLoginId(theUnit.getId()) != null){
				//continue;
				//userDao.saveOrUpdate(newUser);
			}else{
				userDao.save(newUser);
			}*/
			userDao.save(newUser);
			List<Discipline> discList = disciplineService.getDisciplinesByUnitId(theUnit.getId());
			for(Discipline theDisc:discList){
				User discUser = new User();
				discUser.setLoginId(theUnit.getId()+"_"+theDisc.getId());
				discUser.setName(theDisc.getName());
				discUser.setUnitId(theUnit.getId());
				discUser.setDiscId(theDisc.getId());
				discUser.setPassword("test");
				discUser.setUserType("3");
				Set<Role> discRoleSet = new HashSet<Role>();
				//Role discRole = roleDao.get("t0002");
				discRoleSet.add(theDiscRole);
				discUser.setRoles(discRoleSet);
				discUser.setId(GUID.get());
				/*if( this.getUserByLoginId(discUser.getLoginId()) != null){
					//continue;
					//userDao.saveOrUpdate(discUser);
				}else{
					userDao.save(discUser);
				}*/
				userDao.save(discUser);
				result++;
			}
			userDao.flush();
		}
		// Hibernate commit会call flush,flush不会call flush
		// 这里为什么反了？事务问题
		
		//System.out.println("+++++++++++++++++++++"+result+"++++++++++++++++++++");
		return result;
	}
	@Override
	public void setUserRole() {
		// TODO Auto-generated method stub
		Role role1,role2,role3;
		role1 = roleDao.get("t0004");//中心
		role2 = roleDao.get("t0003");//学校
		role3 = roleDao.get("t0002");//学科
		List<User> users = userDao.getAll();
		for(User user : users){
			String userType = user.getUserType();
			Set<Role> roles = new HashSet<Role>(0);
			switch(userType){
				case "1":
					//中心
					//Role role1 = roleDao.get("t0004");
					roles.add(role1);
					user.setRoles(roles);
					userDao.saveOrUpdate(user);
					break;
				case "2":
					//学校
					//Role role2 = roleDao.get("t0003");
					roles.add(role2);
					user.setRoles(roles);
					userDao.saveOrUpdate(user);
					break;
				case "3":
					//学科
					//Role role3 = roleDao.get("t0002");
					roles.add(role3);
					user.setRoles(roles);
					userDao.saveOrUpdate(user);
					break;
				case "4":
					//教师
					break;
				default:
					break;
			}
		}
		userDao.flush();
	}
	
	@Override
	public void updateUserPassword(String password, String id) {
		userDao.updateUserPassword(password, id);
	}
	
}
