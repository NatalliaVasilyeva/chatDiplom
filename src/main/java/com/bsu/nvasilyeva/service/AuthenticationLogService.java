package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.AuthenticationLogDAO;
import com.bsu.nvasilyeva.dto.LogDTO;
import com.bsu.nvasilyeva.entity.AuthenticationLog;
import com.bsu.nvasilyeva.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthenticationLogService {

    @Autowired
    AuthenticationLogDAO authenticationDao;

    public List<LogDTO> list() {
        return authenticationDao.list();
    }

    public void add(AuthenticationLog log) {
        authenticationDao.add(log);
    }

    public void changeLogoutTime(User user) {
        authenticationDao.changeLogoutTime(user);
    }

    public AuthenticationLog buildPOJOObject() {
        AuthenticationLog authenticationLog = new AuthenticationLog();
        authenticationLog.setCreatedAt(new Date());
        authenticationLog.setLoginTime(new Date());
        authenticationLog.setLogoutTime(new Date());
        return authenticationLog;
    }

}
