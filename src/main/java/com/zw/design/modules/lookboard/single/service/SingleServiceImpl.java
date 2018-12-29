package com.zw.design.modules.lookboard.single.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.baseinfosetting.sectiontype.entity.SectionType;
import com.zw.design.modules.baseinfosetting.sectiontype.repository.SectionTypeRepository;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import com.zw.design.modules.build.distributedesigntask.repository.TaskRepository;
import com.zw.design.modules.lookboard.single.entity.Image;
import com.zw.design.modules.lookboard.single.entity.Message;
import com.zw.design.modules.lookboard.single.entity.Receiver;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import com.zw.design.modules.lookboard.single.mapper.SingleMapper;
import com.zw.design.modules.lookboard.single.query.SingleQuery;
import com.zw.design.modules.lookboard.single.repository.ImageRepository;
import com.zw.design.modules.lookboard.single.repository.MessageRepository;
import com.zw.design.modules.lookboard.single.repository.ReceiverRepository;
import com.zw.design.modules.lookboard.single.repository.TaskEmployeeRepository;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.repository.SysUserRepository;
import com.zw.design.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
public class SingleServiceImpl implements SingleService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private SingleMapper singleMapper;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private ReceiverRepository receiverRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private SectionTypeRepository sectionTypeRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TaskEmployeeRepository taskEmployeeRepository;

    @Value("${upload.path}")
    private String uploadPath;

    // 按ID查询项目
    @Override
    public Project findProjectById(Integer id) {
        return projectRepository.findById(id).get();
    }

    // 按code查询项目
    @Override
    public Project findProjectByCode(String code) {
        return projectRepository.findByCodeAndStatus(code, 2);
    }

    // 查询科室类型
    @Override
    public List<SectionType> findSectionTypeByStatus(Integer status) {
        return sectionTypeRepository.findByStatus(status);
    }

    // 修改项目状态
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project updateStatus(Integer id, Integer status, String comment) {
        Project project = projectRepository.findById(id).get();
        logService.saveLog(( status == 0 ? "取消项目" : "恢复项目："),  project.getName());
        project.setPreStatus(project.getStatus());
        project.setStatus(status);
        project.setComment(comment);
        project = projectRepository.save(project);
        return project;
    }

    // 项目任务列表
    @Override
    public BaseDataTableModel<Task> findTaskByQuery(SingleQuery query) {
        List<Task> tasks = taskRepository.findAll((Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("project").get("id"), query.getIdQuery()));
            list.add(criteriaBuilder.isNull(root.get("section")));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, Sort.by("orderNo"));
        BaseDataTableModel<Task> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(tasks);
        baseDataTableModel.setRecordsTotal(tasks.size());
        baseDataTableModel.setRecordsFiltered(tasks.size());
        return baseDataTableModel;
    }

    // 修改任务状态
    @Override
    public Task updateTaskStatus(Integer id, Integer status) {
        Task task = taskRepository.findById(id).get();
        logService.saveLog(( status == 2 ? "不需要" : "需要："),  "【项目：" + task.getProject().getName() + "】【任务：" + task.getAlias() + "】");
        task.setStatus(status);
        task = taskRepository.save(task);
        updateProject(task.getProject());
        return task;
    }

    // 修改项目
    @Override
    public Project updateProject(Project project, Integer[] sectionId) {
        Project p = projectRepository.findById(project.getId()).get();
        logService.saveLog("修改项目", p, project);
        p.setName(project.getName());
        p.setDemander(project.getDemander());
        p.setAddress(project.getAddress());
        p.setCodeSpecial(project.getCodeSpecial());
        p.setComment(project.getComment());
        /*if (p.getOrderNo() != 2) {
            if (project.getOrderNo() == null) {
                p.setOrderNo(0);
            } else {
                p.setOrderNo(1);
            }
        }*/
        p.setNum(project.getNum());
        p.setPlanTime(project.getPlanTime());
        // 生成修改后科室字符串
        StringBuilder designSectionNames = new StringBuilder();
        for (Integer id : sectionId) {
            if (id != null) {
                Section section = sectionRepository.findById(id).get();
                designSectionNames.append(section.getName() + ",");
            }
        }
        String newSectionNames = designSectionNames.toString().substring(0, designSectionNames.lastIndexOf(","));
        // 修改前后科室字符串不同才需要处理
        if (!newSectionNames.equals(p.getDesignDepts())) {
            p.setDesignDepts(newSectionNames);
            for (Task task : p.getTasks()) {
                if (task.getTaskName().getId() == 2 || task.getTaskName().getId() == 4) {
                    task.setDesignDepts(p.getDesignDepts());
                    taskRepository.save(task);
                }
            }
            // 求新科室集合
            List<Section> newSection = new ArrayList<>();
            for (String str : newSectionNames.split(",")) {
                newSection.add(sectionRepository.findByNameAndStatus(str, 1));
            }
            // 循环处理新科室
            for (Section section : newSection) {
                boolean isCreate = true; // 是否新建任务
                // 遍历项目所有任务
                for (Task task : p.getTasks()) {
                    // 存在此科室不必新建并且跳过循环
                    if (task.getSection() != null && task.getSection().getId() == section.getId()) {
                        isCreate = false;
                        break;
                    }
                    // 不存在此科室，但存在相同科室类型，修改任务科室与状态
                    if (task.getSection() != null && task.getSection().getId() != section.getId() && task.getSection().getSectionType().getId() == section.getSectionType().getId()) {
                        task.setSection(section);
                        task.setStatus(1);
                        taskRepository.save(task);
                        isCreate = false;
                    }
                }
                // 需要创建的任务
                if (isCreate) {
                    for (TaskName taskName : section.getSectionType().getTaskNames()) {
                        Task task = new Task();
                        task.setAlias(taskName.getName());
                        task.setSection(section);
                        task.setTaskName(taskName);
                        task.setOrderNo(taskName.getOrderNo());
                        task.setProject(project);
                        taskRepository.save(task);
                    }
                }
            }
            // 处理需要删除的任务
            for (Task task : p.getTasks()) {
                boolean isDelete = true;
                if (task.getSection() != null) {
                    for (Section section : newSection) {
                        if (task.getSection().getId() == section.getId()) {
                            isDelete = false;
                            break;
                        }
                    }
                } else {
                    isDelete = false;
                }
                if (isDelete) {
                    task.setStatus(0);
                    taskRepository.save(task);
                }
            }
        }
        p = projectRepository.save(p);
        updateProject(p);
        return p;
    }

    // 编辑任务
    @Override
    public Task editTask(Task task) {
        Task t = taskRepository.findById(task.getId()).get();
        logService.saveLog("编辑任务", t, task);
        t.setComment(task.getComment());
        t.setContractNo(task.getContractNo());
//        t.setPrincipal(task.getPrincipal());
        t.setPlanFinishTime(task.getPlanFinishTime());
        // 单独处理签协议和基础条件任务
        if (t.getTaskName().getId() == 2 || t.getTaskName().getId() == 12) {
            if (task.getCompStatus() != null && task.getCompStatus() != 0) {
                t.setCompStatus(2);
                t.setStartTime(new Date());
                t.setEndTime(new Date());
            }
        } else {
            if (task.getCompStatus() != null && task.getCompStatus() != 0) {
                t.setCompStatus(task.getCompStatus());
            }
            if (task.getCompStatus() != null && task.getCompStatus() == 1) {
                t.setStartTime(new Date());
            } else if (task.getCompStatus() != null && task.getCompStatus() == 2) {
                t.setEndTime(new Date());
            }
        }
        // 判断并设置项目表完成状态
        t = taskRepository.saveAndFlush(t);
        Project p = projectRepository.findById(t.getProject().getId()).get();
        updateProject(p);
        return t;
    }

    // 按任务ID查询任务负责人表格数据模型
    @Override
    public BaseDataTableModel<TaskEmployee> findTaskEmployeeByTaskId(SingleQuery query) {
        List<TaskEmployee> employees = taskEmployeeRepository.findAll((Specification<TaskEmployee>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("task").get("id"), query.getIdQuery()));
            list.add(criteriaBuilder.equal(root.get("status"), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        });
        BaseDataTableModel<TaskEmployee> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(employees);
        baseDataTableModel.setRecordsTotal(employees.size());
        baseDataTableModel.setRecordsFiltered(employees.size());
        return baseDataTableModel;
    }

    // 添加负责人
    @Override
    public TaskEmployee saveEmployee(TaskEmployee taskEmployee) {
        Task task = taskRepository.findById(taskEmployee.getTask().getId()).get();
        Project project = projectRepository.findById(task.getProject().getId()).get();
        logService.saveLog("添加负责人" , "【项目：" + project.getName() + "，任务名：" + task.getTaskName() + "负责人：" + taskEmployee.getEmpName() + "】");
        return taskEmployeeRepository.save(taskEmployee);
    }

    // 修改负责人
    @Override
    public TaskEmployee updateEmployee(TaskEmployee taskEmployee) {
        TaskEmployee emp = taskEmployeeRepository.findById(taskEmployee.getId()).get();
        logService.saveLog("修改负责人", emp, taskEmployee);
        emp.setEmpName(taskEmployee.getEmpName());
        emp.setContent(taskEmployee.getContent());
        emp.setStartTime(taskEmployee.getStartTime());
        emp.setEndTime(taskEmployee.getEndTime());
        emp.setDuration(taskEmployee.getDuration());
        return taskEmployeeRepository.save(emp);
    }

    // 修改负责人状态
    @Override
    public TaskEmployee updateEmployeeStatus(Integer id, int status) {
        TaskEmployee taskEmployee = taskEmployeeRepository.findById(id).get();
        logService.saveLog("删除负责人" , taskEmployee.getEmpName());
        taskEmployee.setStatus(status);
        return taskEmployeeRepository.save(taskEmployee);
    }

    // 撤消任务
    @Override
    public Task cancelTask(Integer id) {
        Task task = taskRepository.findById(id).get();
        logService.saveLog("撤消任务进度", "任务名：" + task.getAlias() + " 】");
        if (task.getCompStatus() == 1) {
            task.setCompStatus(0);
            task.setStartTime(null);
        }
        if (task.getCompStatus() == 2) {
            task.setEndTime(null);
            task.setCompStatus(1);
            if (task.getTaskName().getId() == 2 || task.getTaskName().getId() == 12) {
                task.setCompStatus(0);
                task.setStartTime(null);
            }
        }
        task = taskRepository.saveAndFlush(task);
        updateProject(task.getProject());
        return task;
    }

    // 修改项目和各种完成状态
    private void updateProject(Project project) {
        float projectTasksCompleteStatus = singleMapper.findProjectTasksCompleteStatus(project.getId());
        if (projectTasksCompleteStatus == 0) {
            project.setProjectTaskStatus(0);
        } else if (projectTasksCompleteStatus == 2) {
            project.setProjectTaskStatus(2);
        } else {
            project.setProjectTaskStatus(1);
        }
        float designTasksCompleteStatus = singleMapper.findDesignTasksCompleteStatus(project.getId());
        if (designTasksCompleteStatus == 0) {
            project.setDesignTaskStatus(0);
        } else if (designTasksCompleteStatus == 2) {
            project.setDesignTaskStatus(2);
            project.setDesignCompleteTime(new Date());
        } else {
            project.setDesignTaskStatus(1);
        }
        float sectionTasksCompleteStatus = singleMapper.findSectionTasksCompleteStatus(project.getId());
        if (sectionTasksCompleteStatus == 0) {
            project.setSectionTaskStatus(0);
        } else if (sectionTasksCompleteStatus == 2) {
            project.setSectionTaskStatus(2);
            project.setSectionCompleteTime(new Date());
        } else {
            project.setSectionTaskStatus(1);
        }
        float processTasksCompleteStatus = singleMapper.findProcessTasksCompleteStatus(project.getId());
        if (processTasksCompleteStatus == 0) {
            project.setProcessTaskStatus(0);
        } else if (processTasksCompleteStatus == 2) {
            project.setProcessTaskStatus(2);
            project.setProcessCompleteTime(new Date());
        } else {
            project.setProcessTaskStatus(1);
        }
        float produceTasksCompleteStatus = singleMapper.findProduceTasksCompleteStatus(project.getId());
        if (produceTasksCompleteStatus == 0) {
            project.setProduceTaskStatus(0);
        } else if (produceTasksCompleteStatus == 2) {
            project.setProduceTaskStatus(2);
            project.setProduceCompleteTime(new Date());
        } else {
            project.setProduceTaskStatus(1);
        }
        if (projectTasksCompleteStatus == 2 && designTasksCompleteStatus == 2 && processTasksCompleteStatus == 2 && produceTasksCompleteStatus == 2) {
            project.setStatus(4);
            project.setCompleteTime(new Date());
        } else {
            project.setStatus(2);
            project.setCompleteTime(null);
        }
        projectRepository.saveAndFlush(project);
    }

    // 发送消息
    @Override
    public Message sendMessage(Message message, Integer projectId, List<String> users) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        message.setUser(user);
        Project project = projectRepository.findById(projectId).get();
        project.setId(projectId);
        message.setProject(project);
        message.setUsers(StringUtils.join(users.toArray(), ","));
        message = messageRepository.save(message);
        for (String s : users) {
            SysUser u = sysUserRepository.findByName(s);
            Receiver receiver = new Receiver();
            receiver.setMessage(message);
            receiver.setProject(project);
            receiver.setUser(u);
            receiverRepository.save(receiver);
        }
        logService.saveLog("发消息", "【项目：" + project.getName() + "】 " + message.getContent());
        return message;
    }

    // 修改未读消息状态
    @Override
    public void updateReceiverStatus(Integer receiverId) {
        Receiver receiver = receiverRepository.findById(receiverId).get();
        receiver.setStatus(2);
        receiverRepository.save(receiver);
    }

    // 按ID查询任务
    @Override
    public Task findTaskById(Integer taskId) {
        return taskRepository.findById(taskId).get();
    }

    // 保存文件
    @Override
    public void saveImage(Integer projectId, String fileName, String s, Task task) {
        if (imageRepository.countByUrl(s) > 0) {
            return;
        }
        Project project = projectRepository.findById(projectId).get();
        logService.saveLog("上传文件", "【项目名：" + project.getName() + "】");
        Image image = new Image();
        image.setName(fileName);
        image.setUrl(s);
        image.setTask(task);
        image.setType(task.getTaskName().getId());
        image.setUploadTime(new Date());
        image.setProject(project);
        imageRepository.save(image);
    }

    // 下载文件
    @Override
    public void download(HttpServletResponse response, Integer[] id, String code) {
        logService.saveLog("下载文件","数量" + id.length + "  【项目号：" + code + "】");
        if (id.length == 1) {
            Image image = imageRepository.findById(id[0]).get();
            File file = new File(image.getUrl());
            FileUtils.downloadFile(file, response);
        } else {
            List<File> list = new ArrayList<>();
            Image image;
            for (Integer integer : id) {
                image = imageRepository.findById(integer).get();
                list.add(new File(image.getUrl()));
            }
            try {
                // 要压缩的文件路径
                File file = new File(uploadPath + code + ".zip");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                //创建文件输出流
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // ZipOutputStream输出流转换
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                // 接收文件集合、压缩流
                FileUtils.zipFileAll(list, zipOutputStream);
                zipOutputStream.close();
                fileOutputStream.close();
                FileUtils.downloadZip(file, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 删除文件
    @Override
    public void delFile(Integer[] ids) {

        for (Integer id : ids) {
            Image image = imageRepository.findById(id).get();
            logService.saveLog("删除文件", "【项目：" + image.getProject().getName() + "】 【任务：" + image.getTask().getAlias() + "】");
            File file = new File(image.getUrl());
            if (file.exists()) {
                file.delete();
            }
            imageRepository.deleteById(id);
        }
    }
}
