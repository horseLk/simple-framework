package org.horse.simple.spring.aop.support;

import lombok.Getter;
import org.horse.simple.spring.aop.utils.AopUtils;

/**
 * 被代理对象信息
 *
 * @author horse
 * @date 2021/6/26
 */
@Getter
public class TargetSource {
    /**
     * 被代理类对象
     */
    private Class<?> targetClass;

    /**
     * 被代理对象
     */
    private Object target;

    private TargetSource(){
    }

    public TargetSource(Class<?> targetClass, Object target) {
        this.target = target;
        this.targetClass = targetClass;
    }
}
