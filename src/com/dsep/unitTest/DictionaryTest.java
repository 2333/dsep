package com.dsep.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class DictionaryTest {

	@Test
	public void test() {
		String one = Dictionaries.getUnitName("10006");
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("-------------"+one+"-------------------");
		System.out.println("----------------------------------");
		System.out.println("");
		
		String two = Dictionaries.getUnitName("10001");
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("-------------"+two+"-------------------");
		System.out.println("----------------------------------");
		System.out.println("");
		
	}
	
	@Test
	public void test_uploadFile(){
		
	}
	
	@Test
	public void testSearchObject(){
		String formerFileName = "sadfadfas.zip";
		String userString = "20132";
		String formerFileSimpleName = "";
		String[] fileArray = formerFileName.split("\\.");
		String suffix = fileArray[fileArray.length-1];
		for(int i=0;i < fileArray.length-1;i++){
			formerFileSimpleName += fileArray[i];
		}
		String newFileName = formerFileSimpleName + "_" + userString + "."+suffix;
		System.out.println(newFileName);
	}

}
