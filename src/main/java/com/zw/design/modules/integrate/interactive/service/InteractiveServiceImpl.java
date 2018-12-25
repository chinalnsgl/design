package com.zw.design.modules.integrate.interactive.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.mapper.InteractiveMapper;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.lookboard.single.entity.Message;
import com.zw.design.modules.lookboard.single.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractiveServiceImpl implements InteractiveService {

    @Autowired
    private InteractiveMapper interactiveMapper;
    @Autowired
    private MessageRepository messageRepository;

    // 按条件查询项目
    @Override
    public BaseDataTableModel<Project> findProjectByQuery(InteractiveQuery query) {
        if (query.getSenderQuery() != null && !"".equals(query.getSenderQuery().trim())) {
            query.setSenderQuery("%" + query.getSenderQuery().trim() + "%");
        }
        if (query.getReceiverQuery() != null && !"".equals(query.getReceiverQuery().trim())) {
            query.setReceiverQuery("%" + query.getReceiverQuery().trim() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<Project> model = interactiveMapper.findProjectByQuery(query);
        PageInfo<Project> pageInfo = new PageInfo(model);
        BaseDataTableModel<Project> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

    // 按项目查询消息
    @Override
    public List<Message> findMessageByProjectId(Integer id) {
        return messageRepository.findByProject_IdAndStatus(id, 1);
    }
}
