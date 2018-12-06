package com.zw.design.modules.build.create.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.distribute.entity.DeptTask;
import com.zw.design.modules.build.distribute.entity.ProduceTask;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distribute.form.*;
import com.zw.design.modules.build.distribute.query.ProjectQuery;

import javax.servlet.http.HttpServletResponse;

public interface ProjectService {

    BaseDataTableModel<Project> findProjectsForNotSendByCriteria(ProjectQuery query);

    Project saveProject(Project project);

    Boolean sendTask(ProjectSendForm form);

    BaseDataTableModel<Project> findProjectsForSendByCriteria(ProjectQuery query);

    Project updateStatus(Integer id, Integer status, String comment);

    Project findProjectByQuery(ProjectQuery query);

    Project findByCode(String code);

    Project findProjectById(Integer id);

    void delProject(Integer id);

    DeptTask cancelDept(Integer id);

    ProduceTask cancelProduce(Integer id);

    Project updateProject(ProjectForm project);

    void saveImage(Integer id, String fileName, String path, Integer type);

    void download(HttpServletResponse response, Integer[] id, String code);

    void delFile(Integer[] ids);

    Integer findTaskFileCount(Integer id);

    Integer findSignFileCount(Integer id);

    Integer findContractFileCount(Integer id);

    void updateReceiverStatus(Integer receiverId);

    Integer findAcceptFileCount(Integer id);
}
