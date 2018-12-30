package com.zw.design.config;


import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public String unAuthorizedHandler() {
        return "/403";
    }

    @ExceptionHandler(Exception.class)
    public String allExceptionHandler() {
        return "/500";
    }

}
