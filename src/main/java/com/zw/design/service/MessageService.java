package com.zw.design.service;

import com.zw.design.entity.Message;
import com.zw.design.entity.Receiver;

import java.util.List;

public interface MessageService {
    List<Receiver> findUserReceiver();

    void saveMessage(Message message, Integer projectId, List<String> users);
}
