package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.entity.GroupMessageTransactionDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class GroupMessageTransactionDetailDAO {

    private static final String CHANGE_MESSAGES_STATUS_QUERY = "update GroupMessageTransactionDetail gd set gd.isReaded=false where gd.id=:id";


    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void add(GroupMessageTransactionDetail groupMessageTransactionDetail) {
        entityManager.
                persist(groupMessageTransactionDetail);
    }

    @Transactional
    public void changeMessageStatusNotReaded(int id) {
        entityManager.
                createQuery(CHANGE_MESSAGES_STATUS_QUERY).
                setParameter("id", id).
                executeUpdate();
    }

}
