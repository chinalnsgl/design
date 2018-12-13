package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.repository.TaskTypeRepository;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import com.zw.design.modules.build.distributedesigntask.query.DistributeDesignTaskQuery;
import com.zw.design.modules.build.distributedesigntask.repository.TaskRepository;
import com.zw.design.modules.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DistributeDesignTaskServiceImpl implements DistributeDesignTaskService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private LogService logService;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<Project> findProjectsByQuery(DistributeDesignTaskQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Project> projectPage = projectRepository.findAll((Specification<Project>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            if (null != query.getCodeQuery() && !"".equals(query.getCodeQuery())) {
                list.add(criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCodeQuery() + "%"));
            }
            if (null != query.getStatusQuery()) {
                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), query.getStatusQuery()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<Project> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(projectPage.getContent());
        baseDataTableModel.setRecordsTotal((int)projectPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)projectPage.getTotalElements());
        return baseDataTableModel;
    }

    // 下达设计任务
    @Transactional
    @CacheEvict(value = "projects",allEntries = true)
    @Override
    public boolean distributeDesignTAsk(Integer projectId, Integer[] sectionId) {
        Project project = projectRepository.findById(projectId).get();
        project.setStatus(2); // 设置项目为已下达状态
        logService.saveLog("下达任务单", project.getName() + " 项目号：" + project.getCode());
        Task task;
        StringBuilder designDepts = new StringBuilder();
        for (Integer id : sectionId) {
            if (id != null) {
                Section section = sectionRepository.findById(id).get();
                designDepts.append(section.getName() + ",");
                for (TaskName taskName : section.getSectionType().getTaskNames()) {
                    task = new Task();
                    task.setAlias(taskName.getName());
                    task.setSection(section);
                    task.setTaskName(taskName);
                    task.setOrderNo(taskName.getOrderNo());
                    task.setProject(project);
                    taskRepository.save(task);
                }
            }
        }
        project.setDesignDepts(designDepts.toString().substring(0, designDepts.lastIndexOf(",")));
        projectRepository.save(project);
        List<TaskType> taskTypes = taskTypeRepository.findByIdNotAndStatus(2, 1);
        for (TaskType taskType : taskTypes) {
            for (TaskName taskName : taskType.getTaskNames()) {
                task = new Task();
                task.setAlias(taskName.getName());
                task.setTaskName(taskName);
                task.setOrderNo(taskName.getOrderNo());
                task.setProject(project);
                if (taskName.getId() == 1) {
                    task.setStartTime(new Date());
                    task.setEndTime(new Date());
                    task.setCompStatus(2);
                }
                taskRepository.save(task);
            }
        }
        return true;
    }

}
