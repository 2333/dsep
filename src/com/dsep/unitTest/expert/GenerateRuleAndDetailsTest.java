package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.expert.rule.RuleMetaService;
import com.dsep.util.expert.rule.FreeMarkerGenerateRulePageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class GenerateRuleAndDetailsTest {

	@Autowired
	private RuleMetaService ruleMetaService;
	
	
	@Test
	public void generate(){
		FreeMarkerGenerateRulePageUtil u = FreeMarkerGenerateRulePageUtil
				.getSingleInstance();
		try {
			u.generateFiles(ruleMetaService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
