package com.zw.design.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.dto.BaseResponse;
import com.zw.design.entity.SysUser;
import com.zw.design.mapper.dbo.SysUserMapper;
import com.zw.design.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @ResponseBody
    @LogAnnotation(action = "登录")
    public BaseResponse login(SysUser user) throws Exception {
//        log.info(user.getUserName() + "   " + user.getPassword());
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            SysUser u = (SysUser) subject.getPrincipal();
//            session.setAttribute("user", user);
            return BaseResponse.STATUS_200;
        } catch(Exception e) {
            return BaseResponse.STATUS_400;
        }
    }

    /**
     * 修改密码
     */
    @ResponseBody
    @PostMapping("/update")
    @LogAnnotation(action = "修改密码")
    public BaseResponse updatePassword(String password) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUser.setPassword(password);
        sysUser = userService.updateUser(sysUser);
        return BaseResponse.toResponse(sysUser);
    }
}
