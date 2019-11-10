package com.bsu.nvasilyeva.service;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomUserIdGenrator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		int size = session.createNativeQuery("select * from user").list().size();
		return "USER" + (size + 1);
	}

}
