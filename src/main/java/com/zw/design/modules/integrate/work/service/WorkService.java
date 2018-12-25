package com.zw.design.modules.integrate.work.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;

public interface WorkService {

    // 按条件查询项目
    BaseDataTableModel<TaskEmployee> findEmployeeByQuery(WorkQuery query);

}
