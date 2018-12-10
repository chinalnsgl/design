package com.zw.design.modules.baseinfosetting.projecttype.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.baseinfosetting.projecttype.entity.ProjectType;
import com.zw.design.modules.baseinfosetting.projecttype.query.ProjectTypeQuery;
import com.zw.design.modules.baseinfosetting.projecttype.service.ProjectTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/baseinfo")
public class ProjectTypeController {

    private String prefix = "baseinfo";

    @Autowired
    private ProjectTypeService projectTypeService;


    /**
     * 项目类型页面
     */
    @GetMapping("/types")
    @RequiresRoles({"admin"})
    public String types() {
        return prefix + "/type/list";
    }
    /**
     * 获取所有项目类型
     */
    @ResponseBody
    @PostMapping("/type/list")
    @RequiresRoles({"admin"})
    public BaseResponse projectTypeList(ProjectTypeQuery query) {
        BaseDataTableModel<ProjectType> dto = projectTypeService.findProjectTypeByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }


    /**
     * 删除项目类型， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/type/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateProjectTypeStatus(@RequestParam("id")Integer id) {
        ProjectType department = projectTypeService.updateProjectTypeStatus(id, 0);
        return BaseResponse.toResponse(department);
    }

    /**
     * 创建项目类型页面
     */
    @GetMapping("/type/create")
    @RequiresRoles({"admin"})
    public String typeCreate() {
        return  prefix + "/type/create";
    }
    /**
     * 项目类型唯一验证
     */
    @ResponseBody
    @PostMapping("/type/checkTypeNameUnique")
    @RequiresRoles({"admin"})
    public BaseValidResponse checkProjectTypeNameUnique(@RequestParam("name") String name, @RequestParam(value = "id", required = false) Integer id) {
        ProjectType projectType = projectTypeService.findByNameAndStatus(name, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(projectType);
        }
        if (projectType == null || projectType.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存项目类型
     */
    @ResponseBody
    @PostMapping("/type/save")
    @RequiresRoles({"admin"})
    public BaseResponse typeSave(ProjectType projectType) {
        ProjectType type = projectTypeService.saveProjectType(projectType);
        return BaseResponse.toResponse(type);
    }

    /**
     * 修改项目类型
     */
    @ResponseBody
    @PostMapping("/type/update")
    @RequiresRoles({"admin"})
    public BaseResponse typeUpdate(ProjectType projectType) {
        ProjectType type = projectTypeService.updateProjectType(projectType);
        return BaseResponse.toResponse(type);
    }

}
