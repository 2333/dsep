package com.dsep.unitTest;
/**
 * fanghongyu
 */
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.common.logger.LoggerTool;
import com.dsep.dao.rbac.RightDao;
import com.dsep.domain.MenuTreeNode;
import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.service.rbac.RightService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.UserService;
import com.dsep.vm.CheckTreeVM;
import com.meta.unittest.AbstractTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"
		})
public class TestRight extends AbstractTestCase{

	
	@Autowired
	private RightService rightService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private LoggerTool loggerTool;
	/*@Autowired
	private RightDao rightDao;*/
	/*@Test
	public void TestMenuTree() {
		//Map<String, MenuTreeNode> r=userService.getUserRightTree("10001");
		System.out.print(1);
	}
	*/
	@Test
	public void TestRightMap()
	{
		rightService.getRight("1");
		List<CheckTreeVM> tree=rightService.getRoleRights("00004");
		//List<TreeVM> treeRole= roleService.getUserRolesTree("10001");
		System.out.print("1");
	}
	/*@Test
	public void TestRightAll()
	{
		List<Right> rights=rightDao.getAll();
		System.out.println(rights);
	}*/
	@Test
	public void TestUserRights()
	{
		loggerTool.warn("------------right--------------");
		RightDao rightDao=(RightDao)dsContext.getBean("rightDao");
		List<Right> l=rightDao.getUserRights("10006");
		System.out.println("1");
	}
}
