package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class UserDAO {

    private static final String IB_BY_EMAIL_QUERY = "select id from User where email=:email";
    private static final String EMAIL_BY_ID_QUERY = "select email from User where id=:id";
    private static final String ALL_USER_QUERY = "From User";
    private static final String UPDATE_USER_CHANGE_PASSWORD_QUERY = "update User set password=:password where email=:email";
    private static final String UPDATE_USER_ADD_DATE_QUERY = "update User set activeTime=:date where email=:email";
    private static final String UPDATE_USER_ADD_IMAGE_QUERY = "update User set profileImagePath=:path where id=:id";
    private static final String GET_NAME_BY_EMAIL_QUERY = "select name from User where email=:email";

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addUser(User user) {

        entityManager.persist(user);
    }

    @Transactional
    public void deleteUser(User user) {

        entityManager.remove(user);
    }

    @Transactional
    public void updateUser(User user) {

        entityManager.merge(user);
    }

    @Transactional
    public User findById(String id) {

        return entityManager.find(User.class, id);
    }

    @Transactional
    public User findByEmail(String email) {
        User user = null;
        try {
            user = entityManager.
                    unwrap(Session.class).
                    byNaturalId(User.class).
                    using("email", email).
                    load();
        } catch (Exception e) {
            return user;
        }
        return user;
    }

    public String getIdByEmail(String email) {
        return (String) entityManager.
                createQuery(IB_BY_EMAIL_QUERY).
                setParameter("email", email).
                getSingleResult();
    }

    public String getEmailById(String Id) {
        return (String) entityManager.
                createQuery(EMAIL_BY_ID_QUERY).
                setParameter("id", Id).
                getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> list(int page) {
        int firstResult = ((page - 1) * 20) + 1;
        return (List<User>) entityManager.
                createQuery(ALL_USER_QUERY).
                setFirstResult(firstResult).
                setMaxResults(20).
                getResultList();

    }

    @Transactional
    public void forgotPassword(String email, String password) {
        entityManager.
                createQuery(UPDATE_USER_CHANGE_PASSWORD_QUERY).
                setParameter("password", password).
                setParameter("email", email).
                executeUpdate();

    }

    @Transactional
    public void updateTime(String email, Date date) {
        entityManager.
                createQuery(UPDATE_USER_ADD_DATE_QUERY).
                setParameter("date", date).
                setParameter("email", email).
                executeUpdate();
    }

    @Transactional
    public void updateProfileImagePath(String userId, String path) {
        entityManager.
                createQuery(UPDATE_USER_ADD_IMAGE_QUERY).
                setParameter("id", userId).
                setParameter("path", path).
                executeUpdate();

    }

    public String getNameByEmail(String fromEmail) {
        return (String) entityManager.
                createQuery(GET_NAME_BY_EMAIL_QUERY).
                setParameter("email", fromEmail).
                getSingleResult();
    }

}
