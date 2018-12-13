package com.zw.design.modules.lookboard.multi.controller;

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
@RequestMapping("/board/multi")
public class MultiController {

    private String prefix = "lookboard/multi";

    @Autowired
    private MultiService multiService;

    @Autowired
    private SectionService sectionService;

    /**
     * 多项目看板页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"multi:list"})
    public String distributePage(Model model) {
        model.addAttribute("sections", sectionService.findByStatus(1));
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"multi:list"})
    public BaseResponse ddtList(MultiQuery query) {
        BaseDataTableModel<MultiModel> dto = multiService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
