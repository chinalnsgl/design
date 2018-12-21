package com.zw.design.modules.index.service;

import com.zw.design.modules.lookboard.single.entity.Receiver;
import com.zw.design.modules.lookboard.single.repository.ReceiverRepository;
import com.zw.design.modules.system.user.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ReceiverRepository receiverRepository;

    @Override
    public List<Receiver> findUserReceiver() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return receiverRepository.findByStatusAndUser_Id(1, user.getId());
    }


}
