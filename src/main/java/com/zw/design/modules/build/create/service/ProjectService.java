package com.zw.design.modules.build.create.service;

import com.zw.design.modules.build.create.entity.Project;

public interface ProjectService {

    // 按code查询项目
    Project findByCode(String code);

    // 保存项目
    Project saveProject(Project project);

    // 删除项目
    void delProject(Integer id);

    // 按ID查询项目
    Project findProjectById(Integer id);

}
