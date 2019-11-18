package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.GGroupDAO;
import com.bsu.nvasilyeva.dto.GGroupDTO;
import com.bsu.nvasilyeva.entity.GGroup;
import com.bsu.nvasilyeva.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

@Service
public class GGroupService {

    @Autowired
    GGroupDAO groupDAO;

    @Autowired
    ServletContext servletContext;

    @Autowired
    GroupMemberService memberService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    public void addGroup(GGroup group) {
        groupDAO.addGroup(group);
    }

    public void editGroupDetail(GGroupDTO groupDTO) {
        GGroup group = new GGroup();
        if (groupDTO.getFile() != null) {
            String location = servletContext.getRealPath("/") + File.separator + "resources" + File.separator
                    + "profileImage";

            String path = imageService.storeImage(groupDTO.getFile(), location);
            group.setProfileImagePath(path);
        } else {
            String path = groupDAO.getProfileImageById(groupDTO.getGroupId());
            group.setProfileImagePath(path);
        }
        group.setId(groupDTO.getGroupId());
        group.setName(groupDTO.getName());
        group.setStatus(groupDTO.getStatus());
        groupDAO.updateGroup(group);


        List<String> groupMemberEmails = memberService.getGroupMemberEmials(group, userService.buildUserFromId(groupDTO.getLeaderId()));
        String fromEmail = userService.getEmailById(groupDTO.getLeaderId());
        for (String memberEmail : groupMemberEmails) {
            chatService.addNotification("", memberEmail, " change group detail of " + group.getName() + " Group",
                    Notification.MEMBEROFGROUP, fromEmail);
        }
    }
}
