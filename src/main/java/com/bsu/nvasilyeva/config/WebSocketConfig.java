package com.bsu.nvasilyeva.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


/**
 * Make setting for websocket technology
 */


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


	/**
	 * Use to sent information from client messages
	 * @param registry helper which sent messages
	 */

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/broker");
		registry.setApplicationDestinationPrefixes("/app");
	}

	/**
	 * Help register endpoint
	 * @param registry  helper which register endpoint for chat
	 */

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		registry.addEndpoint("/connect").withSockJS();
	}


	/**
	 * Set max message size
	 * @param registration parameter which keep information about max message size
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.setMessageSizeLimit(11 * 1024 * 1024);
	}
}
