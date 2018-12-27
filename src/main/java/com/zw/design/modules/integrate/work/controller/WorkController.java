package com.zw.design.modules.integrate.work.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.integrate.work.model.WorkModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import com.zw.design.modules.integrate.work.service.WorkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String workPage(Model model) {
        model.addAttribute("taskType", workService.findTaskTypeByStatus(1));
        model.addAttribute("sections", workService.findSectionByStatus(1));
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"work:list"})
    public BaseResponse workList(WorkQuery query) {
        BaseDataTableModel<WorkModel> dto = workService.findEmployeeByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
