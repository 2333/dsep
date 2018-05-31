package com.dsep.unitTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.Discipline;
import com.dsep.entity.User;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.rbac.RightService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class TestUser {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RightService rightService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private DisciplineService disciplineService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Test
	public void test1() {
	Boolean result =false;
	
	if(userService == null)	
		result=false;
	else
		result=userService.validateUser("10006", "test");
	System.out.print(result);
	}

	@Test 
	public void test3() {			
			User u= userService.getUser("10001");
			String json="";
			for(int i=0;i<10;i++)
				json=JsonConvertor.obj2JSON(u);
			System.out.print(JsonConvertor.obj2JSON(u));
	}
	
	/**
	 * 插入学校和学科表
	 */
	@Test
	public void test_insertUnitAndDisc(){
		userService.insertUnitAndDiscFromRbac();
	}
	
	
	@Test
	public void test_insertUser(){
		User user = new User();
		user.setUnitId("10006");
		user.setName("dsafd");
		user.setDiscId("095102");
		user.setPassword("test");
		user.setId(GUID.get());
		user.setLoginId("10006_095102");
		
		userService.newDisciplineUser(user);
	}
	
	@Test 
	public void testReflect() {		
		List<Discipline> disciplines = disciplineService.getDisciplinesByUnitId("10006");
	}
	
	@Test
	public void testSetTeacherRole(){
		teacherService.setTeacherRole();
	}
	@Test
	public void testSetUserRole(){
		userService.setUserRole();
	}
	@Test
	public void test_Insert_From_Expert(){
		/*userService.insertByExpert(expert);*/
	}
	@Test
	public void testLeftJoinTeachDisc(){
		//userService.getLeftJoinTeachDisc("10006", "0835", null, 0, 0, false, "ID");
	}
}
