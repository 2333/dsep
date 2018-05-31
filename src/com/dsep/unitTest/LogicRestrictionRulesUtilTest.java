package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common2014.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/Web-INF/config/spring-quartz.xml",
		"file:WebContent/WEB-INF/config/2014/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"})
public class LogicRestrictionRulesUtilTest {
	@Test
	public void hqlTest() {
	}
}
