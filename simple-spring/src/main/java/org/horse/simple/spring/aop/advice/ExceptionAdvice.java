package org.horse.simple.spring.aop.advice;

import org.aopalliance.aop.Advice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

/**
 * 异常切面
 *
 * @author horse
 * @date 2021/6/27
 */
public interface ExceptionAdvice extends Advice {
    /**
     * 异常通知方法
     * @param methodInvocation MethodInvocation
     * @param throwable 异常
     */
    void afterException(AopMethodInvocation methodInvocation, Throwable throwable);
}
