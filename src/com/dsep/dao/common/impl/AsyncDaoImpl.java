package com.dsep.dao.common.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dsep.dao.common.AsyncDao;
import com.dsep.util.StringProcess;

public class AsyncDaoImpl<T,PK extends Serializable> extends DaoImpl<T,PK> 
	implements AsyncDao<T, PK>{
	
	private SessionFactory sessionFactory;
	private static Session session = null;
	
	@Override
	protected  Session getSession() {
		session = this.sessionFactory.openSession();
		return session;
	}
	
}
