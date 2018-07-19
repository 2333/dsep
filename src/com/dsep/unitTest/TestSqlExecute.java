package com.dsep.unitTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.admin.SqlHelperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class TestSqlExecute {

	@Autowired
	private SqlHelperService sqlHelperService;
	@Test
	public void test() {
		/*String sqlStr = "insert into xx_cpjs(zjbh,xm,YJXKMC,yx,xxdm,xxmc,yjxkdm) " +
				"values('test','test1','test','123@.com','10335','浙大','0835')";
		int count = sqlHelperService.executeUpdateOrSave(sqlStr);
		System.out.println("更新记录数 ： "+count);*/
		
		String sqlSel = "select * from xx_cpjs";
		List<Map<String, Object>> results=sqlHelperService.executeSelect(sqlSel);
		for(Map<String, Object> row:results){
			System.out.println(row);
		}
	
	}

}
