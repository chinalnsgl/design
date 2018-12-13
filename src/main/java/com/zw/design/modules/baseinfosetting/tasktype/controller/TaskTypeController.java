package com.zw.design.modules.baseinfosetting.tasktype.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.query.TaskTypeQuery;
import com.zw.design.modules.baseinfosetting.tasktype.service.TaskTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/baseinfo/tasktype")
public class TaskTypeController {

    private String prefix = "baseinfo/taskType";

    @Autowired
    private TaskTypeService taskTypeService;

    /**
     * 到达任务类型列表页面
     */
    @GetMapping("/page")
    @RequiresRoles({"admin"})
    public String users() {
        return prefix + "/list";
    }
    /**
     * 任务类型列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresRoles({"admin"})
    public BaseResponse taskTypeList(TaskTypeQuery query) {
        BaseDataTableModel<TaskType> dto = taskTypeService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除任务类型
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateTaskTypeStatus(@RequestParam("id")Integer id) {
        TaskType taskType = taskTypeService.updateTaskTypeStatus(id,0);
        return BaseResponse.toResponse(taskType);
    }


    /**
     * 添加页面
     */
    @GetMapping("/create")
    @RequiresRoles({"admin"})
    public String taskTypeCreate() {
        return  prefix + "/create";
    }
    /**
     * 任务类型唯一验证
     */
    @ResponseBody
    @PostMapping("/checkTaskTypeNameUnique")
    @RequiresRoles({"admin"})
    public BaseValidResponse checkTaskTypeNameUnique(@RequestParam(value = "name") String name, @RequestParam(value = "id", required = false) Integer id ) {
        TaskType taskType = taskTypeService.findByNameAndStatus(name, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(taskType);
        }
        if (taskType == null || taskType.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存任务类型
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public BaseResponse taskTypeSave(TaskType taskType) {
        TaskType tType = taskTypeService.saveTaskType(taskType);
        return BaseResponse.toResponse(tType);
    }


    /**
     * 修改任务类型
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresRoles({"admin"})
    public BaseResponse taskTypeUpdate(TaskType taskType) {
        TaskType tType = taskTypeService.updateTaskType(taskType);
        return BaseResponse.toResponse(tType);
    }
}
