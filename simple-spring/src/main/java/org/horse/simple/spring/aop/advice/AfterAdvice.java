package org.horse.simple.spring.aop.advice;

import org.aopalliance.aop.Advice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

/**
 * 后置切面
 *
 * @author horse
 * @date 2021/6/27
 */
public interface AfterAdvice extends Advice {
    /**
     * 后置通知方法
     *
     * @param methodInvocation MethodInvocation
     */
    void after(AopMethodInvocation methodInvocation);
}
