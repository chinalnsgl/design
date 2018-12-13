package com.zw.design.modules.baseinfosetting.taskname.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.baseinfosetting.taskname.query.TaskNameQuery;
import com.zw.design.modules.baseinfosetting.taskname.repository.TaskNameRepository;
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
public class TaskNameServiceImpl implements TaskNameService {

    @Autowired
    private TaskNameRepository taskNameRepository;
    @Autowired
    private LogService logService;

    // 按条件查询任务名称表格模型数据
    @Override
    public BaseDataTableModel<TaskName> findByQuery(TaskNameQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<TaskName> sectionPage = taskNameRepository.findAll((Specification<TaskName>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<TaskName> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(sectionPage.getContent());
        baseDataTableModel.setRecordsTotal((int)sectionPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)sectionPage.getTotalElements());
        return baseDataTableModel;
    }

    // 按名称查询任务名称
    @Override
    public TaskName findByNameAndStatus(String name, Integer status) {
        return taskNameRepository.findByNameAndStatus(name, status);
    }

    // 保存任务名称
    @Override
    public TaskName saveTaskName(TaskName taskName) {
        logService.saveLog("创建任务名称", taskName.getName());
        if (taskName.getSectionType().getId() == null) {
            taskName.setSectionType(null);
        }
        return taskNameRepository.save(taskName);
    }

    // 修改任务名称
    @Override
    public TaskName updateTaskName(TaskName tn) {
        TaskName taskName = taskNameRepository.findById(tn.getId()).get();
        logService.saveLog("修改任务名称", taskName, tn);
        taskName.setName(tn.getName());
        taskName.setTaskType(tn.getTaskType());
        taskName.setOrderNo(tn.getOrderNo());
        if (tn.getSectionType().getId() == null) {
            taskName.setSectionType(null);
        } else {
            taskName.setSectionType(tn.getSectionType());
        }
        return taskNameRepository.save(taskName);
    }

    // 修改任务名称状态
    @Override
    public TaskName updateTaskNameStatus(Integer id, Integer status) {
        TaskName taskName = taskNameRepository.findById(id).get();
        logService.saveLog("删除任务名称", taskName.getName());
        taskName.setStatus(status);
        return taskNameRepository.save(taskName);
    }
}
