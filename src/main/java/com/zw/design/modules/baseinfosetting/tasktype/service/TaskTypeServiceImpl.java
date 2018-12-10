package com.zw.design.modules.baseinfosetting.tasktype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.query.TaskTypeQuery;
import com.zw.design.modules.baseinfosetting.tasktype.repository.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private LogService logService;

    // 按条件查询任务类型表格模型数据
    @Override
    public BaseDataTableModel<TaskType> findByQuery(TaskTypeQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<TaskType> secTypePage = taskTypeRepository.findAll((Specification<TaskType>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<TaskType> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(secTypePage.getContent());
        baseDataTableModel.setRecordsTotal((int)secTypePage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)secTypePage.getTotalElements());
        return baseDataTableModel;
    }

    // 按名称查询任务类型
    @Override
    public TaskType findByNameAndStatus(String name, Integer status) {
        return taskTypeRepository.findByNameAndStatus(name, status);
    }

    // 按任务类型状态查询任务类型集合
    @Override
    public List<TaskType> findByStatus(Integer status) {
        return taskTypeRepository.findByStatus(status);
    }

    // 保存任务类型
    @Override
    public TaskType saveTaskType(TaskType taskType) {
        logService.saveLog("创建任务类型", taskType.getName());
        return taskTypeRepository.save(taskType);
    }

    // 修改任务类型
    @Override
    public TaskType updateTaskType(TaskType taskType) {
        TaskType taskType1 = taskTypeRepository.findById(taskType.getId()).get();
        logService.saveLog("修改任务类型", taskType1, taskType);
        taskType1.setName(taskType.getName());
        return taskTypeRepository.save(taskType1);
    }

    // 修改任务类型状态
    @Override
    public TaskType updateTaskTypeStatus(Integer id, Integer status) {
        TaskType taskType = taskTypeRepository.findById(id).get();
        logService.saveLog("删除任务类型", taskType.getName());
        taskType.setStatus(status);
        return taskTypeRepository.save(taskType);
    }
}
