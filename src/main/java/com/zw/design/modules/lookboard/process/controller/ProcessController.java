package com.zw.design.modules.lookboard.process.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.lookboard.process.model.ProcessModel;
import com.zw.design.modules.lookboard.process.query.ProcessQuery;
import com.zw.design.modules.lookboard.process.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/board/process")
public class ProcessController {

    private String prefix = "lookboard/process";

    @Autowired
    private ProcessService processService;

    /**
     * 多项目看板页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"process:list"})
    public String distributePage() {
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"process:list"})
    public BaseResponse ddtList(ProcessQuery query) {
        BaseDataTableModel<ProcessModel> dto = processService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
