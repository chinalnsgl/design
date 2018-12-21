package com.zw.design.modules.build.create.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/build/create")
public class ProjectController {

    private String prefix = "build/create";
    @Autowired
    private ProjectService projectService;

    @Value("${upload.path}")
    private String uploadPath;

    // 创建项目页面
    @GetMapping("/page")
    @RequiresPermissions({"create:create"})
    public String create() {
        return prefix + "/create";
    }

    // 项目编号唯一验证
    @ResponseBody
    @PostMapping("/checkCodeUnique")
    @RequiresPermissions({"create:create"})
    public BaseValidResponse checkCodeUnique(@RequestParam("code") String code, @RequestParam(value = "id",required = false) Integer id) {
        Project project = projectService.findByCode(code);
        if (id == null) {
            return BaseValidResponse.toResponse(project);
        }
        if (project == null || project.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }

    // 创建项目
    @PostMapping("/create")
    @RequiresPermissions({"create:create"})
    public String create(Project project) {
        project.setCode(project.getCode().trim());
        projectService.saveProject(project);
        return "redirect:/build/ddt/page";
    }

    // 删除项目
    @ResponseBody
    @PostMapping("/del")
    @RequiresPermissions({"create:del"})
    public BaseResponse create(@RequestParam("id")Integer id) {
        projectService.delProject(id);
        return BaseResponse.STATUS_200;
    }

}
