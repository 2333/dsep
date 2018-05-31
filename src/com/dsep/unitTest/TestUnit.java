package com.dsep.unitTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




import com.dsep.service.base.UnitService;
import com.dsep.util.Configurations;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/2014/util/utils.xml"})
public class TestUnit {

	@Autowired
	private UnitService unitService;
	
	@Test
	public void Test_getJoinUnitByDiscId(){
	   List<String> unitList = unitService.getEvalUnitByDiscId("0826");
	   for(int i=0;i < unitList.size();i++){
		   System.out.println("-----------------------------------------");
		   System.out.println(unitList.get(i));
	   }
	   List<String> evalUnitId = unitService.getAllEvalUnitIds();
	}
	@Test
	public void TestConfiguration(){
		System.out.println(Configurations.getCurrentDomainId());
	}
}
