package com.zw.design.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
public @interface LogAnnotation {

    String value() default "";
}
