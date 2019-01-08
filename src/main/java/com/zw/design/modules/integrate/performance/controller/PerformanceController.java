package com.zw.design.modules.integrate.performance.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.integrate.performance.model.PerformanceModel;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import com.zw.design.modules.integrate.performance.service.PerformanceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String performancePage(Model model) {
        model.addAttribute("taskType", performanceService.findTaskTypeByStatus(1));
        model.addAttribute("sections", performanceService.findSectionByStatus(1));
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"performance:list"})
    public BaseResponse projectList(PerformanceQuery query) {
        BaseDataTableModel<PerformanceModel> dto = performanceService.findProjectByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
