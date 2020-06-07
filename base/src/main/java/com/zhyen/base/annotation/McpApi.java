package com.zhyen.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记可供 h5 调用的 api，每个方法最少有三个参数，第一个是 Context
 * 倒数两个分别是成功回调和失败回调
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface McpApi {
    McpApiType value() default McpApiType.Common;
}
