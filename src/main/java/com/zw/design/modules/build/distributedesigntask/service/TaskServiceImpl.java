package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.baseinfosetting.sectiontype.entity.SectionType;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.repository.TaskTypeRepository;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import com.zw.design.modules.build.distributedesigntask.model.CollectDto;
import com.zw.design.modules.build.distributedesigntask.model.DeptTaskDto;
import com.zw.design.modules.build.distributedesigntask.model.TaskDto;
import com.zw.design.modules.build.distributedesigntask.entity.DeptTask;
import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.mapper.DeptTaskDao;
import com.zw.design.modules.build.distributedesigntask.query.ProjectQuery;
import com.zw.design.modules.build.distributedesigntask.query.TaskQuery;
import com.zw.design.modules.build.distributedesigntask.repository.DeptTaskRepository;
import com.zw.design.modules.build.distributedesigntask.repository.ProduceTaskRepository;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.build.distributedesigntask.repository.TaskRepository;
import com.zw.design.modules.system.log.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private DeptTaskRepository deptTaskRepository;
    @Autowired
    private ProduceTaskRepository produceTaskRepository;
    @Autowired
    private DeptTaskDao taskDao;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;

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
                task.setProject(project);
                if ("任务单".equals(taskName.getName())) {
                    task.setStartTime(new Date());
                    task.setEndTime(new Date());
                    task.setStatus(2);
                }
                taskRepository.save(task);
            }
        }
        return true;
    }

    /**
     * 保存科室任务
     */
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public DeptTask saveDeptTask(DeptTask deptTask) {
        DeptTask t = deptTaskRepository.findById(deptTask.getId()).get();

        t.setComment(deptTask.getComment());
        t.setPrincipal(deptTask.getPrincipal());
        t.setPlanStartTime(deptTask.getPlanStartTime());
        if (deptTask.getStatus() != null && deptTask.getStatus() != 0) {
            t.setStatus(deptTask.getStatus());
        }
        if (deptTask.getStatus() != null && deptTask.getStatus() == 1) {
            t.setStartTime(new Date());
            // 设置部门执行顺序
            /*Integer num = taskDao.findMaxStepNumByProjectId(t.getProject().getId());
            if (num == null) {
                t.setStepNum(1);
            } else {
                t.setStepNum(num + 1);
            }*/
        } else if (deptTask.getStatus() != null && deptTask.getStatus() == 2) {
            t.setEndTime(new Date());
        }
        // 判断并设置项目完成状态
        t = deptTaskRepository.saveAndFlush(t);
        Project p = projectRepository.findById(t.getProject().getId()).get();
        float produceStatus = taskDao.findProduceCompleteStatus(p.getId());
        float deptStatus = taskDao.findDeptCompleteStatus(p.getId());
        if (produceStatus == 2 && deptStatus == 2) {
            p.setStatus(4);
            p.setCompleteTime(new Date());
            projectRepository.saveAndFlush(p);
        }

        return t;
    }

    /**
     * 保存生产任务
     */
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public ProduceTask saveProduceTask(ProduceTask produceTask) {
        ProduceTask t = produceTaskRepository.findById(produceTask.getId()).get();
        // 单独处理签协议和基础条件任务
        if (t.getProduceNum() == 2 || t.getProduceNum() == 9) {
            if (produceTask.getStatus() != null && produceTask.getStatus() != 0) {
                t.setStatus(2);
                t.setStartTime(new Date());
                t.setEndTime(new Date());
            }
        } else {
            if (produceTask.getStatus() != null && produceTask.getStatus() != 0) {
                t.setStatus(produceTask.getStatus());
            }
            if (produceTask.getStatus() != null && produceTask.getStatus() == 1) {
                t.setStartTime(new Date());
            } else if (produceTask.getStatus() != null && produceTask.getStatus() == 2) {
                t.setEndTime(new Date());
            }
        }
        t.setComment(produceTask.getComment());
        t.setContractNo(produceTask.getContractNo());
        t.setPrincipal(produceTask.getPrincipal());
        t.setPlanStartTime(produceTask.getPlanStartTime());

        // 判断并设置项目完成状态
        t = produceTaskRepository.saveAndFlush(t);
        Project p = projectRepository.findById(t.getProject().getId()).get();
        float produceStatus = taskDao.findProduceCompleteStatus(p.getId());
        float deptStatus = taskDao.findDeptCompleteStatus(p.getId());
        if (produceStatus == 2 && deptStatus == 2) {
            p.setStatus(4);
            p.setCompleteTime(new Date());
            projectRepository.saveAndFlush(p);
        }

        return t;
    }

    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public ProduceTask saveProduceComment(ProduceTask produceTask) {
        ProduceTask t = produceTaskRepository.findById(produceTask.getId()).get();
        t.setComment(produceTask.getComment());
        return produceTaskRepository.saveAndFlush(t);
    }

    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public DeptTask saveDeptComment(DeptTask deptTask) {
        DeptTask t = deptTaskRepository.findById(deptTask.getId()).get();
        t.setComment(deptTask.getComment());
        return deptTaskRepository.saveAndFlush(t);
    }

    /**
     * 多项目看板列表
     */
    @Cacheable(value = "projects",key = "'many'+#query")
    public BaseDataTableModel<TaskDto> findByCriteria(ProjectQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery() + "%");
        }
        if (query.getDemander() != null && !"".equals(query.getDemander().trim())) {
            query.setDemander("%" + query.getDemander() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);
        List<TaskDto> dtos = taskDao.findTasks(query);
        Integer count = taskDao.findTasksCount(query);
        BaseDataTableModel<TaskDto> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(dtos);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }

    /**
     * 科室看板
     */
    @Override
    @Cacheable(value = "projects",key = "'dept'+#query")
    public BaseDataTableModel<DeptTaskDto> findTasksByCriteria(TaskQuery query) {
        if (query.getDepartmentQuery() != null && !"".equals(query.getDepartmentQuery())) {
            String[] strings = query.getDepartmentQuery().split(",");
            query.setType(Integer.parseInt(strings[0]));
            query.setNum(Integer.parseInt(strings[1]));
        }
        List<Integer> ids = taskDao.findIdsByDept(query);
        query.setIds(StringUtils.join(ids.toArray(), ","));
        if (query.getCode() != null && !"".equals(query.getCode().trim())) {
            query.setCode("%" + query.getCode() + "%");
        }
        if (query.getName() != null && !"".equals(query.getName().trim())) {
            query.setName("%" + query.getName() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);

        List<DeptTaskDto> dtos = taskDao.findDeptTaskList(query);
        Integer count = taskDao.findDeptTaskCount(query);
        BaseDataTableModel<DeptTaskDto> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(dtos);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }

    @Override
    public List<CollectDto> findDeptTaskCollect(TaskQuery query) {
        if (query.getDepartmentQuery() != null && !"".equals(query.getDepartmentQuery())) {
            String[] strings = query.getDepartmentQuery().split(",");
            query.setType(Integer.parseInt(strings[0]));
            query.setNum(Integer.parseInt(strings[1]));
        }
        if (query.getCode() != null && !"".equals(query.getCode().trim())) {
            query.setCode("%" + query.getCode() + "%");
        }
        if (query.getName() != null && !"".equals(query.getName().trim())) {
            query.setName("%" + query.getName() + "%");
        }
        List<CollectDto> dtos = new ArrayList<>();
        dtos.add(taskDao.findProjectCount(query));
        dtos.add(taskDao.findProjectDelUnCompCount(query));
        dtos.add(taskDao.findProjectDelCompCount(query));
        dtos.add(taskDao.findDeptUndoneCount(query));
        dtos.add(taskDao.findDeptdoneCount(query));
        return dtos;
    }

    /**
     * 工艺看板
     */
    @Override
    public BaseDataTableModel<DeptTaskDto> findProcessList(TaskQuery query) {
        if (query.getCode() != null && !"".equals(query.getCode().trim())) {
            query.setCode("%" + query.getCode() + "%");
        }
        if (query.getName() != null && !"".equals(query.getName().trim())) {
            query.setName("%" + query.getName() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);

        List<DeptTaskDto> dtos = taskDao.findProcessList(query);
        Integer count = taskDao.findProcessCount(query);
        BaseDataTableModel<DeptTaskDto> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(dtos);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }

    /**
     * 生产看板
     */
    @Override
    public BaseDataTableModel<DeptTaskDto> findProduceList(TaskQuery query) {
        if (query.getCode() != null && !"".equals(query.getCode().trim())) {
            query.setCode("%" + query.getCode() + "%");
        }
        if (query.getName() != null && !"".equals(query.getName().trim())) {
            query.setName("%" + query.getName() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);

        List<DeptTaskDto> dtos = taskDao.findProduceList(query);
        Integer count = taskDao.findProduceCount(query);
        BaseDataTableModel<DeptTaskDto> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(dtos);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }
}
