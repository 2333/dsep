package com.dsep.unitTest.expert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.vm.PageVM;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class TestJqgridTest {
	
	/*@Autowired
	private TestJqgridService testJqgridService;
	
	@Test
	public void Test_saveRow(){
		TestJqgrid theTest = new TestJqgrid();
		theTest.setUserId("4");
		theTest.setUserName("mahuateng");
		theTest.setDepartment("beihanguniversity");
		theTest.setPassword("testpassword");
		testJqgridService.saveRowData(theTest);
	}*/
	
	/*@Test
	public void Test_getJqgrid(){
	   PageVM<TestJqgrid> vmList = testJqgridService.getAllTestJq(1, 10, true, "userName");
	   System.out.println("----------"+vmList.getTotalPage()+"-------");
	   System.out.println("----------"+vmList.getPageIndex()+"--------");
	   System.out.println("----------"+vmList.getTotalCount()+"-------");
	}*/
}
