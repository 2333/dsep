package com.dsep.unitTest;

import java.text.ParseException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.base.DiscCategoryService;
import com.dsep.service.publicity.prepublic.PrePublicityService;
import com.dsep.util.UnitTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class PrePublicityTest {
	
	@Autowired
	private DiscCategoryService discCategoryService;
	
	@Autowired
	private PrePublicityService prePublicityService;
	
	@Test
	public void getCategoryMap_Test(){
		Map<String,String> theMap = discCategoryService.getAllCategoryMap();
		for(Map.Entry<String, String> entry:theMap.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}
	
	@Test
	public void deletePublicityRound_test(){
		prePublicityService.deletePublicityRound("5AF1FD39906F406383075A270E9E48D1");
	}
	
	@Test
	public void beginNewPrePublicityRound_test(){
		try {
			prePublicityService.openNewPublicityRound();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCurrentStatus_test(){
		try {
			String currentStatus = prePublicityService.getPublicityRoundStatus();
			UnitTest.testPrint(currentStatus);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void backupPrePublicityData_test(){
		/*prePublicityService.backupPrePublicityData();*/
	}
	
	@Test
	public void deleteBackupData_test(){
		prePublicityService.deleteBackData("E20130201", 
				"DBC34A90B76B44A5BC55CE7EB4B3F48A", "1", 
				"center_public_20140627_160541", "10006", "0835");
	}
	
	@Test
	public void beginPublicity_test(){
		try {
			if(prePublicityService.setPublicity("测试一下", "2014-08-23", null,null)){
				UnitTest.testPrint("测试成功");
			}
			else
			{
				UnitTest.testPrint("测试失败");
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
