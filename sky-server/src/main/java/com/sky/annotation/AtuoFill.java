package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解，用于标识公共字段的自动填充处理
@Target(ElementType.METHOD)//指明这个注解只能加载在方法上面
@Retention(RetentionPolicy.RUNTIME)
public @interface AtuoFill{
    //指定数据库操作类型：update insert

    OperationType value();
}