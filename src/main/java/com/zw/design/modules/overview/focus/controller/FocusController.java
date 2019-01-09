package com.zw.design.modules.overview.focus.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.section.service.SectionService;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import com.zw.design.modules.lookboard.multi.service.MultiService;
import com.zw.design.modules.overview.meeting.entity.Comment;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;
import com.zw.design.modules.overview.meeting.service.CommentService;
import com.zw.design.modules.overview.meeting.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/overview/focus")
public class FocusController {

    private String prefix = "overview/focus";

    @Autowired
    private MultiService multiService;

    @Autowired
    private SectionService sectionService;

    /**
     * 重点项目页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"focus:list"})
    public String focusPage(Model model) {
        model.addAttribute("sections", sectionService.findByStatus(1));
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"focus:list"})
    public BaseResponse focusList(MultiQuery query) {
        BaseDataTableModel<MultiModel> dto = multiService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
