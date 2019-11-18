package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.GroupMessageTransactionDAO;
import com.bsu.nvasilyeva.entity.GGroup;
import com.bsu.nvasilyeva.entity.GroupMessageTransaction;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GroupMessageTransactionService {

    @Autowired
    GroupMessageTransactionDAO groupMessageTransactionDAO;

    @Autowired
    UserService userService;

    public void add(GroupMessageTransaction groupMessageTransaction) {

        groupMessageTransactionDAO.add(groupMessageTransaction);
    }

    @Transactional
    public void changeAllGroupMessageStatusAllReaded(String groupId, String fromId) {
        GGroup group = new GGroup();
        group.setId(groupId);

        User user = userService.buildUserFromId(fromId);
        groupMessageTransactionDAO.changeAllGroupMessageStatusAllReaded(group, user);
    }

}
