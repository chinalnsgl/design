package com.zw.design.modules.index.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.index.service.IndexService;
import com.zw.design.modules.lookboard.single.entity.Receiver;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.service.UserService;
import com.zw.design.utils.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private IndexService indexService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 获取用户未读消息
     */
    @ResponseBody
    @GetMapping("/user/receiver")
    @RequiresAuthentication
    public BaseResponse userReceiver() {
        List<Receiver> receivers = indexService.findUserReceiver();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(receivers);
        return baseResponse;
    }

    /**
     * 修改头像
     */
    @ResponseBody
    @PostMapping("/update/user/img")
    @RequiresAuthentication
    public BaseResponse updateImage(String imageData) {
        String imgName = UUID.randomUUID().toString() + ".png";
        SysUser user = userService.updateImage("/files/" + imgName);
        try {
            FileUtils.decodeBase64DataURLToImage(imageData, uploadPath, imgName);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setContent(user.getImg());
            return baseResponse;
        } catch (IOException e) {
            return BaseResponse.STATUS_400;
        }
    }

    /**
     * 修改密码
     */
    @ResponseBody
    @PostMapping("/update/user/pwd")
    @LogAnnotation(value = "修改密码")
    @RequiresAuthentication
    public BaseResponse updatePassword(String password) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUser.setPassword(password);
        sysUser = userService.updateUserPassword(sysUser);
        return BaseResponse.toResponse(sysUser);
    }

}
