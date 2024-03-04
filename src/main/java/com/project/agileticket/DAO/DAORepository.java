package com.project.agileticket.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.project.agileticket.Util.HibernateUtil;

public class DAORepository {
	SessionFactory sf;
	Session session;
	Transaction tx;

	public DAORepository() {
		sf = HibernateUtil.getSessionFactory();
		session = sf.openSession();
		tx = null;
	}

	public void begin() {
		
		tx = session.beginTransaction();
		
	}

	public Session getSession() {
		return this.session;
	}

	protected void commit() {
		tx.commit();
	}

	public void close() {
		session.close();
	}
}
