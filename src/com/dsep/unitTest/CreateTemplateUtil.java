package com.dsep.unitTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.file.ImportService;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common2014.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/Web-INF/config/spring-quartz.xml",
		"file:WebContent/WEB-INF/config/2014/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"})
public class CreateTemplateUtil {
	
	@Autowired 
	private ImportService importService;
	
	@Autowired
	private MetaEntityService metaEntityService;
	@Test
	public void createTemplateTest(){
		List<MetaEntity> entities = metaEntityService.getEntities("D201401","");
		for(MetaEntity entity:entities){
			String entityId = entity.getId();
			String entityName = entity.getName();
			if(entityName.startsWith("DSEP_C")){
				importService.createExcelTmpByEntityId(entityId,"C", "D:/DSEP_TEMPLATE",null);
			}
		}
		
/*
		String entityId = entities.get(0).getId();
		importService.createExcelTmpByEntityId(entityId, "C","D:/DSEP_TEMPLATEexcel",null);
		*/
	}
	@Test
	public void createTemplateTestById(){
		String [] entities = new String []{"E20140301"};
		for(String entityId: entities){
			//MetaEntity entity = metaEntityService.getById(entityId);
			importService.createExcelTmpByEntityId(entityId,"C", "D:/DSEP_ZY_TEMPLATE",null);
			
		}
		
	}
}
