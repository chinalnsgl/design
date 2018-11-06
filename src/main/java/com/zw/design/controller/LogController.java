package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.LogInfo;
import com.zw.design.query.LogQuery;
import com.zw.design.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/sys")
public class LogController {

    private String prefix = "system";

    @Autowired
    private LogService logService;


    @GetMapping("/logs")
    public String logPage() {
        return prefix + "/log/list";
    }

    @PostMapping("/log/list")
    @ResponseBody
    public BaseResponse logList(LogQuery query) {
        DataTablesCommonDto<LogInfo> dto = logService.findLogByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

}
