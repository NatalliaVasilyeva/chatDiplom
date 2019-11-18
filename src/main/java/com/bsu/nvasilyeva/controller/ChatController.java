package com.bsu.nvasilyeva.controller;

import com.bsu.nvasilyeva.dto.*;
import com.bsu.nvasilyeva.entity.GroupMessageTransaction;
import com.bsu.nvasilyeva.entity.Notification;
import com.bsu.nvasilyeva.entity.ParticipantRepository;
import com.bsu.nvasilyeva.entity.User;
import com.bsu.nvasilyeva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
public class ChatController {

    /**
     * Defines a set of methods that a servlet uses to communicate with its
     * servlet container, for example, to get the MIME type of a file, dispatch
     * requests, or write to a log file.
     */
    @Autowired
    ServletContext servletContext;

    @Autowired
    FriendListService friendListService;

    @Autowired
    ChatService chatService;

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    GroupMemberService groupMemberService;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    GroupMessageTransactionDetailService groupMessageTransactionDetailService;

    @Autowired
    ImageService imageService;


    @RequestMapping("/chat")
    public ModelAndView doingChat(@SessionAttribute("userSession") User user) throws ParseException {
        return new ModelAndView("chat", "users", chatService.getGroupAndFriendWithMessaggeCount(user));
    }

    @SubscribeMapping("/getHomeDTO")
    public void getHomeDTO(Principal principal) {
        HomeDTO homeDTO = chatService.getHomeDTO(principal.getName());
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/getHomeDTO", homeDTO);
    }

    @MessageMapping("/loadChat/{userId}/{fromId}")
    @SendToUser("/broker/getAllPersonalChat")
    public List<LoadMessagesDTO> loadUserchat(@DestinationVariable("userId") String userId,
                                              @DestinationVariable("fromId") String fromId,
                                              Principal principal) {
        return chatService.getAllPersonalChat(fromId, userId);
    }

    @MessageMapping("/sendPersonalMessage/{userId}")
    public void getUserMessage(@DestinationVariable("userId") String userId,
                               Principal principal,
                               MessageDTO message,
                               @SessionAttribute("userSession") User user) {

        String path = null;
        if (message.getMessage() == null) {
            String location = servletContext.getRealPath("/") + File.separator + "resources" + File.separator
                    + "shareImage";
            path = imageService.storeImage(message.getFile(), location);
        }
        message.setFile(path);
        List<String> userStore = activeUserStore.getUsers();
        if (userId.substring(0, 4).equals("USER")) {
            if (userStore.contains(message.getToEmail())) {
                int messageId = chatService.addMessage(message.getFromId(), userId, message.getMessage(), path, true);
                message.setMessageId(messageId);
                message.setFromEmail(principal.getName());
                simpMessagingTemplate.convertAndSendToUser(message.getToEmail(), "/broker/getOnePersonalMessage",
                        message);
            } else {
                chatService.addMessage(message.getFromId(), userId, message.getMessage(), path, false);
            }
        } else {
            List<ApplicationUserDTO> groupMembers = groupMemberService.getGroupMemberList(userId, message.getFromId());
            GroupMessageTransaction messageTransaction = chatService.addMessageInGroup(message.getFromId(), userId,
                    message.getMessage(), path);
            message.setToEmail(principal.getName());
            message.setFromId(userId);
            for (ApplicationUserDTO member : groupMembers) {
                if (userStore.contains(member.getEmail())) {
                    int transcationId = groupMessageTransactionDetailService.add(member.getId(), messageTransaction,
                            true);
                    message.setMessageId(transcationId);
                    simpMessagingTemplate.convertAndSendToUser(member.getEmail(), "/broker/getOnePersonalMessage",
                            message);
                } else {
                    groupMessageTransactionDetailService.add(member.getId(), messageTransaction, false);
                }
            }
        }
    }

    @MessageMapping("/changePersonalMessageStatusNotReaded")
    public void changePersonalMessageStatusNotReaded(Principal principal, MessageDTO messageDTO) {
        if (messageDTO.getFromId().substring(0, 4).equals("USER")) {
            chatService.changePersonalMessageStatusNotReaded(messageDTO.getMessageId());
        } else {
            groupMessageTransactionDetailService.changeMessageStatusNotReaded(messageDTO.getMessageId());
        }
    }

    @MessageMapping("/changePersonalMessageStatusAllReaded/{userId}/{fromId}")
    public void changePersonalMessageStatusAllReaded(@DestinationVariable("userId") String userId,
                                                     @DestinationVariable("fromId") String fromId) {
        if (userId.substring(0, 4).equals("USER")) {
            chatService.changePersonalMessageStatusAllReaded(userId, fromId);
        } else {
            chatService.changeAllGroupMessageStatusAllReaded(userId, fromId);
        }
    }

    @MessageMapping("/changeStatusNotificationsReaded")
    public void changeStatusNotificationsReaded(Principal principal) {

        List<NotificationDTO> allNotifications = notificationService.getAllNotifications(principal.getName());
        chatService.changeStatusNotificationsReaded(principal.getName());
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/receiveAllNotification",
                allNotifications);

    }

    @MessageMapping("/userSendFriendRequest/{id}/{email}")
    public void userSendFriendRequest(@DestinationVariable("id") String id, @DestinationVariable("email") String email,
                                      Principal principal) {
        if (!friendListService.hasRequest(principal.getName(), id)) {
            friendListService.sendRequest(principal.getName(), id);
            chatService.addNotification(id, email, " sent a friend request.", Notification.SENDREQUEST,
                    principal.getName());
        }

    }

    @MessageMapping("/acceptFriendRequest/{id}/{email}")
    public void acceptFriendRequest(@DestinationVariable("id") String id, @DestinationVariable("email") String email,
                                    Principal principal) {
        friendListService.acceptFriendRequest(id, principal.getName());
        chatService.addNotification(id, email, " accept your friend request.", Notification.ACCEPTREQUEST,
                principal.getName());

    }

    @MessageMapping("/rejectFriendRequest/{id}/{email}")
    public void rejectFriendRequest(@DestinationVariable("id") String id, @DestinationVariable("email") String email,
                                    Principal principal) {
        friendListService.rejectFriendRequest(id, principal.getName());
        chatService.addNotification(id, email, " reject your friend request.", Notification.REJECTREQUEST,
                principal.getName());
    }

    @MessageMapping("/unFriendRequest/{id}/{email}")
    public void unFriendRequest(@DestinationVariable("id") String id, @DestinationVariable("email") String email,
                                Principal principal) {
        friendListService.rejectFriendRequest(id, principal.getName());
        chatService.addNotification(id, email, " is deleted to you from his friendlist.", Notification.REJECTREQUEST,
                principal.getName());
    }

    @MessageMapping("/typingNotification/{toEmail}/{typeFlag}")
    public void typingNotification(@DestinationVariable("toEmail") String toEmail,
                                   @DestinationVariable("typeFlag") String typeFlag, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(toEmail, "/broker/subscriptionTypingNotification", typeFlag);

    }

    @MessageExceptionHandler
    @SendToUser(destinations = "/broker/errors", broadcast = false)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }

}
