package org.horse.simple.spring.aop.advice;

import org.aopalliance.aop.Advice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

/**
 * 前置切面
 *
 * @author horse
 * @date 2021/6/27
 */
public interface BeforeAdvice extends Advice {
    /**
     * 前置通知方法
     * @param methodInvocation MethodInvocation
     */
    void before(AopMethodInvocation methodInvocation);
}
