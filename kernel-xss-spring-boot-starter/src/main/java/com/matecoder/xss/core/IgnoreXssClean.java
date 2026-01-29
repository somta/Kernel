package com.matecoder.xss.core;

import java.lang.annotation.*;

/**
 * 添加在方法上忽略清理
 * @author husong
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreXssClean {
}