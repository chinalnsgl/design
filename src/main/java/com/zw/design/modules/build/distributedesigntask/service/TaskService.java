package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.distributedesigntask.model.CollectDto;
import com.zw.design.modules.build.distributedesigntask.model.DeptTaskDto;
import com.zw.design.modules.build.distributedesigntask.model.TaskDto;
import com.zw.design.modules.build.distributedesigntask.entity.DeptTask;
import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import com.zw.design.modules.build.distributedesigntask.query.ProjectQuery;
import com.zw.design.modules.build.distributedesigntask.query.TaskQuery;

import java.util.List;

public interface TaskService {

    // 下达设计任务
    boolean distributeDesignTAsk(Integer projectId, Integer[] sectionId);

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
