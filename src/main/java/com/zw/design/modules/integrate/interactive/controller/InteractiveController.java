package com.zw.design.modules.integrate.interactive.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.integrate.interactive.service.InteractiveService;
import com.zw.design.modules.lookboard.single.entity.Message;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/integrate/interactive")
public class InteractiveController {

    private String prefix = "integrate/interactive";

    @Autowired
    private InteractiveService interactiveService;

    // 互动页面
    @GetMapping("/page")
    @RequiresPermissions({"interactive:list"})
    public String interactivePage() {
        return prefix + "/list";
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/project/list")
    @RequiresPermissions({"interactive:list"})
    public BaseResponse projectList(InteractiveQuery query) {
        BaseDataTableModel<Project> dto = interactiveService.findProjectByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    // 列表数据
    @ResponseBody
    @PostMapping("/message/list")
    @RequiresPermissions({"interactive:list"})
    public BaseResponse messageList(@RequestParam("id") Integer id) {
        List<Message> messages = interactiveService.findMessageByProjectId(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(messages);
        return baseResponse;
    }
}
