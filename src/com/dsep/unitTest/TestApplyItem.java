package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.expert.select.SelectService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml"})
public class TestApplyItem {

	@Autowired
	private SelectService selectExpertService;
	
	/*@Autowired
	private ExpertService expertService;*/
	
	
	@Test
	public void Test_Insert_Expert(){
		/*selectExpertService.addSelectedExpert(expert);*/
	}
	
	@Test
	public void Test_Select_Expert(){
		/*try {
			selectExpertService.select("8BF9FD2995664CFBA9131DA937927FAD");
			selectExpertService.select("C2318D5F26604EC2AF1847FD35DC2E7B");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void Test_Delete_All(){
	}
	
	/*@Test
	public void Test_Delete(){
		selectExpertService.Test();
	}*/
	
	
	@Test
	/**
	 * 删除专家
	 */
	public void Test_Delete_Expert(){
		/*expertService.test_Delete_Expert();*/
	}
	
	@Test
	public void Test_Delete_ExpertAndUser(){
	}
	
	/*@Test
	*//**
	 * 将遴选的专家插入到用户表和已选专家表中，事务操作
	 * Controller中的操作可参照此方法
	 *//*
	public void Test_Insert_User_And_Expert(){
		List<Expert> expertList = new ArrayList<Expert>();
		Expert expert1 = new Expert();
		Expert expert2 = new Expert();
		Expert expert3 = new Expert();
		String theNumber = "100004";
		expert1.setZjbh(theNumber);
		expert2.setZjbh(theNumber);
		expert3.setZjbh("100005");
		expert1.setMm(theNumber);
		expert2.setMm(theNumber);
		expert3.setMm("100005");
		expertList.add(expert1);
		expertList.add(expert2);
		expertList.add(expert3);
		List<Expert> wrongExpertList = new ArrayList<Expert>(); 
		for(int i=0;i < expertList.size();i++){
			try{
				selectExpertService.addSelectedExpert(expertList.get(i));
			}catch(Exception e){
				wrongExpertList.add(expertList.get(i));
				continue;
			}
		}
		int totalNumber = expertList.size();
		int wrongNumber = wrongExpertList.size();
		System.out.println("成功插入"+(totalNumber-wrongNumber)+"条,失败"+wrongNumber+"条，列表如下");
		for(int i=0;i < wrongExpertList.size();i++){
			System.out.println("编号:"+wrongExpertList.get(i).getBgdh());
			System.out.println("密码:"+wrongExpertList.get(i).getMm());
		}
	}*/
	
}
