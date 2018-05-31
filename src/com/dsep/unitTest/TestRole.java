package com.dsep.unitTest;

/**
 * @author fanghongyu
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.entity.User;
import com.dsep.service.rbac.RightService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml", })
public class TestRole {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RightService rightService;
	private User user;
	private Role role;
	private Right right;

	@Before
	public void setUp() {
		role = new Role();
		role.setId("12345");
		role.setName("test");
	}

	/**
	 * functions need to be tested getUserRoles(String) getRoles(int, int, bool,
	 * String) newRole(Role) update(Role) deleteRole(String) getRole(String)
	 */
	@Test
	public void getUserRolesTest() {

	}

	@Test
	public void newRoleTest() {
		try {
			//Boolean realResult = roleService.newRole(role);
			//Assert.assertTrue(realResult);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void test_date(){
		Date currentDate = new Date();
		Date theDate = new Date();
		theDate.setHours(5);
		int date2 = theDate.getDate();
		int date1 = currentDate.getDate();
		if( date1 > date2 ){
			System.out.println(currentDate.toString() + ">"+theDate.toString());
			System.out.println(date1+">"+date2);
		}
		else if( date1 < date2 ){
			System.out.println(currentDate.toString() + "<"+theDate.toString());
			System.out.println(date1+"<"+date2);
		}
		else{
			System.out.println(currentDate.toString() + "="+theDate.toString());
			System.out.println(date1+"="+date2);
		}
	}

	@Test
	public void deleteRoleTest() {
		try {
			//Boolean realResult = roleService.deleteRole("12345");
			//Assert.assertTrue(realResult);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}

}
