package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.project.ApplyItemService;
import com.dsep.service.project.SchoolProjectService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:./WebContent/WEB-INF/config/spring-common.xml",
		"file:./WebContent/WEB-INF/config/spring-dao.xml",
		"file:./WebContent/WEB-INF/config/spring-myservice.xml",
		"file:./WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:./WebContent/WEB-INF/config/2013/util/utils.xml"})
public class TestSchoolProject {

	@Autowired
	private SchoolProjectService schoolProjectService;
	
	@Autowired
	private ApplyItemService applyItemService;
	
	@Test
	public void test(){
		applyItemService.delete("");;
	}
	
}
