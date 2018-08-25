package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.DeptTaskDto;
import com.zw.design.dto.TaskDto;
import com.zw.design.entity.DeptTask;
import com.zw.design.entity.ProduceTask;
import com.zw.design.query.ProjectQuery;
import com.zw.design.query.TaskQuery;

public interface TaskService {

    DataTablesCommonDto<TaskDto> findByCriteria(ProjectQuery query);

    DeptTask saveDeptTask(DeptTask deptTask);

    ProduceTask saveProduceTask(ProduceTask produceTask);

    DataTablesCommonDto<DeptTaskDto> findTasksByCriteria(TaskQuery query);

    ProduceTask saveProduceComment(ProduceTask produceTask);

    DeptTask saveDeptComment(DeptTask deptTask);
}
