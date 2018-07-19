package com.dsep.unitTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.impl.OuterExpertDaoImpl;
import com.dsep.domain.dsepmeta.expert.OuterExpert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml" })
public class TestGetExpertFromOtherDB extends OuterExpertDaoImpl {
	
	@Autowired
	private OuterExpertDao getExpertResultFromOtherDBDao;

	//@Autowired
	//private SelectionDao selectionDao;

	@Test
	public void Test_CreateBackupTable() {
		String sql = "select zjxm, xxdm, yjxkm, is_211 from xx_zj z left join dsep_base_unit u on z.xxdm = u.id order by z.yjxkm, z.xxdm";
		try {
			List<OuterExpert> experts = getExpertResultFromOtherDBDao.getAllBySql(sql);
			
			//selectionDao.convertList(experts);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
