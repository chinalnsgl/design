package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.query.DistributeDesignTaskQuery;

public interface DistributeDesignTaskService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<Project> findProjectsByQuery(DistributeDesignTaskQuery query);

    // 下达设计任务
    boolean distributeDesignTAsk(Integer projectId, Integer[] sectionId);

}
