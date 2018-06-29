package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"})
public class TestDiscAndUnit {
	
	@Autowired
	private DisciplineService disciplineService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private LoggerTool loggerTool;
	
	@Test
	public void disc_test(){
		disciplineService.getJoinDisciplineByUnitId("10006");
	}
	
	@Test
	public void test_unitCache(){
		unitService.getAllUnitMaps();
		loggerTool.info("success1");
		unitService.getAllUnitMaps();
		loggerTool.info("success2");
//		unitService.testCache();
		loggerTool.info("success3");
		unitService.getAllUnitMaps();
		loggerTool.info("success4");
	}
}
