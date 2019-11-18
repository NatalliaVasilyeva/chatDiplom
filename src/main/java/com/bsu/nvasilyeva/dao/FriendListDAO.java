package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.ApplicationUserDTO;
import com.bsu.nvasilyeva.dto.ChatPeopleDTO;
import com.bsu.nvasilyeva.entity.FriendList;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class FriendListDAO {

    private static final String REQUESTABLE_USER_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath) from User u where u.id not like :userId and (((select count(id) from FriendList where u.id like fromId.id and :userId like toId)<1)and(select count(id) from FriendList where u.id=toId and :userId like fromId)<1))";
    private static final String FRIEND_PENDING_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.toId where f.fromId like :fromId and enable=false";
    private static final String FRIEND_REQUEST_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.fromId where f.toId like :fromId and enable=false";
    private static final String UPDATE_FRIEND_LIST_QUERY = "update FriendList set enable=true where fromId=:fromUser and toId=:toUser";
    private static final String ALL_FRIEND_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.fromId or u.id=f.toId where (f.fromId like :user or f.toId like :user) and enable=true and  u.id!=:userId";
    private static final String DELETE_FROM_FRIEND_LIST_QUERY = "delete from FriendList  where (fromId=:fromUser and toId=:toUser) or (fromId=:toUser and toId=:fromUser)";
    private static final String DELETE_FROM_PERSONAL_MESSAGE_QUERY = "delete from PersonalMessageTransaction  where (fromId=:fromUser and toId=:toUser) or (fromId=:toUser and toId=:fromUser)";
    private static final String CHAT_FRIEND_LIST_QUERY = "select new com.bsu.nvasilyeva.dto.ChatPeopleDTO(u.id,u.name,u.email,u.profileImagePath,u.activeTime) from User u left join FriendList f on u.id=f.fromId or u.id=f.toId  where (f.fromId like :user or f.toId like :user) and enable=true and  u.id!=:userId order by u.activeTime desc";
    private static final String CHAT_MESSAGES_QUERY = "select count(*) From PersonalMessageTransaction p where p.fromId=:fromUser and p.toId=:toUser and p.isReaded=false";
    private static final String IS_HAS_FRIEND_QUERY = "select count(*) from FriendList f where (f.fromId=:toUser and f.toId=:fromUser) or (f.fromId=:fromUser and f.toId=:toUser)";


    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addRequest(User fromUser, User toUser) {
        FriendList friend = new FriendList();
        friend.setFromId(fromUser);
        friend.setToId(toUser);
        entityManager.persist(friend);
    }

    public List<ApplicationUserDTO> getRequestableUser(int page, User user) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> applicationUserDTOs = entityManager.
                createQuery(REQUESTABLE_USER_QUERY).
                setParameter("userId", user.getId()).
                getResultList();
        return applicationUserDTOs;
    }

    public List<ApplicationUserDTO> getFriendPendingUser(int page, User user) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> applicationUserDTOs = entityManager.
                createQuery(FRIEND_PENDING_QUERY).
                setParameter("fromId", user).
                getResultList();
        return applicationUserDTOs;

    }

    public List<ApplicationUserDTO> getFriendRequestUser(int page, User user) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> applicationUserDTOs = entityManager.
                createQuery(FRIEND_REQUEST_QUERY).
                setParameter("fromId", user).
                getResultList();
        return applicationUserDTOs;
    }

    @Transactional
    public void acceptFriendRequest(User fromUser, User toUser) {
        entityManager.
                createQuery(UPDATE_FRIEND_LIST_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                executeUpdate();
    }

    public List<ApplicationUserDTO> getFriendList(User user) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> applicationUserDTOs = entityManager.
                createQuery(ALL_FRIEND_QUERY).
                setParameter("userId", user.getId()).
                setParameter("user", user).
                getResultList();
        return applicationUserDTOs;
    }

    @Transactional
    public void rejectFriendRequest(User fromUser, User toUser) {
        entityManager.
                createQuery(DELETE_FROM_FRIEND_LIST_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                executeUpdate();

        entityManager.
                createQuery(DELETE_FROM_PERSONAL_MESSAGE_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                executeUpdate();

    }

    @Transactional
    public List<ChatPeopleDTO> getChatFriendList(User user) {
        @SuppressWarnings("unchecked")
        List<ChatPeopleDTO> chatPeopleDTOs = entityManager.
                createQuery(CHAT_FRIEND_LIST_QUERY).
                setParameter("userId", user.getId()).
                setParameter("user", user).getResultList();

        User fromUser = new User();
        for (ChatPeopleDTO chatPeopleDTO : chatPeopleDTOs) {
            fromUser.setId(chatPeopleDTO.getId());
            Long messageCount = (Long) entityManager.
                    createQuery(CHAT_MESSAGES_QUERY).
                    setParameter("fromUser", fromUser).
                    setParameter("toUser", user).
                    getSingleResult();
            chatPeopleDTO.setCountMessage(messageCount);
        }
        return chatPeopleDTOs;
    }

    public boolean hasRequest(User fromUser, User toUser) {
        Long size = (Long) entityManager.createQuery(IS_HAS_FRIEND_QUERY).
                setParameter("fromUser", fromUser).
                setParameter("toUser", toUser).
                getSingleResult();
        return size != 0;
    }


}
