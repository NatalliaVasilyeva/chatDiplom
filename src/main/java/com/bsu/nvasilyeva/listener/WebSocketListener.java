package com.bsu.nvasilyeva.listener;

import com.bsu.nvasilyeva.handler.ParticipantHandler;
import com.bsu.nvasilyeva.service.ChatService;
import com.bsu.nvasilyeva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.ParseException;

@Component
public class WebSocketListener {

	@Autowired
	ParticipantHandler participantHandler;

	@Autowired
    UserService userService;

	@Autowired
    ChatService chatService;

	@Autowired
	SimpMessagingTemplate messageTemplet;

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) throws ParseException {
	}

}
