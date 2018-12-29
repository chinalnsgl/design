package com.zw.design.modules.overview.statistical.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/overview/statistical")
public class StatisticalController {

    private String prefix = "overview/statistical";

    /**
     * 会议列表
     */
    @GetMapping("/page")
    @RequiresPermissions({"statistical:list"})
    public String meetings() {
        return prefix + "/detail";
    }

}
