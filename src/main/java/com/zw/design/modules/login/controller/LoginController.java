package com.zw.design.modules.login.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.system.user.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录控制器
 */
@Controller
public class LoginController {


    @PostMapping("/login")
    @ResponseBody
    @LogAnnotation(value = "登录")
    public BaseResponse login(SysUser user) {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", subject.getPrincipal());
            return BaseResponse.STATUS_200;
        } catch(Exception e) {
            return BaseResponse.STATUS_400;
        }
    }


}
