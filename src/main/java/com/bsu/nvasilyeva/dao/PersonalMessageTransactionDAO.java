package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.LoadMessagesDTO;
import com.bsu.nvasilyeva.entity.PersonalMessageTransaction;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonalMessageTransactionDAO {

    private static final String MESSAGES_FROM_ALL_CHAT_QUERY = "select new com.bsu.nvasilyeva.dto.LoadMessagesDTO(m.id,m.message,m.path,p.createdAt,p.fromId,p.toId) from PersonalMessageTransaction p left join Message m  on p.message=m  where (p.fromId like :fromUser and p.toId like :toUser) or (p.fromId like :toUser and p.toId like :fromUser) order by p.createdAt asc";
    private static final String CHANGE_PERSONAL_MESSAGE_STATUS_QUERY = "update PersonalMessageTransaction p set p.isReaded=false where p.id=:id";
    private static final String CHANGE_MESSAGE_STATUS_AS_TRUE_QUERY = "update PersonalMessageTransaction p set isReaded=true where  (p.fromId like :fromUser and p.toId like :toUser) or (p.fromId like :toUser and p.toId like :fromUser)";
    private static final String UNREAD_MESSAGE_COUNT_QUERY = "select count(*) from PersonalMessageTransaction p  where (p.toId like :fromUser) and p.isReaded=false";


    @PersistenceContext
    EntityManager entityManager;

    public List<LoadMessagesDTO> getAllPersonalChat(User fromUser, User toUser) {
        @SuppressWarnings("unchecked")
        List<LoadMessagesDTO> messageDTOs = entityManager.
                createQuery(MESSAGES_FROM_ALL_CHAT_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                getResultList();
        return messageDTOs;

    }

    @Transactional
    public void add(PersonalMessageTransaction messageTransaction) {

        entityManager.
                persist(messageTransaction);
    }

    @Transactional
    public void changePersonalMessageStatus(int id) {
        entityManager.
                createQuery(CHANGE_PERSONAL_MESSAGE_STATUS_QUERY).
                setParameter("id", id).
                executeUpdate();
    }

    @Transactional
    public void changePersonalMessageStatusAllReaded(User fromUser, User toUser) {
        entityManager.
                createQuery(CHANGE_MESSAGE_STATUS_AS_TRUE_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                executeUpdate();

    }

    public Long getTotalUnreadMessageCount(User fromUser) {
        return (Long) entityManager.
                createQuery(UNREAD_MESSAGE_COUNT_QUERY).
                setParameter("fromUser", fromUser).
                getSingleResult();
    }
}
