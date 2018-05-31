package com.meta.unittest;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class AbstractTestCase {
	private SessionFactory sessionFactory;

	private Session session;

	protected FileSystemXmlApplicationContext dsContext;

	/*private String[] configStr = { "/WebContent/WEB-INF/config/spring-common.xml",
			"/WebContent/WEB-INF/config/spring-dao.xml",
			"/WebContent/WEB-INF/config/spring-myservice.xml",
			"/WebContent/WEB-INF/config/spring-util.xml"
	};*/
	private String[] configStr = { "/WebContent/WEB-INF/config/spring-common.xml",
			"/WebContent/WEB-INF/config/spring-dao.xml",
			"/WebContent/WEB-INF/config/spring-myservice.xml",
			"/WebContent/WEB-INF/config/spring-util.xml",
			"/WebContent/WEB-INF/config/spring-logger.xml",
			"/WebContent/WEB-INF/config/spring-transaction-annotation.xml"
	};
	@Before
	public void openSession() throws Exception {
		dsContext = new FileSystemXmlApplicationContext(configStr);
		sessionFactory = (SessionFactory) dsContext.getBean("sessionFactory");
		session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.MANUAL);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
	}

	@After
	public void closeSession() throws Exception {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		session.close();
	}
}
