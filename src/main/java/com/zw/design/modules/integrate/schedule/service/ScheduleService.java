package com.zw.design.modules.integrate.schedule.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.schedule.model.ScheduleModel;
import com.zw.design.modules.integrate.schedule.query.ScheduleQuery;

public interface ScheduleService {

    // 按条件查询项目
    BaseDataTableModel<ScheduleModel> findProjectByQuery(ScheduleQuery query);

}
