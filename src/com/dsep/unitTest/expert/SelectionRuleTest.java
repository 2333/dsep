package com.dsep.unitTest.expert;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.rule.RuleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class SelectionRuleTest {
	
	@Autowired
	private RuleService ruleService;
	
	@Test
	public void Test_RuleDetail_Delete(){
		
	}
	
	@Test
	public void Test_Cascade_Delete(){
		ExpertSelectionRule newRule = new ExpertSelectionRule();
		newRule.setId("4E3756314D2B4A469B0F80BC2818D4DF");
		ruleService.deleteRuleById("8A13AB3B29A44A5FA631AB66B08B0519");
		/*ExpertSelectionRule newRule = ruleService.getRuleById("4E3756314D2B4A469B0F80BC2818D4DF");
		System.out.println(newRule.getId()+"===============================");*/
	}
	
	@Test
	public void Test_Expert_Rule_Delete(){
		/*ruleService.TestCascadeDelete();*/
	}
	
	@Test
	public void Test_Cascade_Save(){
		ExpertSelectionRule newRule = new ExpertSelectionRule();
		newRule.setRuleName("pantest");
		ExpertSelectionRuleDetail newDetail = new ExpertSelectionRuleDetail();
		newDetail.setRule(newRule);
		newDetail.setIsNumber(true);
		newDetail.setItemName("itemname");
		newDetail.setOperator(">=");
		newDetail.setIsNecessary(true);
		List<ExpertSelectionRuleDetail> detailList = new ArrayList<ExpertSelectionRuleDetail>();
		detailList.add(newDetail);
		//ruleService.saveRuleAndDetails(newRule, detailList);
	}
	
}
