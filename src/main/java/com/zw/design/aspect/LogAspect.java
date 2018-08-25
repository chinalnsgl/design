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

    /*public void recordLog(JoinPoint joinPoint){
        Long start = System.currentTimeMillis();
        Log log = new Log();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            logger.warn("user 信息为空");
        }else{
            log.setUserId(user.getId());
            log.setOperator(user.getUserName());
            log.setCustomerId(user.getCustomerId());
        }
        //下面开始获取 ip，targetType，remark，action
        try {
            Map<String,String> map = getLogMark(joinPoint);
            log.setAction(map.get(LoggerUtil.LOG_ACTION));
            log.setTargetType(map.get(LoggerUtil.LOG_TARGET_TYPE));
            log.setRemark(map.get(LoggerUtil.LOG_REMARK));
            log.setIp(LoggerUtil.getCliectIp(request));
            logMapper.insert(log);
        }catch (ClassNotFoundException c){
            logger.error(c.getMessage());
        }catch (Exception e){
            logger.error("插入日志异常",e.getMessage());
        }
        Long end = System.currentTimeMillis();
        logger.info("记录日志消耗时间:"+ (end - start) / 1000);
    }

    private Map<String,String> getLogMark(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String,String> map = new HashMap<>();
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods){
            if(method.getName().equals(methodName)){
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                map.put(LoggerUtil.LOG_TARGET_TYPE,logAnnotation.targetType());
                map.put(LoggerUtil.LOG_ACTION,logAnnotation.action());
                map.put(LoggerUtil.LOG_REMARK,logAnnotation.remark());
            }
        }
        return map;
    }*/
}
