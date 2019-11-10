package com.bsu.nvasilyeva.service;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomGroupIdGenrator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		int size = session.createNativeQuery("select * from ggroup").list().size();
		return "GROUP" + (size + 1);
	}

}
