package com.dsep.unitTest;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.service.datacalculate.FactorAndWeight.IndexFactorService;
import com.dsep.service.datacalculate.FactorAndWeight.IndexWeightService;
import com.dsep.service.datacalculate.RuleEngine.ObjectCalculateService;
import com.dsep.service.datacalculate.RuleEngine.SubjectCalculateService;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.service.dsepmeta.dsepmetas.DMExportService;
import com.dsep.service.dsepmeta.dsepmetas.impl.DMCheckLogicRuleServiceImpl;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.email.ExpertEmailRegisterValidationService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/Web-INF/config/spring-quartz.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"})
public class CalculateTest {
	/*@Autowired
	private EvalResultDao evalResultDao;
	@Autowired
	private SubjectCalculateService subjectCalculateService;
	@Autowired
	private DiscCategoryDao discCategoryDao;*/
	@Autowired
	private IndexFactorService indexFactorService; 
	@Autowired
	private IndexWeightService indexWeightService;
	@Autowired
	private ObjectCalculateService objectCalculateService;
	@Autowired
	private OuterExpertDao getExpertResultFromOtherDBDao;
	/*@Autowired
	private SqlBuilder sqlBuilder;
	@Autowired
	private SqlExecutor sqlExecutor;
	@Autowired
	private MetaEntityService metaEntityService;
	@Autowired
	private DMExportService dMExportService;
	@Autowired
	private EvalBatchService evalBatchService;
	@Autowired
	private EvalService evalService;
	@Autowired
	private DMCheckLogicRuleService checkLogicRule;
	@Autowired
	private DataCalculateService dataCalculateService;*/
	
	@Autowired
	private ExpertEmailRegisterValidationService expertEmailRegisterValidationService;
	/*@Autowired
	private DMCheckLogicRuleService checkLogicRule;*/
	@Test
	public void testtest() {
		
		//checkLogicRule.entityLogicCheck("10006", "152100");
	}
	
	public static Logger logger1 = Logger.getLogger("selectLog");
	@Test
	public void hqlTest(){
		System.out.println("he");
		//EvalBatch b = evalBatchService.getEvalBatchById("5F0564BDEAD345419D09435CD7D13D1E");
		//evalService.solidifyPapers(b);
		/*try {
			expertEmailRegisterValidationService.massSendingInvitationEmailsAndSolidifyPapersAndQuestions("A19CE767BF4343F182351D0FA9DB109E", "/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			indexFactorService.calculateIndicWtByDisc("0835");
			//indexFactorService.calculateAwardWtByDisc("0835");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			objectCalculateService.calculate("0835", "JSJ000I010101", "10006");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			objectCalculateService.calculate("#SUMFACTOR(E:E20130101;F:ZJ_TYPE;)", "JSJ0I010101", "10006", "0835");
		} catch (Exception e) {
			//throw new Exception("表达式计算错误");
		}*/
		int count = 0;
		List<OuterExpert> data;
		try {
			data = getExpertResultFromOtherDBDao.getAllByCondition();
			/*for(ExpertInfoFromOtherDB e:data){
				if(!"0".equals(e.getYJXKM2()))
					System.out.println(e.getYJXKM2());
			}*/
			Map<String,Map<String,List<OuterExpert>>> tree = OuterExpertsToTreeConvertorValve.convertToMakeUpTree(data);
			for(String id:tree.keySet()){
				logger1.info("学科：" + id);
				logger1.info("该学科参评的学校有：");
				logger1.info("-------------------");
				for(String id2:tree.get(id).keySet()){
					logger1.info("学校：" + id2);
					for(OuterExpert e:tree.get(id).get(id2)){
						logger1.info(e.getZJBH() + e.getZJXM() + "||次级学科：" + e.getYJXKM2());
						count ++;
					}
				}
				logger1.info("===================");
			}
			logger1.info("专家总数：" + count);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
