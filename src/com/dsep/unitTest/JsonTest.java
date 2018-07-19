package com.dsep.unitTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.domain.dsepmeta.publicity.process.ProcessTest;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.process.production.publicity.PublicityProcess;
import com.dsep.service.publicity.process.production.publicity.UnitPublicityProcess;
import com.dsep.util.JsonConvertor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-jms.xml"})
public class JsonTest {
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private DisciplineService disciplineService;
	
	@Test
	public void print_json(){
		HashMap<String,String> theMap = new HashMap<String,String>();
		theMap.put("10006", "beihang");
		theMap.put("10231","beili");
		System.out.println(JsonConvertor.mapJSON(theMap));
	}
	
	@Test
	public void publicityProcess_test(){
		PublicityProcess testProcess = new UnitPublicityProcess("10006");
		Map<String,String> result = testProcess.changeUnit("10005",disciplineService);
		System.out.println(result);
	}
	
	@Test
	public void test_process(){
		ProcessTest pt = new ProcessTest();
		pt.testUnit();
	}
}
