package com.bsu.nvasilyeva.service;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import javax.transaction.Transactional;
import java.io.Serializable;

public class CustomUserIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int size = session.createNativeQuery("from User").list().size();
        return "USER" + (size + 1);
    }

}
