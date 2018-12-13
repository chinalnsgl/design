package com.zw.design.modules.build.create.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.entity.DeptTask;
import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import com.zw.design.modules.build.distributedesigntask.query.DistributeDesignTaskQuery;

import javax.servlet.http.HttpServletResponse;

public interface ProjectService {

    // 按code查询项目
    Project findByCode(String code);

    // 保存项目
    Project saveProject(Project project);

    // 删除项目
    void delProject(Integer id);

//    Boolean sendTask(ProjectSendForm form);

    BaseDataTableModel<Project> findProjectsForSendByCriteria(DistributeDesignTaskQuery query);

    Project updateStatus(Integer id, Integer status, String comment);

    Project findProjectByQuery(DistributeDesignTaskQuery query);



    Project findProjectById(Integer id);

    DeptTask cancelDept(Integer id);

    ProduceTask cancelProduce(Integer id);

//    Project updateProject(ProjectForm project);

    void saveImage(Integer id, String fileName, String path, Integer type);

    void download(HttpServletResponse response, Integer[] id, String code);

    void delFile(Integer[] ids);

    Integer findTaskFileCount(Integer id);

    Integer findSignFileCount(Integer id);

    Integer findContractFileCount(Integer id);

    void updateReceiverStatus(Integer receiverId);

    Integer findAcceptFileCount(Integer id);


}
