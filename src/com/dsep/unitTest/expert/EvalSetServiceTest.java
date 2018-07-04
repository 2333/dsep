package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.expert.evaluation.EvalIndicIdxService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class EvalSetServiceTest {
	@Autowired
	private EvalIndicIdxService evalSetService;
	
	@Test
	public void testSavePaper(){
		String discId = "0835";
		String expertId = "95ED4310D31140B7BE384428902AAA35";
	    //evalSetService.getIndicatorIndexQuestions(expertId,discId);
	    
	}
	
	@Test 
	public void testProcessData(){
		String discId = "0835";
		String expertId = "7CE28532A057423EA628B64219567703";
		//.getAchievementProcess(expertId, discId);
	}
}
