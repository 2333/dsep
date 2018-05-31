package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.expert.EvalQuestion;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class EvalRankingTest {

	@Autowired
	private DMCheckLogicRuleService checkLogicRule;
	
	@Test
	public void testInit(){
		//evalRankingService.initAllUnitsRanking("1367B46CF8DA4E2F9B1E617360137B79", "F26CF2526BC646DAAC616B9DB049D24C");
	}
	
	@Test
	public void testSort(){
		checkLogicRule.checkSingleField("E20130204", "TYCS", "-1");
	}
}
