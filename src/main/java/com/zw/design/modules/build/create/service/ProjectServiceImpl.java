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

}
