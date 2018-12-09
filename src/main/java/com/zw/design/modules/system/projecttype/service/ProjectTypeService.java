package com.zw.design.modules.system.projecttype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.projecttype.entity.ProjectType;
import com.zw.design.modules.system.projecttype.query.ProjectTypeQuery;

import java.util.List;

public interface ProjectTypeService {

    // 按条件查询所有项目类型表格模型数据
    BaseDataTableModel<ProjectType> findProjectTypeByQuery(ProjectTypeQuery query);

    // 按ID查询项目类型
    ProjectType findById(Integer id);

    // 按名称查询项目类型
    ProjectType findByNameAndStatus(String name, Integer status);

    // 保存项目类型
    ProjectType saveProjectType(ProjectType projectType);

    // 修改项目类型
    ProjectType updateProjectType(ProjectType projectType);

    // 修改项目类型状态
    ProjectType updateProjectTypeStatus(Integer id, Integer status);


    
}
