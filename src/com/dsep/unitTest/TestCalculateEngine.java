package com.dsep.unitTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class TestCalculateEngine {

//	@Autowired
//	private CommanderCalculateService commanderCalculateService;
	
	@Test
	public void testCalculateSumfactor(){        //测试SumFactor
		Map<String,String> commPackage=new HashMap<String,String>();
		commPackage.put(DataCode.COMMANDER_NAME, DataCode.COMM_SUMFACTOR);
		commPackage.put(DataCode.INDEX_ID, "JSJ000I020401");
		commPackage.put(DataCode.DISC_ID, "0835");
		commPackage.put(DataCode.UNIT_ID, "10006");
		commPackage.put(DataCode.ENTITY_ID, "E20130201");
//		commPackage.put(DataCode.TABLE_NAME,"GG_ZJTD_CJ" );
		commPackage.put(DataCode.FIELD,"HJ_NAME");
//		commPackage.put(DataCode.LIMIT, DataCode.NUMBER+"&5");
	//commPackage.put(DataCode.INDEX_CONT,"973首席科学家");
		
	//	double result=commanderCalculateService.calculate(commPackage);
		
		System.out.println("专家折算系数：");
	//	System.out.println(result);
	}
	
	@Test
	public void testCalculateCommander(){
		Map<String,String> commPackage=new HashMap<String,String>();
		commPackage.put(DataCode.COMMANDER_NAME, DataCode.COMM_COUNT);
		commPackage.put(DataCode.DISC_ID,"0835");
		commPackage.put(DataCode.UNIT_ID,"10006");
		commPackage.put(DataCode.ENTITY_ID,"E20130101");
	//	commPackage.put(DataCode.FIELD, DataCode.DISC_ID);
		commPackage.put(DataCode.CONDITION,"ZJ_TYPE='中国科学院院士' or ZJ_TYPE='千人计划入选者'" );
		
	//	double result=commanderCalculateService.calculate(commPackage);
		
		System.out.println("专家折算系数：");
	//	System.out.println(result);
	}
}
