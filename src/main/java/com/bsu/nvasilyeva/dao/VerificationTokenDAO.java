package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.entity.VerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class VerificationTokenDAO {

    private static final String FIND_BY_TOKEN_QUERY = "from VerificationToken v where v.token=:token and purpose=:purpose";
    private static final String DELETE_TOKEN_QUERY = "delete from VerificationToken  where token=:token";

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addVerificationToken(VerificationToken verifivationToken) {
        entityManager.persist(verifivationToken);
    }

    @Transactional
    public VerificationToken findById(int id) {
        return entityManager.find(VerificationToken.class, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public VerificationToken findByTokenAndPurpose(String token, String purpose) {
        return (VerificationToken) entityManager.
                createQuery(FIND_BY_TOKEN_QUERY).
                setParameter("token", token).
                setParameter("purpose", purpose).
                getResultList().stream().findFirst().
                orElse(null);
    }

    @Transactional
    public void deleteToken(String token) {
        entityManager.
                createQuery(DELETE_TOKEN_QUERY).
                setParameter("token", token).
                executeUpdate();
    }

}
