package com.zw.design.service.impl;

import com.zw.design.dto.CollectDto;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.DeptTaskDto;
import com.zw.design.dto.TaskDto;
import com.zw.design.entity.DeptTask;
import com.zw.design.entity.ProduceTask;
import com.zw.design.entity.Project;
import com.zw.design.mapper.DeptTaskDao;
import com.zw.design.query.ProjectQuery;
import com.zw.design.query.TaskQuery;
import com.zw.design.repository.DeptTaskRepository;
import com.zw.design.repository.ProduceTaskRepository;
import com.zw.design.repository.ProjectRepository;
import com.zw.design.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    /**
     * 保存科室任务
     */
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public DeptTask saveDeptTask(DeptTask deptTask) {
        DeptTask t = deptTaskRepository.findById(deptTask.getId()).get();
        if (deptTask.getAnnotate() != null) {
            t.setAnnotate(deptTask.getAnnotate());
        } else {
            t.setComment(deptTask.getComment());
            t.setPrincipal(deptTask.getPrincipal());
            t.setPlanStartTime(deptTask.getPlanStartTime());
            if (deptTask.getStatus() != null && deptTask.getStatus() != 0) {
                t.setStatus(deptTask.getStatus());
            }
            if (deptTask.getStatus() != null && deptTask.getStatus() == 1) {
                t.setStartTime(new Date());
                Integer num = taskDao.findMaxStepNumByProjectId(t.getProject().getId());
                if (num == null) {
                    t.setStepNum(1);
                } else {
                    t.setStepNum(num + 1);
                }
            } else if (deptTask.getStatus() != null && deptTask.getStatus() == 2) {
                t.setEndTime(new Date());
            }
        }
        t = deptTaskRepository.saveAndFlush(t);
        return t;
    }

    /**
     * 保存生产任务
     */
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public ProduceTask saveProduceTask(ProduceTask produceTask) {
        ProduceTask t = produceTaskRepository.findById(produceTask.getId()).get();
        // 处理批注
        if (produceTask.getAnnotate() != null) {
            t.setAnnotate(produceTask.getAnnotate());
        } else {
            // 单独处理签协议任务
            if (t.getProduceNum() == 2) {
                t.setComment(produceTask.getComment());
                if (t.getStatus() != null && t.getStatus() != 2) {
                    t.setStatus(2);
                    t.setStartTime(new Date());
                    t.setEndTime(new Date());
                }
                return produceTaskRepository.saveAndFlush(t);
            }
            t.setComment(produceTask.getComment());
            t.setPrincipal(produceTask.getPrincipal());
            t.setPlanStartTime(produceTask.getPlanStartTime());
            if (produceTask.getStatus() != null && produceTask.getStatus() != 0) {
                t.setStatus(produceTask.getStatus());
            }
            if (produceTask.getStatus() != null && produceTask.getStatus() == 1) {
                t.setStartTime(new Date());
            } else if (produceTask.getStatus() != null && produceTask.getStatus() == 2) {
                t.setEndTime(new Date());
                if (t.getProduceNum() == 7) {
                    Project p = projectRepository.findById(t.getProject().getId()).get();
                    p.setStatus(4);
                    p.setCompleteTime(new Date());
                    projectRepository.saveAndFlush(p);
                }
            }

        }
        t = produceTaskRepository.saveAndFlush(t);
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
    public DataTablesCommonDto<TaskDto> findByCriteria(ProjectQuery query) {
        if (query.getCode() != null && !"".equals(query.getCode().trim())) {
            query.setCode("%" + query.getCode() + "%");
        }
        if (query.getName() != null && !"".equals(query.getName().trim())) {
            query.setName("%" + query.getName() + "%");
        }
        if (query.getDemander() != null && !"".equals(query.getDemander().trim())) {
            query.setDemander("%" + query.getDemander() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);
        List<TaskDto> dtos = taskDao.findTasks(query);
        Integer count = taskDao.findTasksCount(query);
        DataTablesCommonDto<TaskDto> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(dtos);
        dataTablesCommonDto.setRecordsTotal(count);
        dataTablesCommonDto.setRecordsFiltered(count);
        return dataTablesCommonDto;
    }

    /**
     * 科室看板
     */
    @Override
    @Cacheable(value = "projects",key = "'dept'+#query")
    public DataTablesCommonDto<DeptTaskDto> findTasksByCriteria(TaskQuery query) {
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
        DataTablesCommonDto<DeptTaskDto> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(dtos);
        dataTablesCommonDto.setRecordsTotal(count);
        dataTablesCommonDto.setRecordsFiltered(count);
        return dataTablesCommonDto;
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
}
