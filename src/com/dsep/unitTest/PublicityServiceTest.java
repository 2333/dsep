package com.dsep.unitTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.domain.dsepmeta.publicity.PublicityMessage;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.publicity.PublicityStatus;
import com.dsep.service.publicity.feedback.FeedbackManagementService;
import com.dsep.service.publicity.objection.PublicityService;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UnitTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class PublicityServiceTest {
	
	@Autowired
	private PublicityService publicityService;
	
	@Autowired
	private FeedbackManagementService feedbackManagementService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void publicityFinish_test(){
		try {
			publicityService.publicityFinish();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPublicityMesssage_test(){
		PublicityMessage theMessage = publicityService.getPublicityMessage("0");
		String jsonData = JsonConvertor.obj2JSON(theMessage);
		UnitTest.testPrint(jsonData);
	}
	
	@Test
	public void jsonChange_test(){
		String id = "C59E6F8DC1E6463AAD93220FAFF482D7";
		PublicityMessage pubMessage = publicityService.getPublicityMessage(id);
		String jsonData = JsonConvertor.obj2JSON(pubMessage);
		UnitTest.testPrint(jsonData);
	}
	
	@Test
	public void autoClose_test() throws IllegalArgumentException, IllegalAccessException{
		publicityService.autoClosePublicityRound();
		publicityService.autoBeginPublicityRound();
		feedbackManagementService.autoCloseFeedbackRound();
	}

}
