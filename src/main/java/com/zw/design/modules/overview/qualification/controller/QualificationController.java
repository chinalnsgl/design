package com.zw.design.modules.overview.qualification.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/overview/qualification")
public class QualificationController {

    private String prefix = "overview/qualification";

    /**
     * 资质表页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"qualification:list"})
    public String qualificationPage() {
        return prefix + "/list";
    }

}
