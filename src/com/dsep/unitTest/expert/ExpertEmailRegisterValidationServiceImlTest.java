package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.email.ExpertEmailRegisterValidationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class ExpertEmailRegisterValidationServiceImlTest {

	@Autowired
	private ExpertEmailRegisterValidationService expertEmailRegisterValidationService;
	
	@Test
	public void PathTest(){
		Expert expert = new Expert();
		expert.setId("2C8B60D517024DB5B26DB3B78CD08FDC");
		expert.setExpertEmail1("dsep001@163.com");
		expert.setValidateCode1("499f9b83a12974f29e77062ce640da10");
		expert.setExpertName("刘老师");
		expert.setDiscId("软件工程");
		/*try {
			expertEmailRegisterValidationService.massSendingInvitationEmailsAndSolidifyPapersAndQuestions("C:/develop_kit/eclipse/projects/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/DSEP/");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	
	
	
	

}



