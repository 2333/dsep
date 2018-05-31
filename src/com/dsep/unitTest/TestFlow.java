package com.dsep.unitTest;


import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.flow.PreFlowDao;
import com.dsep.domain.UnitDiscCollect;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.service.flow.PreFlowService;
import com.dsep.vm.EntityLogicCheckVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.CollectFlowVM;
import com.dsep.vm.flow.EvalVM;
import com.dsep.vm.flow.PreEvalVM;

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
public class TestFlow {
	@Autowired 
	private EvalFlowService evalFlowService;
	
	@Autowired
	private PreFlowService preFlowService;
	
	@Autowired
	private DMCollectService collectService;
	
	@Autowired
	private DMDiscIndexService discIndexService;
	
	@Autowired
	private DMCheckLogicRuleService checkLogicRule;
//	@Test
//	public void TestCreateTable()
//	{
//		collectionFlowService.discipline2College("10000", "10000");
//	}

	@Test
	public void tesGetEvalData(){
	 
	}
	@Test
	public void testPreEval()
	{
		preFlowService.importDiscsFromBase("10003", "xxx");
		/*PageVM<PreEvalVM> pageVM= preFlowService.getCollectPreByPage("10006", null, 1, 10, true, "id");*/
	}
	
	@Test
	public void testImportToLogic()
	{
		evalFlowService.unit2Center("10006");
	}
	@Test
	public void testGetTableNames()
	{
		discIndexService.getCollectTableNameByDiscId("0835");
	}
	@Test
	public void testGetCategory(){
		String cat = discIndexService.getCategotyByDiscId("0835");
		System.out.println(cat);
	}
	@Test
	public void testCheckLogicRule(){
		EntityLogicCheckVM vm= checkLogicRule.entityLogicCheck("10006", "0835");
		System.out.println(vm);
	}
	@Test 
	public void testIndexMaps(){
		/*discIndexService.getIndexMaps("0835");*/
	}
	@Test
	public void testGetPreEvalByUnitId(){
		List<PreEval> pres = preFlowService.getPreEvalsByUnitIds("10006");
		System.out.println(pres);
	}
}
