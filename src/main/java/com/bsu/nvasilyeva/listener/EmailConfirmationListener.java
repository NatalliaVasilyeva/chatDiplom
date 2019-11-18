package com.bsu.nvasilyeva.listener;

import com.bsu.nvasilyeva.event.EmailConfirmationEvent;
import com.bsu.nvasilyeva.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailConfirmationListener {

    @Autowired
    VerificationTokenService tokenService;

    @EventListener
    @Async
    public void onApplicationEvent(EmailConfirmationEvent event) {
        tokenService.sendTo(event.getUser(), event.getPurpose(), event.getContextPath());
    }

}
