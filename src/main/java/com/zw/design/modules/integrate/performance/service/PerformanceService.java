package com.zw.design.modules.integrate.performance.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import com.zw.design.modules.lookboard.single.entity.Message;

import java.util.List;

public interface PerformanceService {

    // 按条件查询项目
    BaseDataTableModel<Project> findProjectByQuery(PerformanceQuery query);

}
