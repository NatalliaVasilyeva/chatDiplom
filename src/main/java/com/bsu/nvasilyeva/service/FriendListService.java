package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.FriendListDAO;
import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.dto.ApplicationUserDTO;
import com.bsu.nvasilyeva.dto.ChatPeopleDTO;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class FriendListService {

	@Autowired
	FriendListDAO friendListDAO;

	@Autowired
	ActiveUserStore activeUserStore;

	@Autowired
	UserService userService;

	public void sendRequest(String fromEmail, String toId) {
		User fromUser = userService.buildUserFromEmail(fromEmail);
		User toUser = userService.buildUserFromId(toId);

		friendListDAO.addRequest(fromUser, toUser);
	}

	public boolean hasRequest(String fromEmail, String toId) {
		User fromUser = userService.buildUserFromEmail(fromEmail);
		User toUser = userService.buildUserFromId(toId);
		return friendListDAO.hasRequest(fromUser, toUser);
	}

	public List<ApplicationUserDTO> getRequestableUser(int page, User user) {
		return friendListDAO.getRequestableUser(page, user);
	}

	public List<ApplicationUserDTO> getFriendPendingUser(int page, User user) throws ParseException {
		return friendListDAO.getFriendPendingUser(page, user);
	}

	public List<ApplicationUserDTO> getFriendRequestUser(int page, User user) throws ParseException {
		return friendListDAO.getFriendRequestUser(page, user);
	}

	public void acceptFriendRequest(String fromId, String toEmail) {
		User toUser = userService.buildUserFromEmail(toEmail);
		User fromUser = userService.buildUserFromId(fromId);
		friendListDAO.acceptFriendRequest(fromUser, toUser);
	}

	public List<ApplicationUserDTO> getFriendList(User user) {
		return friendListDAO.getFriendList(user);
	}

	public void rejectFriendRequest(String fromId, String toEmail) {
		User toUser = userService.buildUserFromEmail(toEmail);
		User fromUser = userService.buildUserFromId(fromId);
		friendListDAO.rejectFriendRequest(fromUser, toUser);

	}

	public List<ChatPeopleDTO> getChatFriendList(User user) throws ParseException {
		List<ChatPeopleDTO> chatPeopleDTOs = friendListDAO.getChatFriendList(user);
		List<String> users = activeUserStore.getUsers();
		for (ChatPeopleDTO chatPeopleDTO : chatPeopleDTOs) {
			if (users.contains(chatPeopleDTO.getEmail())) {
				chatPeopleDTO.setIsOnline(true);
			}
		}
		return chatPeopleDTOs;
	}
}
