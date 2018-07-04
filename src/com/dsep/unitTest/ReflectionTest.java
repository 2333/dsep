package com.dsep.unitTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;

import com.dsep.service.databackup.TestService;
import com.dsep.service.publicity.objection.PublicityService;
import com.meta.entity.PgshTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class ReflectionTest {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private PublicityService publicityService;
	
	private void processType(Field theField,Object obj){
		String typeName = theField.getType().getName();
		try {
			theField.set(obj, "20");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test_Reflection(){
		try {
			List<PgshTest> theList = testService.getPgshTest();
			System.out.println("----------------------"+theList.size()+"------------------------");
			/*Class thePgsh = Class.forName("com.meta.entity.PgshTest");*/
		    /*Field[] field = thePgsh.getClass().getDeclaredFields();*/
			for(int i=0;i < theList.size();i++){
				PgshTest pgshInList = theList.get(i);
				Field[] field = pgshInList.getClass().getDeclaredFields();
				for(int j=0;j < field.length;j++){
					System.out.print(j+":");
					field[j].setAccessible(true);
					System.out.println(field[j].getName() + ":" + field[j].get(pgshInList));
				}
				System.out.println("-------------------------------");
				/*System.out.println("-------------------"+theList.get(i).getId()+"----------------------");
				System.out.println("-------------------"+theList.get(i).getName()+"----------------------");*/
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 已知类名和函数名，通过反射机制调用函数
	 */
	@Test
	public void test_callCertainFunctionThroughName(){
		try {
			Class invokeClass = Class.forName("com.dsep.service.publicity.objection.impl.PublicityServiceImpl");
			Method method = invokeClass.getMethod("autoFinishPublicityRound"); 
			method.invoke(publicityService);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test(){
		/*Class demo = null;
		try {
			demo = Class.forName("com.dsep.dao.databackup.impl.TheReflection");
			TheReflection re = (TheReflection)demo.newInstance();
			Field[] field = demo.getDeclaredFields();
			Method[] method = demo.getDeclaredMethods();
			for(int i=0;i < field.length ;i++){
				field[i].setAccessible(true);
				field[i].set(re, "20");
				System.out.println("--------"+field[i].getName()+"---------");
			}
			System.out.println("-------------"+re.getTestValue()+"----------");
		}
		catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
