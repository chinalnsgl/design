package com.zw.design.modules.system.taskname.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.taskname.entity.TaskName;
import com.zw.design.modules.system.taskname.query.TaskNameQuery;

public interface TaskNameService {

    // 按条件查询任务名称表格模型数据
    BaseDataTableModel<TaskName> findByQuery(TaskNameQuery query);

    // 按名称查询任务名称
    TaskName findByNameAndStatus(String name, Integer status);

    // 保存任务名称
    TaskName saveTaskName(TaskName taskName);

    // 修改任务名称
    TaskName updateTaskName(TaskName taskName);

    // 修改任务名称状态
    TaskName updateTaskNameStatus(Integer id, Integer status);
}
