package com.zw.design.modules.build.create.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.distributedesigntask.entity.*;
import com.zw.design.modules.build.distributedesigntask.form.*;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.build.distributedesigntask.query.ProjectQuery;
import com.zw.design.modules.build.distributedesigntask.repository.DeptTaskRepository;
import com.zw.design.modules.build.distributedesigntask.repository.ImageRepository;
import com.zw.design.modules.build.distributedesigntask.repository.ProduceTaskRepository;
import com.zw.design.modules.build.distributedesigntask.repository.ReceiverRepository;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.utils.Const;
import com.zw.design.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipOutputStream;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private DeptTaskRepository deptTaskRepository;
    @Autowired
    private ProduceTaskRepository produceTaskRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ReceiverRepository receiverRepository;

    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<Project> findProjectsByQuery(ProjectQuery query) {
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

    // 按code查询项目
    @Override
    public Project findByCode(String code) {
        return projectRepository.findByCode(code);
    }

    // 保存项目
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project saveProject(Project project) {
        logService.saveLog("创建项目：" ,project.getName() + " 项目号：" + project.getCode());
        return projectRepository.save(project);
    }

    // 删除项目
    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public void delProject(Integer id) {
        Project project = findProjectById(id);
        logService.saveLog("删除项目：" , project.getName() + " 项目号：" + project.getCode());
        projectRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public DeptTask cancelDept(Integer id) {
        DeptTask deptTask = deptTaskRepository.findById(id).get();
        if (deptTask.getStatus() == 1) {
            deptTask.setStatus(0);
            deptTask.setStartTime(null);
        }
        if (deptTask.getStatus() == 2) {
            deptTask.setEndTime(null);
            deptTask.setStatus(1);
        }
        Project project = deptTask.getProject();
        if (project.getStatus() == 4) {
            project.setStatus(2);
            projectRepository.save(project);
        }
        return deptTaskRepository.saveAndFlush(deptTask);
    }

    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public ProduceTask cancelProduce(Integer id) {
        ProduceTask produceTask = produceTaskRepository.findById(id).get();
        if (produceTask.getStatus() == 1) {
            produceTask.setStatus(0);
            produceTask.setStartTime(null);
        }
        if (produceTask.getStatus() == 2) {
            produceTask.setEndTime(null);
            produceTask.setStatus(1);
            if (produceTask.getProduceNum() == 2 || produceTask.getProduceNum() == 9) {
                produceTask.setStatus(0);
                produceTask.setStartTime(null);
            }
        }
        Project project = produceTask.getProject();
        if (project.getStatus() == 4) {
            project.setStatus(2);
            projectRepository.saveAndFlush(project);
        }
        return produceTaskRepository.saveAndFlush(produceTask);
    }

    @Override
    public Project findProjectById(Integer id) {
        return projectRepository.findById(id).get();
    }



    @Override
    public Project findProjectByQuery(ProjectQuery query) {

        Optional<Project> one = projectRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.equal(root.get("name").as(String.class), query.getNameQuery()));
            }
            if (null != query.getCodeQuery() && !"".equals(query.getCodeQuery())) {
                list.add(criteriaBuilder.equal(root.get("code").as(String.class), query.getCodeQuery()));
            }
            list.add(criteriaBuilder.greaterThan(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        });
        return one.orElse(null);
    }

    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project updateStatus(Integer id, Integer status, String comment) {
        Project p = projectRepository.findById(id).get();
        if (status < 0) {
            p.setStatus(p.getPreStatus());
        } else {
            p.setPreStatus(p.getStatus());
            p.setStatus(status);
        }
        p.setComment(comment);
        projectRepository.save(p);
        return p;
    }

    @Override
    public BaseDataTableModel<Project> findProjectsForSendByCriteria(ProjectQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Project> projectPage = projectRepository.findAll((Specification<Project>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            if( null!=query.getCodeQuery()&&!"".equals(query.getCodeQuery())){
                list.add(criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCodeQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 2));
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





    @Override
    @CacheEvict(value = "projects",allEntries = true)
    public Project updateProject(ProjectForm project) {
        Project p = projectRepository.findById(project.getId()).get();
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
        List<DeptTask> mechineList = new ArrayList<>();
        List<DeptTask> hypreList = new ArrayList<>();
        List<DeptTask> electricList = new ArrayList<>();
        List<DeptTask> softwareList = new ArrayList<>();
        /*for (Task deptTask : p.getTasks()) {
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
        }*/

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
        /*p.getDeptTasks().removeAll(p.getDeptTasks());
        p.getDeptTasks().addAll(mechineList);
        p.getDeptTasks().addAll(hypreList);
        p.getDeptTasks().addAll(electricList);
        p.getDeptTasks().addAll(softwareList);*/
        return projectRepository.save(p);
    }

    @Override
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
    }

    @Override
    public void saveImage(Integer id, String fileName, String path, Integer type) {
        if (imageRepository.countByUrl(path) > 0) {
            return;
        }
        Project project = projectRepository.findById(id).get();
        Image image = new Image();
        image.setName(fileName);
        image.setUrl(path);
        image.setType(type);
        image.setUploadTime(new Date());
        image.setProject(project);
        imageRepository.save(image);
    }

    @Override
    public void download(HttpServletResponse response, Integer[] id, String code) {
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

    @Override
    public void delFile(Integer[] ids) {
        for (Integer id : ids) {
            Image image = imageRepository.findById(id).get();
            File file = new File(image.getUrl());
            if (file.exists()) {
                file.delete();
            }
            imageRepository.deleteById(id);
        }
    }

    @Override
    public Integer findTaskFileCount(Integer id) {
        return imageRepository.countByProject_IdAndType(id, 1);
    }

    @Override
    public Integer findSignFileCount(Integer id) {
        return imageRepository.countByProject_IdAndType(id, 2);
    }

    @Override
    public Integer findContractFileCount(Integer id) {
        return imageRepository.countByProject_IdAndType(id, 3);
    }

    @Override
    public Integer findAcceptFileCount(Integer id) {
        return imageRepository.countByProject_IdAndType(id, 4);
    }

    @Override
    public void updateReceiverStatus(Integer receiverId) {
        Receiver receiver = receiverRepository.findById(receiverId).get();
        receiver.setStatus(2);
        receiverRepository.save(receiver);
    }
}
