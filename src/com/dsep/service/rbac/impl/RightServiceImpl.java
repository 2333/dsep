package com.dsep.service.rbac.impl;
/**
 * @author fanghongyu
 * @date   2013/09/04/11:24
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.service.rbac.RightService;
import com.dsep.vm.PageVM;
import com.dsep.vm.RightVM;
import com.dsep.vm.CheckTreeVM;

public class RightServiceImpl implements RightService {
	private RightDao rightDao;
	private RoleDao roleDao;
	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public RightDao getRightDao() {
		return rightDao;
	}

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}
	
	
	@Override
	public Right getRight(String rightId) {
		return rightDao.get(rightId);
	}

	@Override
	public PageVM<RightVM> getRights(int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		List<Right> list = rightDao.page(pageIndex, pageSize, desc, orderProperName);
		List<RightVM> vmList=new ArrayList<RightVM>();
		for(Right r : list){
			RightVM vm=new RightVM();
			vm.setRight(r);
			vmList.add(vm);
		}
		int totalCount=rightDao.Count();
		PageVM<RightVM> result=new PageVM<RightVM>(pageIndex,totalCount,pageSize,vmList);
		return result;
	}

	@Override
	public void newRight(Right right){
		rightDao.save(right);
	}

	@Override
	public void updateRight(Right right) {
		rightDao.saveOrUpdate(right);
	}

	@Override
	public void deleteRight(String rightId) {
		rightDao.deleteRoleRight(rightId);
		rightDao.deleteRight(rightId);
	}
    
	public List<CheckTreeVM> getRoleRights(String roleId) {
		Set<Right> rolerights = null;
		List<CheckTreeVM> tree= new ArrayList<CheckTreeVM>();
		Role role = roleDao.get(roleId);//获取角色对象
		if(role != null)	
			rolerights=role.getRights();//获取该角色所有菜单权限
		
		List<Right> allRights = rightDao.getAll();//获取所有菜单权限
		for(Right r : allRights){
			if(this.SetContainRight(rolerights,r.getId()))
			{
				tree.add(RightToNode(r, true));
			}
			else{
				tree.add(RightToNode(r, false));
			}	
		}
		return tree;
	}

    //获取角色菜单权限
	public List<CheckTreeVM> getMenuRoleRights(String roleId) {
		Set<Right> rolerights = null;
		Set<Right> menurolerights = new HashSet<Right>();
		List<CheckTreeVM> tree= new ArrayList<CheckTreeVM>();
		Role role = roleDao.get(roleId);//获取角色对象
		if(role != null){	
			rolerights=role.getRights();//获取该角色所有权限
			for (Right str:rolerights) {  //获取该角色所有菜单权限
			      if(str.getCategory().equals("1"))
			      {
			    	  menurolerights.add(str);
			      }
			}  
		}
		List<Right> allRights = rightDao.getAll();//获取所有权限
		List<Right> allMenuRights = new ArrayList<Right>();
		for (Right str:allRights) {                //获取所有菜单权限
		      if(str.getCategory().equals("1"))
		      {
		    	  allMenuRights.add(str);
		      }
		}  
		for(Right r : allMenuRights){
			if(this.SetContainRight(menurolerights,r.getId()))
			{
				tree.add(RightToNode(r, true));
			}
			else{
				tree.add(RightToNode(r, false));
			}	
		}
		return tree;
	}
	//获取角色动作权限
	public List<CheckTreeVM> getActionRoleRights(String roleId) {
		Set<Right> rolerights = null;
		Set<Right> actionrolerights = new HashSet<Right>();;
		List<CheckTreeVM> tree= new ArrayList<CheckTreeVM>();
		Role role = roleDao.get(roleId);//获取角色对象
		if(role != null){	
			rolerights=role.getRights();//获取该角色所有权限
			for (Right str:rolerights) {     //获取该角色所有动作权限
			      if(str.getCategory().equals("2"))
			      {
			    	  actionrolerights.add(str);
			      }
			}  
		}
		List<Right> allRights = rightDao.getAll();//获取所有权限
		List<Right> allActionRights = new ArrayList<Right>();
		for (Right str:allRights) {             //获取所有动作去想念  
		      if(str.getCategory().equals("2"))
		      {
		    	  allActionRights.add(str);
		      }
		}  
		for(Right r : allActionRights){
			if(this.SetContainRight(actionrolerights,r.getId()))
			{
				tree.add(RightToNode(r, true));
			}
			else{
				tree.add(RightToNode(r, false));
			}	
		}
		return tree;
	}
	private Boolean SetContainRight(Set<Right> rights, String rightId)
	{
		if(rights == null)
			return false;
		for(Right r : rights)
			if(r.getId() == rightId)
				return true;
		return false;
	}
	
	private CheckTreeVM RightToNode(Right r,boolean checked)
	{
		CheckTreeVM node= new CheckTreeVM();
		node.setChecked(checked);
		node.setId(r.getId());
		node.setName(r.getName());
		node.setOpen(checked);
		if(r.getParentId()==null)
			node.setpId("root");
		else 
			node.setpId(r.getParentId());
		return node;
	}

	//检查Right是否存在
	public boolean checkRightId(String rightId) {
		// TODO Auto-generated method stub
		Right r = rightDao.get(rightId);
		return r!=null? true : false;
	}

}
