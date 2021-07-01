package org.horse.simple.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * 前置通知注解
 *
 * @author horse
 * @date 2021/6/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Before {
    /**
     * 使用的前置切面类的全路径名
     * @return 全路径名
     */
    String value();
}
