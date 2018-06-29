package com.meta.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.meta.service.MetaEntityService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml"})

public class LogicCheckTest {

	@Autowired
	private DMCheckLogicRuleService checkLogicRuleService;

	
//	@Autowired
//	private DMCollectService collectService;
//	@Autowired
//	private LoggerTool loggerTool;
	
	@Test
	public void testAttrCheck(){
		
		checkLogicRuleService.logicCheck("10006", "0835", "AAF1E126B9924B468049FBC4DBA935ED");
		checkLogicRuleService.logicCheck("10006", "0835", "AAF1E126B9924B468049FBC4DBA935ED");
		checkLogicRuleService.logicCheck("10006", "0835", "AAF1E126B9924B468049FBC4DBA935ED");
	
	}
	
	
	@Test
	public void testEntityCheck() {
		checkLogicRuleService.logicCheck("10006", "0835", null);
	}
}
