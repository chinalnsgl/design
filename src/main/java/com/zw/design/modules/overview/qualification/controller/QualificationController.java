package com.zw.design.modules.overview.qualification.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.section.service.SectionService;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import com.zw.design.modules.lookboard.multi.service.MultiService;
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
