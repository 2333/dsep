package com.dsep.unitTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.feedback.FeedbackType;
import com.dsep.service.publicity.feedback.FeedbackImportService;
import com.dsep.service.publicity.feedback.FeedbackManagementService;
import com.dsep.service.publicity.feedback.FeedbackResponseService;
import com.dsep.service.publicity.objection.PublicityService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackResponseVM;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class FeedbackTest {
	
	@Autowired
	private PublicityService publicityService; 
	
	@Autowired
	private FeedbackManagementService feedbackManagementService;
	
	@Autowired
	private FeedbackResponseService feedbackResponseService;
	
	@Autowired
	private FeedbackImportService feedbackImportService;
	
	@Test
	public void testTransaction() throws IllegalArgumentException, IllegalAccessException{
		publicityService.autoBeginPublicityRound();
		/*publicityService.autoClosePublicityRound();*/
		feedbackManagementService.autoCloseFeedbackRound();
	}
	
	
	@Test
	public void testTree(){
		List<CollectionTreeVM> treeList = feedbackManagementService.getFeedbackTypeTree();
		for(CollectionTreeVM oneTree:treeList){
			System.out.println(oneTree.getId());
		}
		System.out.println(JsonConvertor.obj2JSON(treeList));
	}
	
	@Test
	public void test_getResponseVM() throws IllegalArgumentException, IllegalAccessException{
		FeedbackResponse response = new FeedbackResponse();
		response.setProblemUnitId("10006");
		PageVM<FeedbackResponseVM> responseVm = feedbackResponseService.getFeedbackResponseVM(0, 10, true, "", response);
		System.out.println(responseVm.getTotalCount());
	}
	
	@Test
	public void test_feedbackImport() throws IllegalArgumentException, IllegalAccessException{
		feedbackImportService.importFeedbackDataSource("ACDEA3749AFE4C7882C3E52C0B6C9584",FeedbackType.OBJECTION.getStatus());
	}
	
	@Test
	public void test_saveResponse(){
		FeedbackResponse theResponse = new FeedbackResponse();
		theResponse.setModifyTime(new Date());
		theResponse.setAdviceValue("2123");
		theResponse.setResponseAdvice("21323");
		feedbackResponseService.testSave(theResponse, "56218D9CFC8A4ED0834657B1A514F201");
	}
	
	@Test
	public void test_agreeUnitAdvice(){
		List<String> responseItemIdList = new ArrayList<String>();
		responseItemIdList.add("D8262394CA014BE78ADF113D2FE75DF7");
		feedbackResponseService.agreeUnitAdvice(responseItemIdList,"10006");
	}
	
	@Test
	public void test_saveTime(){
		FeedbackResponse response = new FeedbackResponse();
		feedbackResponseService.testSave(response, "D8262394CA014BE78ADF113D2FE75DF7");
	}
		
	@Test
	public void test_deleteFeedbackRound(){
		feedbackManagementService.deleteFeedbackRound("07F9897A56514E678BF729ED97F248FE");
	}
	
	@Test
	public void test_getSameItemTest(){
		FeedbackResponse conditionalResponse = new FeedbackResponse();
		conditionalResponse.setFeedbackRoundId("47E22F1C49574CFD9952BB309BA940E1");
		feedbackResponseService.getSameItemJqgridVM(1,10,true,null,conditionalResponse);
	}
}
