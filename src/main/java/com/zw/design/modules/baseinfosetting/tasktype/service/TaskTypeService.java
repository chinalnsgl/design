package com.zw.design.modules.baseinfosetting.tasktype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.query.TaskTypeQuery;

import java.util.List;

public interface TaskTypeService {

    // 按条件查询任务类型表格模型数据
    BaseDataTableModel<TaskType> findByQuery(TaskTypeQuery query);

    // 按名称查询任务类型
    TaskType findByNameAndStatus(String name, Integer status);

    // 按任务类型状态查询任务类型集合
    List<TaskType> findByStatus(Integer status);

    // 保存任务类型
    TaskType saveTaskType(TaskType taskType);

    // 修改任务类型
    TaskType updateTaskType(TaskType taskType);

    // 修改任务类型状态
    TaskType updateTaskTypeStatus(Integer id, Integer status);
}
