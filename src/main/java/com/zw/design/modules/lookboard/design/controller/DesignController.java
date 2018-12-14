package com.zw.design.modules.lookboard.design.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.section.service.SectionService;
import com.zw.design.modules.lookboard.design.model.DesignModel;
import com.zw.design.modules.lookboard.design.query.DesignQuery;
import com.zw.design.modules.lookboard.design.service.DesignService;
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
@RequestMapping("/board/design")
public class DesignController {

    private String prefix = "lookboard/design";

    @Autowired
    private DesignService designService;

    @Autowired
    private SectionService sectionService;

    /**
     * 设计进度看板页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"design:list"})
    public String distributePage(Model model) {
        model.addAttribute("sections", sectionService.findByStatus(1));
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"design:list"})
    public BaseResponse ddtList(DesignQuery query) {
        BaseDataTableModel<DesignModel> dto = designService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
