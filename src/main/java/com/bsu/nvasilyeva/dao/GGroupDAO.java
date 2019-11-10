package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.entity.GGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class GGroupDAO {

    private static final String PROFILE_IMAGE_QUERY = "select profileImagePath from GGroup where id=:groupId";

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addGroup(GGroup group) {

        entityManager.persist(group);
    }

    @Transactional
    public void updateGroup(GGroup group) {

        entityManager.merge(group);
    }

    public String getProfileImageById(String groupId) {
        String profileImagePath = (String) entityManager.
                createQuery(PROFILE_IMAGE_QUERY).
                setParameter("groupId", groupId).
                getSingleResult();
        return profileImagePath;
    }
}
