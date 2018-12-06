package com.zw.design.modules.build.distribute.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.distribute.model.CollectDto;
import com.zw.design.modules.build.distribute.model.DeptTaskDto;
import com.zw.design.modules.build.distribute.model.TaskDto;
import com.zw.design.modules.build.distribute.entity.DeptTask;
import com.zw.design.modules.build.distribute.entity.ProduceTask;
import com.zw.design.modules.build.distribute.query.ProjectQuery;
import com.zw.design.modules.build.distribute.query.TaskQuery;

import java.util.List;

public interface TaskService {

    BaseDataTableModel<TaskDto> findByCriteria(ProjectQuery query);

    DeptTask saveDeptTask(DeptTask deptTask);

    ProduceTask saveProduceTask(ProduceTask produceTask);

    BaseDataTableModel<DeptTaskDto> findTasksByCriteria(TaskQuery query);

    ProduceTask saveProduceComment(ProduceTask produceTask);

    DeptTask saveDeptComment(DeptTask deptTask);

    List<CollectDto> findDeptTaskCollect(TaskQuery query);

    BaseDataTableModel<DeptTaskDto> findProcessList(TaskQuery query);

    BaseDataTableModel<DeptTaskDto> findProduceList(TaskQuery query);
}
