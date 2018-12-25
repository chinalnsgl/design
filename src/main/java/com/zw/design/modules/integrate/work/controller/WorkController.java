package com.zw.design.modules.integrate.work.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import com.zw.design.modules.integrate.work.service.WorkService;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 项目人工
@Controller
@RequestMapping("/integrate/work")
public class WorkController {

    private String prefix = "integrate/work";

    @Autowired
    private WorkService workService;

    // 互动页面
    @GetMapping("/page")
    @RequiresPermissions({"work:list"})
    public String workPage() {
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"work:list"})
    public BaseResponse workList(WorkQuery query) {
        BaseDataTableModel<TaskEmployee> dto = workService.findEmployeeByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
