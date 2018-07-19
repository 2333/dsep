package com.meta.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.common.logger.LoggerTool;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.meta.service.MetaDicService;
import com.meta.service.MetaDomainService;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml"})
public class DSEPMETATest {
	@Autowired
	private MetaDomainService metaDomainService;
	@Autowired
	private MetaEntityService metaEntityService;
	@Autowired
	private MetaDicService metaDicService;
	@Autowired
	private SqlBuilder sqlBuilder;
	@Autowired
	private SqlExecutor sqlExecutor;
	@Autowired
	private LoggerTool loggerTool;
	
}
