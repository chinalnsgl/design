package com.zw.design.aspect;

import com.zw.design.entity.LogInfo;
import com.zw.design.entity.SysUser;
import com.zw.design.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(LogAnnotation)")
    private void pointCut() { }

    @After("pointCut()")
    public void recordLog(JoinPoint joinPoint) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        LogInfo logInfo = new LogInfo();
        logInfo.setAccount(user.getUserName());
        logInfo.setName(user.getName());
        logInfo.setOperationName(logAnnotation.action());
        logInfo.setOperationTime(new Date());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            logInfo.setAddress(request.getRemoteAddr());
        }
        logService.saveLog(logInfo);
    }
}
