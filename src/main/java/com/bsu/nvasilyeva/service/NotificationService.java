package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.NotificationDAO;
import com.bsu.nvasilyeva.dto.NotificationDTO;
import com.bsu.nvasilyeva.entity.Notification;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationDAO notificationDAO;

    @Autowired
    UserService userService;

    public void add(Notification notification) {
        notificationDAO.add(notification);
    }

    public Long getTotalUnreadNotificationCount(User user) {

        return notificationDAO.getTotalUnreadNotificationCount(user);
    }

    public void changeNotificationStatusAllReaded(User user, boolean isReaded) {
        notificationDAO.changeNotificationStatusAllReaded(user, true);
    }

    public List<NotificationDTO> getAllNotifications(String email) {
        User user = userService.buildUserFromEmail(email);
        return notificationDAO.getAllNotifications(user);
    }
}
