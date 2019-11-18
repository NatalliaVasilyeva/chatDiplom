package com.bsu.nvasilyeva.listener;

import com.bsu.nvasilyeva.dto.ActiveUserStore;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

public class LoggedUserListener implements HttpSessionBindingListener {

    private String email;
    private ActiveUserStore activeUserStore;

    public LoggedUserListener(String email, ActiveUserStore activeUserStore) {
        this.email = email;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUserListener() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUserListener user = (LoggedUserListener) event.getValue();
        if (users != null) {
            if (!users.contains(user.getEmail()))
                users.add(user.getEmail());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUserListener user = (LoggedUserListener) event.getValue();

        if (users != null && user != null) {
            if (users.contains(user.getEmail()))
                users.remove(user.getEmail());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    public void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

}
