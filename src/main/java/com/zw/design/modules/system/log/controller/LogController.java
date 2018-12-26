package com.zw.design.modules.system.log.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.entity.LogInfo;
import com.zw.design.modules.system.log.query.LogQuery;
import com.zw.design.modules.system.log.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/log")
public class LogController {

    private String prefix = "system/log";

    @Autowired
    private LogService logService;

    @GetMapping("/page")
    @RequiresPermissions({"log:list"})
    public String logPage() {
        return prefix + "/list";
    }

    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions({"log:list"})
    public BaseResponse logList(LogQuery query) {
        BaseDataTableModel<LogInfo> dto = logService.findLogByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

}
