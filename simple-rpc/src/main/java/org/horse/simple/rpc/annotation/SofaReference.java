package org.horse.simple.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
/**
 * 引用服务的注解
 *
 * @author horse
 * @date 2021/7/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SofaReference {
    /**
     * 引用服务的名称
     */
    String value() default "";

    /**
     * 超时控制 单位：ms
     */
    long timeout() default 3000L;
}
