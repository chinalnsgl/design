package com.zw.design.modules.build.distributedesigntask.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.sectiontype.service.SectionTypeService;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.service.ProjectService;
import com.zw.design.modules.build.distributedesigntask.query.ProjectQuery;
import com.zw.design.modules.build.distributedesigntask.service.ProcessService;
import com.zw.design.modules.build.distributedesigntask.service.TaskService;
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
    private ProjectService projectService;

    @Autowired
    private SectionTypeService sectionTypeService;

    @Autowired
    private TaskService taskService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 下达设计任务页面
     */
    @GetMapping("/ddts")
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
    public BaseResponse ddtList(ProjectQuery query) {
        query.setStatusQuery(1);
        BaseDataTableModel<Project> dto = projectService.findProjectsByQuery(query);
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
        if (taskService.distributeDesignTAsk(projectId, sectionId)) {
            return BaseResponse.STATUS_200;
        } else {
            return BaseResponse.STATUS_400;
        }
    }
}
