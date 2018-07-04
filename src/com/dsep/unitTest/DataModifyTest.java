package com.dsep.unitTest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.service.datamodify.DataModifyService;
import com.dsep.util.GUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class DataModifyTest {

	@Autowired
	private DataModifyService dataModifyService;
	
	@Test
	public void test_save(){
		DataModifyHistory history = new DataModifyHistory();
		history.setId(GUID.get());
		history.setModifyTime(new Date());
		dataModifyService.changeEntityData(history);
	}
	
}
