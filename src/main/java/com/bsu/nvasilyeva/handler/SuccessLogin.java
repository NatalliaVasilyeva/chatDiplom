package com.bsu.nvasilyeva.handler;

import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.entity.AuthenticationLog;
import com.bsu.nvasilyeva.entity.User;
import com.bsu.nvasilyeva.listener.LoggedUserListener;
import com.bsu.nvasilyeva.service.AuthenticationLogService;
import com.bsu.nvasilyeva.service.ChatService;
import com.bsu.nvasilyeva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

@Component
public class SuccessLogin implements AuthenticationSuccessHandler {

	@Autowired
    UserService userService;
	@Autowired
    ActiveUserStore activeUserStore;

	@Autowired
    ChatService chatService;

	@Autowired
    AuthenticationLogService authenticationLogService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LoggedUserListener loggedUser = null;
		User user = null;

		HttpSession session = request.getSession(false);

		if (session != null) {
			if (!activeUserStore.getUsers().contains(authentication.getName())) {
				user = userService.findByEmail(authentication.getName());
				loggedUser = new LoggedUserListener(authentication.getName(), activeUserStore);
				session.setAttribute("loggedUser", loggedUser);
				session.setAttribute("userSession", user);

				// user log
				AuthenticationLog authenticationLog = authenticationLogService.buildPOJOObject();
				authenticationLog.setUser(user);
				authenticationLogService.add(authenticationLog);

				// redirect to home page
				response.sendRedirect(request.getContextPath() + "/home");
			} else {
				request.setAttribute("message", "You are allready logged in..");
				request.getRequestDispatcher("/login").forward(request, response);
			}

		}
		try {
			chatService.sendToAllFriendOnlineMessage(authentication.getName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
