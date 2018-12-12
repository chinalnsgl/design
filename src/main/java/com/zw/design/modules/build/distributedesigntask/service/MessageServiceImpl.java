package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.modules.build.distributedesigntask.entity.Message;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.entity.Receiver;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.build.distributedesigntask.repository.MessageRepository;
import com.zw.design.modules.build.distributedesigntask.repository.ReceiverRepository;
import com.zw.design.modules.system.user.repository.SysUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ReceiverRepository receiverRepository;

    @Override
    public List<Receiver> findUserReceiver() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return receiverRepository.findByStatusAndUser_Id(1, user.getId());
    }

    @Override
    public void saveMessage(Message message, Integer projectId, List<String> users) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        message.setUser(user);
        Project project = new Project();
        project.setId(projectId);
        message.setProject(project);
        message.setUsers(StringUtils.join(users.toArray(), ","));
        messageRepository.save(message);
        for (String s : users) {
            SysUser u = sysUserRepository.findByName(s);
            Receiver receiver = new Receiver();
            receiver.setMessage(message);
            receiver.setProject(project);
            receiver.setUser(u);
            receiverRepository.save(receiver);
        }
    }

}
