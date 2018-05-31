package com.dsep.unitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.calculate.CalResultDao;
import com.dsep.dao.dsepmeta.calculate.DataCalculateDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.CalIndexScore;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DataCalculateConfig;
import com.dsep.service.datacalculate.DataCalculateService;
import com.dsep.service.dsepmeta.dsepmetas.DMDataCalculateService;
import com.dsep.util.GUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml"})
public class TestCalculateService {
	@Autowired
	private DataCalculateService dataCalculateService;
	@Autowired
	private DMDataCalculateService dmDataCalculateService;
	
	@Test
	public void testStartDataCalculate() throws Exception{
		User user=new User();
		List<String> discList=new LinkedList<String>();
		discList.add("0835");
		
		int t1= (int) System.currentTimeMillis();
		dmDataCalculateService.calLastIndex(discList);
		int t2= (int) System.currentTimeMillis();
		dmDataCalculateService.convertHundredMark(discList);
		int t3= (int) System.currentTimeMillis();
		dmDataCalculateService.calTotalScore(discList);
		int t4= (int) System.currentTimeMillis();
		dmDataCalculateService.calIndexScore(discList);
		int t5= (int) System.currentTimeMillis();
		dmDataCalculateService.deductScore(discList);
		int t6= (int) System.currentTimeMillis();
		dmDataCalculateService.cluster(discList,2);
		int t7= (int) System.currentTimeMillis();
		dmDataCalculateService.sortUnits(discList);
		int t8= (int) System.currentTimeMillis();
		System.out.println("末级指标计算时间："+(t2-t1));
		System.out.println("百分制转换计算时间："+(t3-t2));
		System.out.println("总分计算时间："+(t4-t3));
		System.out.println("一二级指标计算时间："+(t5-t4));
		System.out.println("扣分计算时间："+(t6-t5));
		System.out.println("聚类指标计算时间："+(t7-t6));
		System.out.println("排序计算时间："+(t8-t7));
		System.out.println("总计算时间："+(t7-t1));
	}
	
	@Test
	public void testCluster(){
		List<String> discIds=new ArrayList<String>();
		discIds.add("0835");
		//System.out.println("JVM MAX MEMORY:"+Runtime.getRuntime().maxMemory());
		dmDataCalculateService.cluster( discIds, 10);
	}
	public static void main(String[]args){
		String str1=null;
		String str2="";
		System.out.println(StringUtils.isBlank(str1));
		System.out.println(StringUtils.isBlank(str2));
	}
}
