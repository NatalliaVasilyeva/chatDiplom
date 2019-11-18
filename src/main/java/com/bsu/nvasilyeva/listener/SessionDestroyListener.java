package com.bsu.nvasilyeva.listener;

import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.service.AuthenticationLogService;
import com.bsu.nvasilyeva.service.ChatService;
import com.bsu.nvasilyeva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Component
public class SessionDestroyListener {

    @Autowired
    UserService userService;
    @Autowired
    ActiveUserStore activeUserStore;
    @Autowired
    ChatService chatService;

    @Autowired
    AuthenticationLogService authenticationLogService;

    @EventListener
    public void sessionCreatelistener(HttpSessionCreatedEvent event) {
    }

    @EventListener
    public void sessionDestroylistener(HttpSessionDestroyedEvent event) throws ParseException {
        HttpSession session = event.getSession();
        LoggedUserListener loggedUser = (LoggedUserListener) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            userService.updateTime(loggedUser.getEmail());
            activeUserStore.getUsers().remove(loggedUser.getEmail());
            authenticationLogService.changeLogoutTime(userService.findByEmail(loggedUser.getEmail()));
            chatService.sendToAllFriendOflineMessage(loggedUser.getEmail());
        }
    }

}

