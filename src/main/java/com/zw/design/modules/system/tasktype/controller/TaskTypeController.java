package com.zw.design.modules.system.tasktype.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.tasktype.entity.TaskType;
import com.zw.design.modules.system.tasktype.query.TaskTypeQuery;
import com.zw.design.modules.system.tasktype.service.TaskTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class TaskTypeController {

    private String prefix = "system";

    @Autowired
    private TaskTypeService taskTypeService;

    /**
     * 到达任务类型列表页面
     */
    @GetMapping("/tasktypes")
    @RequiresPermissions({"tasktype:list"})
    public String users() {
        return prefix + "/taskType/list";
    }
    /**
     * 任务类型列表数据
     */
    @ResponseBody
    @PostMapping("/tasktype/list")
    @RequiresPermissions({"tasktype:list"})
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
    @PostMapping("/tasktype/status")
    @RequiresPermissions(value = {"tasktype:del"})
    public BaseResponse updateTaskTypeStatus(@RequestParam("id")Integer id) {
        TaskType taskType = taskTypeService.updateTaskTypeStatus(id,0);
        return BaseResponse.toResponse(taskType);
    }


    /**
     * 添加页面
     */
    @GetMapping("/tasktype/create")
    @RequiresPermissions({"tasktype:create"})
    public String taskTypeCreate() {
        return  prefix + "/taskType/create";
    }
    /**
     * 任务类型唯一验证
     */
    @ResponseBody
    @PostMapping("/tasktype/checkTaskTypeNameUnique")
    @RequiresPermissions({"tasktype:create"})
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
    @PostMapping("/tasktype/save")
    @RequiresPermissions({"tasktype:create"})
    public BaseResponse taskTypeSave(TaskType taskType) {
        TaskType tType = taskTypeService.saveTaskType(taskType);
        return BaseResponse.toResponse(tType);
    }


    /**
     * 修改任务类型
     */
    @ResponseBody
    @PostMapping("/tasktype/update")
    @RequiresPermissions({"tasktype:edit"})
    public BaseResponse taskTypeUpdate(TaskType taskType) {
        TaskType tType = taskTypeService.updateTaskType(taskType);
        return BaseResponse.toResponse(tType);
    }
}
