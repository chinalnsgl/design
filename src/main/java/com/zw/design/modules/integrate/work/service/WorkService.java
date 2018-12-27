package com.zw.design.modules.integrate.work.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.integrate.work.model.WorkModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;

import java.util.List;

public interface WorkService {

    // 按状态查询任务类型
    List<TaskType> findTaskTypeByStatus(Integer status);

    // 按状态查询科室
    List<Section> findSectionByStatus(Integer status);

    // 按条件查询项目工时模型
    BaseDataTableModel<WorkModel> findEmployeeByQuery(WorkQuery query);

}
