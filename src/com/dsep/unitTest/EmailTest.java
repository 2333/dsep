package com.dsep.unitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.catalina.connector.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.email.EmailSendService;
import com.dsep.util.TextProcess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class EmailTest {

	@Autowired
	private EmailSendService emailSendService;
	
	@Test
	public void PathTest(){
		File directory = new File("");// 参数为空
        String path;
		try {
			path = directory.getCanonicalPath();
			path += "/WebContent/EmailModule/expert.jsp";
			System.out.println("++++++++++++++++++++++");
			System.out.println(path);
			System.out.println("++++++++++++++++++++++");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void TheTest() {
		boolean result;
		try {
			List<String> toAddress = new ArrayList<String>();
			for(int i=0;i < 4; i++){
				toAddress.add("408488316@qq.com");
			}
			String content = TextProcess.getContentByRelativePath("/WebContent/EmailModule/expert.jsp");
			/*toAddress.add("408488316@qq.com");
			toAddress.add("1063490741@qq.com");
			toAddress.add("1971193518@qq.com");*/
			result = emailSendService.sendMail(toAddress,
					"Test By Panlinjie",content);
			assertEquals(result,true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	

}



