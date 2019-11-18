package com.bsu.nvasilyeva.controller;

import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.dto.ApplicationUserDTO;
import com.bsu.nvasilyeva.dto.GGroupDTO;
import com.bsu.nvasilyeva.entity.GGroup;
import com.bsu.nvasilyeva.entity.ParticipantRepository;
import com.bsu.nvasilyeva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.security.Principal;
import java.util.List;

@Controller
public class GroupChatController {

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
    GGroupService groupService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ImageService imageService;

    @Autowired
    GroupMemberService groupMemberService;

    @MessageMapping("/getfriendList")
    public void getFriendList(Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/subscriptionGetFriendList",
                chatService.getFriendList(principal.getName()));
    }

    @MessageMapping("/addGroup")
    public void getFriendList(Principal principal, GGroupDTO gGroupDTO) {
        GGroup group = chatService.addGroupWithMember(gGroupDTO);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/getGroupIdAfterRegister", group);
    }

    @MessageMapping("/editGroupMember")
    public void editGroupMember(Principal principal, GGroupDTO gGroupDTO) {
        groupMemberService.editGroupMember(gGroupDTO);
    }

    @MessageMapping("/getGroupMember/{groupId}")
    public void getGroupMember(Principal principal, @DestinationVariable("groupId") String groupId) {
        List<ApplicationUserDTO> groupMembers = groupMemberService.getGroupMember(groupId);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/getGroupMember", groupMembers);
    }

    @MessageMapping("/deleteGroupMember")
    public void deleteGroupMember(Principal principal, GGroupDTO gGroupDTO) {
        groupMemberService.deleteGroupMember(gGroupDTO);

    }

    @MessageMapping("/getGroupInfo/{groupId}")
    public void getGroupInfo(Principal principal, @DestinationVariable("groupId") String groupId) {
        GGroup group = groupMemberService.getGroupInfo(groupId);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/getGroupInfo",
                group);
    }

    @MessageMapping("/editGroupInfo")
    public void getGroupInfo(Principal principal, GGroupDTO groupDTO) {
        groupService.editGroupDetail(groupDTO);
    }


    @MessageMapping("/getFriendListExcludeGroupMember/{groupId}")
    public void getFriendListExcludeGroupMember(@DestinationVariable("groupId") String groupId, Principal principal) {
        List<ApplicationUserDTO> friends = groupMemberService.getFriendListExcludeGroupMember(principal.getName(), groupId);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/broker/getFriendListExcludeGroupMember",
                friends);
    }

    @MessageExceptionHandler
    @SendToUser(destinations = "/broker/errors", broadcast = false)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }

}
