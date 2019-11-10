package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.NotificationDTO;
import com.bsu.nvasilyeva.entity.Notification;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class NotificationDAO {

    private static final String UNREAD_NOTIFICATION_COUNT_QUERY = "select count(*) from Notification where user=:user and isReaded=false";
    private static final String CHANGE_NOTIFICATION_QUERY = "update Notification set isReaded=:isReaded where user=:user";
    private static final String ALL_NOTIFICATION_QUERY = "select new com.bsu.nvasilyeva.dto.NotificationDTO(u.id,u.name,n.message,n.createdAt,n.isReaded,n.purpose) From User u left join Notification n on  u.id=n.user  where n.user=:user  order by n.createdAt desc";

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void add(Notification notification) {

        entityManager.
                persist(notification);
    }

    public Long getTotalUnreadNotificationCount(User user) {
        return (Long) entityManager.
                createQuery(UNREAD_NOTIFICATION_COUNT_QUERY).
                setParameter("user", user).
                getSingleResult();
    }

    @Transactional
    public void changeNotificationStatusAllReaded(User user, boolean isReaded) {
        entityManager.
                createQuery(CHANGE_NOTIFICATION_QUERY).
                setParameter("user", user).
                setParameter("isReaded", isReaded).
                executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<NotificationDTO> getAllNotifications(User user) {
        return (List<NotificationDTO>) entityManager.
                createQuery(ALL_NOTIFICATION_QUERY).
                setParameter("user", user).
                setMaxResults(5).
                getResultList();
    }
}
