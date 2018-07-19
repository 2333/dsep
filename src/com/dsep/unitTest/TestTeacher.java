package com.dsep.unitTest;

import javax.xml.stream.events.EndDocument;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.rbac.TeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml" })
public class TestTeacher {
	@Autowired
	private TeacherService teacherService;
	@Test
	public void testImportTeacherFromExpert(){
		long startTime =  System.currentTimeMillis();
		teacherService.import2TeacherFromOtherExpertDB();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	@Test
	public void testImportTeacherByPage(){
		long startTime = System.currentTimeMillis();
		teacherService.import2TeacherFromOtherExpertDBByPage(50);
		long endTime = System.currentTimeMillis();
		System.out.println("时间："+(endTime-startTime));
	}
}
