package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.modules.build.distributedesigntask.entity.Message;
import com.zw.design.modules.build.distributedesigntask.entity.Receiver;

import java.util.List;

public interface MessageService {
    List<Receiver> findUserReceiver();

    void saveMessage(Message message, Integer projectId, List<String> users);
}
