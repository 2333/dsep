package com.dsep.service.expert.select.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.service.expert.select.SelectService;
import com.dsep.util.GUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml" })
public class UnitDivisionSelectionStrategyServiceImplTest {

	@Autowired
	private SelectService selectService;
	@Autowired
	private ExpertCRUDService expertCRUDService;//.addExpert(expert);

	@Autowired
	private EvalBatchService evalBatchService;

	@Test
	public void test() {
		Expert e = new Expert();
		e.setId(GUID.get());
		EvalBatch b = evalBatchService
				.getEvalBatchById("67A279D035E440C9B2BE92392656F6C5");
		b.getExperts().add(e);
		e.setEvalBatch(b);
		expertCRUDService.addExpert(e);
	}

}
