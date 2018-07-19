package com.dsep.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class EvalAchievementTest {

	/*@Autowired
	private EvalAchievementServiceBAK evalAchievementService;
	
	@Test
	public void test_getEvalItemMessage(){
		evalAchievementService.getExpertEvalItem("189389","0835");
	}
	
	@Test
	public void test_insertIntoEvalAchievement(){
		System.out.println(evalAchievementService.insertIntoAchievement("110473"));
		System.out.println("-------------------------------------------");
	}
	
	@Test
	public void test_Test(){
		evalAchievementService.Test();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}*/

}
