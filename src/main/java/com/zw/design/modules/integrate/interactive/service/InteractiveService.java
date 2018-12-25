package com.zw.design.modules.integrate.interactive.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.lookboard.design.model.DesignModel;
import com.zw.design.modules.lookboard.design.query.DesignQuery;
import com.zw.design.modules.lookboard.single.entity.Message;

import java.util.List;

public interface InteractiveService {

    // 按条件查询项目
    BaseDataTableModel<Project> findProjectByQuery(InteractiveQuery query);

    // 按项目查询消息
    List<Message> findMessageByProjectId(Integer id);
}
