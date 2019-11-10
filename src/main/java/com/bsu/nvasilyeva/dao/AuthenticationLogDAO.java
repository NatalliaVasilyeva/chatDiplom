package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.LogDTO;
import com.bsu.nvasilyeva.entity.AuthenticationLog;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class AuthenticationLogDAO {

    private static final String ALL_LOG_FIELDS_QUERY = "select new com.bsu.nvasilyeva.dto.LogDTO(a.id, u.name, u.email, a.loginTime, a.logoutTime) from AuthenticationLog a left join User u on a.user=u order by a.createdAt desc";
    private static final String UPDATE_LOG_QUERY = "update AuthenticationLog  set logoutTime=:logoutTime where id =:id";
    private static final String MAX_USER_ID_LOG = "select max(id) from AuthenticationLog where user=:user";


    @PersistenceContext
    EntityManager entityManager;

    public List<LogDTO> list() {
        @SuppressWarnings("unchecked")
        List<LogDTO> logs = entityManager.
                createQuery(ALL_LOG_FIELDS_QUERY).getResultList();
        return logs;
    }

    @Transactional
    public void add(AuthenticationLog log) {

        entityManager.persist(log);
    }

    @Transactional
    public void changeLogoutTime(User user) {
        int id = getMaxIdByUser(user);

        entityManager.
                createQuery(UPDATE_LOG_QUERY)
                .setParameter("id", id).setParameter("logoutTime", new Date()).executeUpdate();
    }

    private int getMaxIdByUser(User user) {
        return (int) entityManager.
                createQuery(MAX_USER_ID_LOG)
                .setParameter("user", user).getSingleResult();
    }
}
