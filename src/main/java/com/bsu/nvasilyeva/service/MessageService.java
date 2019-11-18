package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.MessageDAO;
import com.bsu.nvasilyeva.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    public void add(Message message) {
        messageDAO.add(message);
    }

    public Message findById(int id) {
        return messageDAO.findById(id);
    }
}
