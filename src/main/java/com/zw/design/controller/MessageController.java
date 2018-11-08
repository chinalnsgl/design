package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.Message;
import com.zw.design.entity.Receiver;
import com.zw.design.entity.SysUser;
import com.zw.design.query.UserQuery;
import com.zw.design.service.LogService;
import com.zw.design.service.MessageService;
import com.zw.design.service.RoleService;
import com.zw.design.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/message")
public class MessageController {

    private String prefix = "message";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LogService logService;
    @Autowired
    private MessageService messageService;

    /**
     * 获取用户未读消息
     */
    @ResponseBody
    @GetMapping("/user/receiver")
    public BaseResponse userReceiver() {
        List<Receiver> receivers = messageService.findUserReceiver();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(receivers);
        return baseResponse;
    }

    /**
     * 发消息
     */
    @ResponseBody
    @PostMapping("/send")
    public BaseResponse MessageCreate(Message message, Integer projectId, HttpServletRequest request) {
        List<String> users = new ArrayList<>();
        Pattern pattern = Pattern.compile("@(\\S*)\\s+?");

        Matcher matcher = pattern.matcher(message.getContent());
        while (matcher.find()) {
            users.add(matcher.group(1));
        }

        logService.saveLog("发消息：" + message.getContent(), request);
        messageService.saveMessage(message, projectId, users);
        return new BaseResponse();
    }

}
