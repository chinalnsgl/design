package com.zw.design.modules.build.distribute.service;

import com.zw.design.modules.build.distribute.entity.Message;
import com.zw.design.modules.build.distribute.entity.Receiver;

import java.util.List;

public interface MessageService {
    List<Receiver> findUserReceiver();

    void saveMessage(Message message, Integer projectId, List<String> users);
}
