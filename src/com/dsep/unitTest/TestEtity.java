package com.dsep.unitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml"})
public class TestEtity {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("1");
	}

	
	@Test
	public void test_backupVersion(){
		String versionId = "10006_0835_20140520_153002";
		versionId = "N"+versionId;
		System.out.println(versionId);
		versionId = versionId.substring(1,versionId.length()-1);
		System.out.println(versionId);
	}
}
