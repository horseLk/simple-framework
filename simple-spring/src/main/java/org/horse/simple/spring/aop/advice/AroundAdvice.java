package org.horse.simple.spring.aop.advice;

import org.aopalliance.aop.Advice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

/**
 * 环绕切面
 *
 * @author horse
 * @date 2021/6/27
 */
public interface AroundAdvice extends Advice {
    /**
     * 环绕通知方法
     * @param methodInvocation MethodInvocation
     * @return 被代理方法返回
     */
    Object around(AopMethodInvocation methodInvocation);
}
