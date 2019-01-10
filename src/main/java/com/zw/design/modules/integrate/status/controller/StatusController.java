package com.zw.design.modules.integrate.status.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.section.service.SectionService;
import com.zw.design.modules.integrate.status.model.StatusModel;
import com.zw.design.modules.integrate.status.query.StatusQuery;
import com.zw.design.modules.integrate.status.service.StatusService;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/integrate/status")
public class StatusController {

    private String prefix = "integrate/status";

    @Autowired
    private StatusService statusService;

    /**
     * 状态查询页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"status:list"})
    public String statusPage() {
        return prefix + "/list";
    }

    /**
     * 状态查询列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"status:list"})
    public BaseResponse statusList(StatusQuery query) {
        BaseDataTableModel<StatusModel> dto = statusService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
