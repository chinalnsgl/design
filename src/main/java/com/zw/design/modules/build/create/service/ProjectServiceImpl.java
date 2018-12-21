package com.zw.design.modules.build.create.service;

import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LogService logService;

    // 按code查询项目
    @Override
    public Project findByCode(String code) {
        return projectRepository.findByCode(code);
    }

    // 保存项目
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project saveProject(Project project) {
        logService.saveLog("创建项目" ,project.getName() + " 项目号：" + project.getCode());
        return projectRepository.save(project);
    }

    // 删除项目
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public void delProject(Integer id) {
        Project project = findProjectById(id);
        logService.saveLog("删除项目" , project.getName() + " 项目号：" + project.getCode());
        projectRepository.deleteById(id);
    }

    // 按ID查询项目
    @Override
    public Project findProjectById(Integer id) {
        return projectRepository.findById(id).get();
    }


    /*@Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project updateProject(ProjectForm project) {
        Project p = projectRepository.findById(project.getId()).get();
        p.setName(project.getName());
        p.setDemander(project.getDemander());
        p.setAddress(project.getAddress());
        p.setCodeSpecial(project.getCodeSpecial());
        p.setComment(project.getComment());
        *//*if (p.getOrderNo() != 2) {
            if (project.getOrderNo() == null) {
                p.setOrderNo(0);
            } else {
                p.setOrderNo(1);
            }
        }*//*
        p.setNum(project.getNum());
        p.setPlanTime(project.getPlanTime());
        List<DeptTask> mechineList = new ArrayList<>();
        List<DeptTask> hypreList = new ArrayList<>();
        List<DeptTask> electricList = new ArrayList<>();
        List<DeptTask> softwareList = new ArrayList<>();
        *//*for (Task deptTask : p.getTasks()) {
            switch (deptTask.getDepartmentType()) {
                case 1:
                    mechineList.add(deptTask);
                    break;
                case 2:
                    hypreList.add(deptTask);
                    break;
                case 3:
                    electricList.add(deptTask);
                    break;
                case 4:
                    softwareList.add(deptTask);
                    break;
            }
        }*//*

        if (project.getMachineNo() != null) {
            if (mechineList.size() > 0) {
                for (DeptTask deptTask : mechineList) {
                    if (deptTask.getStatus() >= 100) {
                        deptTask.setStatus(deptTask.getStatus() - 100);
                    }
                    deptTask.setDepartmentNum(project.getMachineNo());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    DeptTask deptTask = new DeptTask();
                    deptTask.setDepartmentType(1);
                    deptTask.setStepNo(i+1);
                    deptTask.setDepartmentNum(project.getMachineNo());
                    deptTask.setStepName(Const.MACHINE_STEP_NAME[i]);
                    deptTask.setProject(p);
                    mechineList.add(deptTask);
                }
                deptTaskRepository.saveAll(mechineList);
            }
        } else {
            if (mechineList.size() > 0) {
                boolean flag = true;
                for (DeptTask deptTask : mechineList) {
                    if(deptTask.getStatus() > 0){
                        flag = false;
                    }
                    deptTask.setStatus(deptTask.getStatus() + 100);
                }
                if (flag) {
                    deptTaskRepository.deleteAll(mechineList);
                    mechineList.clear();
                }
            }
        }
        if (project.getHypreNo() != null) {
            if (hypreList.size() > 0) {
                for (DeptTask deptTask : hypreList) {
                    if (deptTask.getStatus() >= 100) {
                        deptTask.setStatus(deptTask.getStatus() - 100);
                    }
                    deptTask.setDepartmentNum(project.getHypreNo());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    DeptTask deptTask = new DeptTask();
                    deptTask.setDepartmentType(2);
                    deptTask.setStepNo(i+1);
                    deptTask.setDepartmentNum(project.getHypreNo());
                    deptTask.setStepName(Const.HYPRE_STEP_NAME[i]);
                    deptTask.setProject(p);
                    hypreList.add(deptTask);
                }
                deptTaskRepository.saveAll(hypreList);
            }
        } else {
            if (hypreList.size() > 0) {
                boolean flag = true;
                for (DeptTask deptTask : hypreList) {
                    if(deptTask.getStatus() > 0){
                        flag = false;
                    }
                    deptTask.setStatus(deptTask.getStatus() + 100);
                }
                if (flag) {
                    deptTaskRepository.deleteAll(hypreList);
                    hypreList.clear();
                }
            }
        }
        if (project.getElectricNo() != null) {
            if (electricList.size() > 0) {
                for (DeptTask deptTask : electricList) {
                    if (deptTask.getStatus() >= 100) {
                        deptTask.setStatus(deptTask.getStatus() - 100);
                    }
                    deptTask.setDepartmentNum(project.getElectricNo());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    DeptTask deptTask = new DeptTask();
                    deptTask.setDepartmentType(3);
                    deptTask.setStepNo(i+1);
                    deptTask.setDepartmentNum(project.getElectricNo());
                    deptTask.setStepName(Const.ELECTRIC_STEP_NAME[i]);
                    deptTask.setProject(p);
                    electricList.add(deptTask);
                }
                deptTaskRepository.saveAll(electricList);
            }
        } else {
            if (electricList.size() > 0) {
                boolean flag = true;
                for (DeptTask deptTask : electricList) {
                    if(deptTask.getStatus() > 0){
                        flag = false;
                    }
                    deptTask.setStatus(deptTask.getStatus() + 100);
                }
                if (flag) {
                    deptTaskRepository.deleteAll(electricList);
                    electricList.clear();
                }
            }
        }
        if (project.getSoftwareNo() != null) {
            if (softwareList.size() > 0) {
                for (DeptTask deptTask : softwareList) {
                    if (deptTask.getStatus() >= 100) {
                        deptTask.setStatus(deptTask.getStatus() - 100);
                    }
                    deptTask.setDepartmentNum(project.getSoftwareNo());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    DeptTask deptTask = new DeptTask();
                    deptTask.setDepartmentType(4);
                    deptTask.setStepNo(i+1);
                    deptTask.setDepartmentNum(project.getSoftwareNo());
                    deptTask.setStepName(Const.SOFTWARE_STEP_NAME[i]);
                    deptTask.setProject(p);
                    softwareList.add(deptTask);
                }
                deptTaskRepository.saveAll(softwareList);
            }
        } else {
            if (softwareList.size() > 0) {
                boolean flag = true;
                for (DeptTask deptTask : softwareList) {
                    if(deptTask.getStatus() > 0){
                        flag = false;
                    }
                    deptTask.setStatus(deptTask.getStatus() + 100);
                }
                if (flag) {
                    deptTaskRepository.deleteAll(softwareList);
                    softwareList.clear();
                }
            }
        }
        *//*p.getDeptTasks().removeAll(p.getDeptTasks());
        p.getDeptTasks().addAll(mechineList);
        p.getDeptTasks().addAll(hypreList);
        p.getDeptTasks().addAll(electricList);
        p.getDeptTasks().addAll(softwareList);*//*
        return projectRepository.save(p);
    }*/

    /*@Override
    @Transactional
    @CacheEvict(value = "projects",allEntries = true)
    public Boolean sendTask(ProjectSendForm form) {
        Project p = projectRepository.findById(form.getProjectId()).get();
        p.setStatus(2);
        projectRepository.save(p);
        List<DeptTask> list = new ArrayList<>();
        if (form.getMachineNo() != null) {
            for (int i = 0; i < 4; i++) {
                DeptTask deptTask = new DeptTask();
                deptTask.setDepartmentType(1);
                deptTask.setStepNo(i+1);
                deptTask.setDepartmentNum(form.getMachineNo());
                deptTask.setStepName(Const.MACHINE_STEP_NAME[i]);
                deptTask.setProject(p);
                list.add(deptTask);
            }
        }
        if (form.getHypreNo() != null) {
            for (int i = 0; i < 4; i++) {
                DeptTask deptTask = new DeptTask();
                deptTask.setDepartmentType(2);
                deptTask.setStepNo(i+1);
                deptTask.setDepartmentNum(form.getHypreNo());
                deptTask.setStepName(Const.HYPRE_STEP_NAME[i]);
                deptTask.setProject(p);
                list.add(deptTask);
            }
        }
        if (form.getElectricNo() != null) {
            for (int i = 0; i < 4; i++) {
                DeptTask deptTask = new DeptTask();
                deptTask.setDepartmentType(3);
                deptTask.setStepNo(i+1);
                deptTask.setDepartmentNum(form.getElectricNo());
                deptTask.setStepName(Const.ELECTRIC_STEP_NAME[i]);
                deptTask.setProject(p);
                list.add(deptTask);
            }
        }
        if (form.getSoftwareNo() != null) {
            for (int i = 0; i < 4; i++) {
                DeptTask deptTask = new DeptTask();
                deptTask.setDepartmentType(4);
                deptTask.setStepNo(i+1);
                deptTask.setDepartmentNum(form.getSoftwareNo());
                deptTask.setStepName(Const.SOFTWARE_STEP_NAME[i]);
                deptTask.setProject(p);
                list.add(deptTask);
            }
        }
        deptTaskRepository.saveAll(list);
        List<ProduceTask> produceTasks = new ArrayList<>();
        for (int i = 0; i < Const.PRODUCE_TASK_STEP_NAME.length; i++) {
            ProduceTask task = new ProduceTask();
            if (i == 0) {
                task.setStartTime(new Date());
                task.setEndTime(new Date());
                task.setStatus(2);
            }
            task.setProduceName(Const.PRODUCE_TASK_STEP_NAME[i]);

            task.setProduceNum(i + 1);
            task.setProject(p);
            produceTasks.add(task);
        }
        produceTaskRepository.saveAll(produceTasks);
        return true;
    }*/


}
