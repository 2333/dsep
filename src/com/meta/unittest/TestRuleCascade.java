package com.meta.unittest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.rule.RuleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml"})
public class TestRuleCascade {
	@Autowired
	private RuleService ruleService;
	
	@Before
	public void setUp(){
	}

	@Test
	public void cascadeSave(){
		ExpertSelectionRule rule = new ExpertSelectionRule();
		ExpertSelectionRuleDetail detail = new ExpertSelectionRuleDetail();
		
		rule.setRuleName("unit_test");
		detail.setIsNecessary(true);
		detail.setIsNumber(true);
		detail.setItemName("test");
		detail.setOperator("between");
		detail.setRestrict1("10");
		detail.setRestrict2("20");
		detail.setRule(rule);
		
		
		
		ExpertSelectionRuleDetail detail2 = new ExpertSelectionRuleDetail();
		
		detail2.setIsNecessary(true);
		detail2.setIsNumber(true);
		detail2.setItemName("test");
		detail2.setOperator("between");
		detail2.setRestrict1("10");
		detail2.setRestrict2("20");
		detail2.setRule(rule);
		List<ExpertSelectionRuleDetail> details = new ArrayList<ExpertSelectionRuleDetail>();
		details.add(detail);
		details.add(detail2);
		
		
		rule.setExpertSelectionRuleDetails(details);
		
		
		//ruleService.saveRuleAndDetails(rule, details);
	}
	
}
