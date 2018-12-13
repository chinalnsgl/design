package com.zw.design.modules.baseinfosetting.taskname.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.baseinfosetting.sectiontype.service.SectionTypeService;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.baseinfosetting.taskname.query.TaskNameQuery;
import com.zw.design.modules.baseinfosetting.taskname.service.TaskNameService;
import com.zw.design.modules.baseinfosetting.tasktype.service.TaskTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/baseinfo/taskname")
public class TaskNameController {

    private String prefix = "baseinfo/taskName";

    @Autowired
    private TaskNameService taskNameService;
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private SectionTypeService sectionTypeService;

    /**
     * 到达部门名称列表页面
     */
    @GetMapping("/page")
    @RequiresRoles({"admin"})
    public String users(Model model) {
        model.addAttribute("taskType", taskTypeService.findByStatus(1));
        model.addAttribute("sectionType", sectionTypeService.findByStatus(1));
        return prefix + "/list";
    }
    /**
     * 部门名称列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresRoles({"admin"})
    public BaseResponse taskNameList(TaskNameQuery query) {
        BaseDataTableModel<TaskName> dto = taskNameService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除部门名称
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateTaskNameStatus(@RequestParam("id")Integer id) {
        TaskName taskName = taskNameService.updateTaskNameStatus(id,0);
        return BaseResponse.toResponse(taskName);
    }


    /**
     * 添加页面
     */
    @GetMapping("/create")
    @RequiresRoles({"admin"})
    public String taskNameCreate(Model model) {
        model.addAttribute("taskType", taskTypeService.findByStatus(1));
        model.addAttribute("sectionType", sectionTypeService.findByStatus(1));
        return  prefix + "/create";
    }

    /**
     * 部门名称唯一验证
     */
    @ResponseBody
    @PostMapping("/checkTaskNameUnique")
    @RequiresRoles({"admin"})
    public BaseValidResponse checkTaskNameUnique(@RequestParam("name") String name, @RequestParam(value = "id", required = false) Integer id) {
        TaskName taskName = taskNameService.findByNameAndStatus(name, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(taskName);
        }
        if (taskName == null || taskName.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存部门名称
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public BaseResponse taskNameSave(TaskName taskName) {
        TaskName tName = taskNameService.saveTaskName(taskName);
        return BaseResponse.toResponse(tName);
    }


    /**
     * 修改部门名称
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresRoles({"admin"})
    public BaseResponse taskNameUpdate(TaskName taskName) {
        TaskName tName = taskNameService.updateTaskName(taskName);
        return BaseResponse.toResponse(tName);
    }
}
