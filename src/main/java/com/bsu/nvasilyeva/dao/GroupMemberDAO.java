package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.dto.ApplicationUserDTO;
import com.bsu.nvasilyeva.dto.ChatPeopleDTO;
import com.bsu.nvasilyeva.entity.GGroup;
import com.bsu.nvasilyeva.entity.GroupMember;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GroupMemberDAO {

    private static final String CHAT_MEMBER_QUERY = "select new com.bsu.nvasilyeva.dto.ChatPeopleDTO(g.id,g.name,g.profileImagePath,m.isLeader) from GGroup g left join GroupMember m on g.id=m.groupId where m.toId like :user and m.enable=true";
    private static final String GROUP_MESSAGE_QUERY = "select count(*) From GroupMessageTransaction g left join GroupMessageTransactionDetail gd on g.id=gd.groupMessageTransaction where g.groupId=:groupId and gd.userId=:toUser and gd.isReaded=false";
    private static final String GROUP_MEMBER_LIST_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.email) from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
    private static final String GROUP_MEMBER_ID_QUERY = "select u.id from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
    private static final String GROUP_MEMBER_EMAIL_QUERY = "select u.email from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
    private static final String DELETE_GROUP_MEMBER_QUERY = "delete from GroupMember where groupId=:group and toId=:user";
    private static final String GROUP_MEMBER_QUERY = "select new com.bsu.nvasilyeva.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,m.isLeader)  from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true";


    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addMember(GroupMember groupMember) {

        entityManager.persist(groupMember);
    }


    public List<ChatPeopleDTO> getGroups(User user) {
        @SuppressWarnings("unchecked")
        List<ChatPeopleDTO> chatPeopleDTOs = entityManager.
                createQuery(CHAT_MEMBER_QUERY).
                setParameter("user", user).
                getResultList();

        User toUser = new User();
        GGroup group = new GGroup();
        for (ChatPeopleDTO chatPeopleDTO : chatPeopleDTOs) {
            toUser.setId(user.getId());
            group.setId(chatPeopleDTO.getId());

            Long messageCount = (Long) entityManager.
                    createQuery(GROUP_MESSAGE_QUERY).
                    setParameter("toUser", toUser).
                    setParameter("groupId", group).
                    getSingleResult();
            chatPeopleDTO.setCountMessage(messageCount);
        }
        return chatPeopleDTOs;
    }

    public List<ApplicationUserDTO> getGroupMemberList(GGroup group, User user) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> groupMembers = entityManager.
                createQuery(GROUP_MEMBER_LIST_QUERY).setParameter("group", group).
                setParameter("userId", user.getId()).
                getResultList();
        return groupMembers;
    }

    public List<String> getGroupMemberIds(GGroup group, User user) {
        @SuppressWarnings("unchecked")
        List<String> groupMembersIds = entityManager.
                createQuery(GROUP_MEMBER_ID_QUERY).
                setParameter("group", group).
                setParameter("userId", user.getId()).
                getResultList();
        return groupMembersIds;
    }

    public List<String> getGroupMemberEmails(GGroup group, User user) {
        @SuppressWarnings("unchecked")
        List<String> groupMembersIds = entityManager.
                createQuery(GROUP_MEMBER_EMAIL_QUERY).
                setParameter("group", group).
                setParameter("userId", user.getId())
                .getResultList();
        return groupMembersIds;
    }

    @Transactional
    public void deleteGroupMember(GGroup group, User user) {
        entityManager.createQuery(DELETE_GROUP_MEMBER_QUERY).
                setParameter("group", group).
                setParameter("user", user).
                executeUpdate();
    }


    public GGroup getGroupInfo(String groupId) {
        return entityManager.
                find(GGroup.class, groupId);
    }


    public List<ApplicationUserDTO> getGroupMember(GGroup group) {
        @SuppressWarnings("unchecked")
        List<ApplicationUserDTO> groupMembers = entityManager.
                createQuery(GROUP_MEMBER_QUERY).
                setParameter("group", group)
                .getResultList();
        return groupMembers;
    }


}
