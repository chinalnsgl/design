package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.DeptTask;
import com.zw.design.entity.ProduceTask;
import com.zw.design.entity.Project;
import com.zw.design.form.ProjectForm;
import com.zw.design.form.ProjectSendForm;
import com.zw.design.query.ProjectQuery;

public interface ProjectService {

    DataTablesCommonDto<Project> findProjectsForNotSendByCriteria(ProjectQuery query);

    Project saveProject(Project project);

    Boolean sendTask(ProjectSendForm form);

    DataTablesCommonDto<Project> findProjectsForSendByCriteria(ProjectQuery query);

    Project updateStatus(Integer id, Integer status, String comment);

    Project findProjectByQuery(ProjectQuery query);

    Project findByCode(String code);

    Project findProjectById(Integer id);

    void delProject(Integer id);

    DeptTask cancelDept(Integer id);

    ProduceTask cancelProduce(Integer id);

    Project updateProject(ProjectForm project);
}
