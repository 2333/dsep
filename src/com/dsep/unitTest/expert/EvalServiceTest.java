package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.expert.select.SelectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class EvalServiceTest {
	
	@Autowired
	private EvalService evalService;
	
	@Autowired
	private SelectService selectService;
	
	
	@Test
	public void testInitProgress() {
		//this.evalProgressService.initExpertEvalProgress("7CE28532A057423EA628B64219567703", "0835");
	}
	
	@Test
	public void test() {
		
	}
}
