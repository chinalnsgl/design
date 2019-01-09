package com.zw.design.modules.integrate.schedule.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.integrate.schedule.model.ScheduleModel;
import com.zw.design.modules.integrate.schedule.query.ScheduleQuery;
import com.zw.design.modules.integrate.schedule.service.ScheduleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/integrate/schedule")
public class ScheduleController {

    private String prefix = "integrate/schedule";

    @Autowired
    private ScheduleService scheduleService;

    // 页面
    @GetMapping("/page")
    @RequiresPermissions({"schedule:list"})
    public String schedulePage() {
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"schedule:list"})
    public BaseResponse scheduleList(ScheduleQuery query) {
        BaseDataTableModel<ScheduleModel> dto = scheduleService.findProjectByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

}
