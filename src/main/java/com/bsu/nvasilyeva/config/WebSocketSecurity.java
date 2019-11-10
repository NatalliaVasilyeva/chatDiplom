package com.bsu.nvasilyeva.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Set security for all messages from web page to server
 */

@Configuration
public class WebSocketSecurity extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    /**
     * Method explain that any message can not be sent not authorized user
     * @param messages
     */

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages.simpDestMatchers("/connect/**").authenticated().anyMessage().authenticated();
    }

    /**
     * <p>
     * Determines if a CSRF token is required for connecting. This protects against remote
     * sites from connecting to the application and being able to read/write data over the
     * connection. The default is false (the token is required).
     * </p>
     * <p>
     * Subclasses can override this method to disable CSRF protection
     * </p>
     *
     * @return false if a CSRF token is required for connecting, else true
     */

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
