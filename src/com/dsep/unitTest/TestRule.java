package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.vm.EntityLogicCheckVM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml","file:WebContent/WEB-INF/config/IKAnalyzer.cfg.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",})
public class TestRule {
	@Autowired
	private DMCheckLogicRuleService dmCheckLogicRule;
	
	@Test
	public void testEntityRule(){
		EntityLogicCheckVM r =this.dmCheckLogicRule.entityLogicCheck("10006", "0835");
		@SuppressWarnings("unused")
		String r1="";
		r1="1";
	}
}
