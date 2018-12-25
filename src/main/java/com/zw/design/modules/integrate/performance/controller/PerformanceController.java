package com.zw.design.modules.integrate.performance.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import com.zw.design.modules.integrate.performance.service.PerformanceService;
import com.zw.design.modules.lookboard.single.entity.Message;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 员工业绩
@Controller
@RequestMapping("/integrate/performance")
public class PerformanceController {

    private String prefix = "integrate/performance";

    @Autowired
    private PerformanceService performanceService;

    // 互动页面
    @GetMapping("/page")
    @RequiresPermissions({"performance:list"})
    public String performancePage() {
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"performance:list"})
    public BaseResponse projectList(PerformanceQuery query) {
        BaseDataTableModel<Project> dto = performanceService.findProjectByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
