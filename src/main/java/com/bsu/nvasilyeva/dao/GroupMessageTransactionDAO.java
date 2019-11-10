package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.LoadMessagesDTO;
import com.bsu.nvasilyeva.entity.GGroup;
import com.bsu.nvasilyeva.entity.GroupMessageTransaction;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GroupMessageTransactionDAO {

    private static final String ALL_GROUP_CHAT_QUERY = "select new com.bsu.nvasilyeva.dto.LoadMessagesDTO(m.id,m.message,m.path,g.createdAt,g.fromId,g.groupId,u.email) from GroupMessageTransaction g left join User u on g.fromId=u left join Message m  on g.message=m  where g.groupId like :group order by g.createdAt asc";
    private static final String CHANGE_ALL_MESSAGES_STATUS_QUERY = "update GroupMessageTransactionDetail gd set isReaded=true where gd.userId=:userId and gd.groupMessageTransaction in (select g.id from GroupMessageTransaction g where g.groupId like :group)";
    private static final String ALL_UNREAD_MESSAGES_COUNT_QUERY = "select count(*) from GroupMessageTransactionDetail gd  where (gd.userId like :fromUser) and gd.isReaded=false";


    @PersistenceContext
    EntityManager entityManager;

    public List<LoadMessagesDTO> getAllGroupChat(User fromUser, GGroup group) {
        @SuppressWarnings("unchecked")
        List<LoadMessagesDTO> messageDTOs = entityManager.
                createQuery(ALL_GROUP_CHAT_QUERY).
                setParameter("group", group).
                getResultList();
        return messageDTOs;
    }

    @Transactional
    public void add(GroupMessageTransaction groupMessageTransaction) {

        entityManager.
                persist(groupMessageTransaction);
    }

    @Transactional
    public void changeAllGroupMessageStatusAllReaded(GGroup group, User user) {
        entityManager.
                createQuery(CHANGE_ALL_MESSAGES_STATUS_QUERY).
                setParameter("userId", user).
                setParameter("group", group).
                executeUpdate();
    }

    public Long getTotalUnreadMessageCount(User fromUser) {
        return (Long) entityManager.
                createQuery(ALL_UNREAD_MESSAGES_COUNT_QUERY).
                setParameter("fromUser", fromUser).
                getSingleResult();
    }

}
