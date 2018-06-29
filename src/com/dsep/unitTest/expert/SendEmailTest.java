package com.dsep.unitTest.expert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.email.EmailSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class SendEmailTest {
	@Autowired
	private EmailSendService emailSendService;
	
	@Test
	public void testGetEmailContentFromFolder() {
		File f = new File("src/com/dsep/util/expert/email/outputpage/user.html");
		try {
			StringBuffer sb = new StringBuffer();
			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[2014];
			int length = -1;
			try {
				while (-1 != (length = in.read(buffer, 0, buffer.length))) {
					sb.append(new String(buffer, 0, length));
				}
				System.out.println(sb.toString());
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
