package com.zw.design.modules.build.distributedesigntask.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.sectiontype.service.SectionTypeService;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.query.DistributeDesignTaskQuery;
import com.zw.design.modules.build.distributedesigntask.service.DistributeDesignTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/build")
public class DistributeDesignTaskController {

    private String prefix = "build/ddt";

    @Autowired
    private DistributeDesignTaskService distributeDesignTaskService;

    @Autowired
    private SectionTypeService sectionTypeService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 下达设计任务页面
     */
    @GetMapping("/ddt/page")
    @RequiresPermissions({"ddt:list"})
    public String distributePage(Model model) {
        model.addAttribute("sectionType", sectionTypeService.findByStatus(1));
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/ddt/list")
    @RequiresPermissions({"ddt:list"})
    public BaseResponse ddtList(DistributeDesignTaskQuery query) {
        query.setStatusQuery(1);
        BaseDataTableModel<Project> dto = distributeDesignTaskService.findProjectsByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 下达任务单
     */
    @ResponseBody
    @PostMapping("/ddt/distribute")
    public BaseResponse send(@RequestParam("projectId") Integer projectId, @RequestParam("sectionId") Integer[] sectionId) {
        if (distributeDesignTaskService.distributeDesignTAsk(projectId, sectionId)) {
            return BaseResponse.STATUS_200;
        } else {
            return BaseResponse.STATUS_400;
        }
    }
}
