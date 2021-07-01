package org.horse.simple.spring.aop.interceptor;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.horse.simple.spring.annotation.After;
import org.horse.simple.spring.annotation.AfterException;
import org.horse.simple.spring.annotation.Around;
import org.horse.simple.spring.annotation.Before;
import org.horse.simple.spring.aop.advice.AfterAdvice;
import org.horse.simple.spring.aop.advice.AroundAdvice;
import org.horse.simple.spring.aop.advice.BeforeAdvice;
import org.horse.simple.spring.aop.advice.ExceptionAdvice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;
import org.horse.simple.spring.aop.utils.AopUtils;

import java.lang.reflect.Method;

/**
 * 默认的方法调用
 *
 * @author horse
 * @date 2021/6/27
 */
public class DefaultMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        AopMethodInvocation aopMethodInvocation = (AopMethodInvocation) methodInvocation;
        // 如果method和class上同时配置了aop相关注解，则以method的注解为准
        if (AopUtils.hasAopAnnotation(aopMethodInvocation.getMethod())) {
            return invokeByMethod(aopMethodInvocation);
        }
        if (AopUtils.hasAopAnnotation(aopMethodInvocation.getTargetSource().getTargetClass())) {
            return invokeByClass(aopMethodInvocation);
        }
        // 不需要织入切面，直接返回即可
        return aopMethodInvocation.proceed();
    }

    private Object invokeByClass(AopMethodInvocation aopMethodInvocation) throws Throwable {
        Class<?> targetClass = aopMethodInvocation.getTargetSource().getTargetClass();
        if (AopUtils.hasConflictAopAnnotation(targetClass)) {
            throw new RuntimeException("环绕通知和其他三类通知不能同时出现");
        }
        if (targetClass.isAnnotationPresent(Around.class)) {
            AroundAdvice aroundAdvice = (AroundAdvice) getAdviceObject(targetClass.getAnnotation(Around.class).value());
            return aroundAdvice.around(aopMethodInvocation);
        }
        // 其他三种类型通知
        Object result;
        try {
            if (targetClass.isAnnotationPresent(Before.class)) {
                BeforeAdvice beforeAdvice = (BeforeAdvice) getAdviceObject(targetClass.getAnnotation(Before.class).value());
                beforeAdvice.before(aopMethodInvocation);
            }
            result = aopMethodInvocation.proceed();
        } catch (Exception e) {
            if (targetClass.isAnnotationPresent(AfterException.class)) {
                ExceptionAdvice exceptionAdvice = (ExceptionAdvice) getAdviceObject(targetClass.getAnnotation(AfterException.class).value());
                exceptionAdvice.afterException(aopMethodInvocation, e);
            }
            throw new RuntimeException(e);
        }
        if (targetClass.isAnnotationPresent(After.class)) {
            AfterAdvice afterAdvice = (AfterAdvice) getAdviceObject(targetClass.getAnnotation(After.class).value());
            afterAdvice.after(aopMethodInvocation);
        }
        return result;
    }

    /**
     * 使用类的aop注解配置织入切面
     *
     * @param aopMethodInvocation AopMethodInvocation
     * @return 被代理方法返回结果
     * @throws Throwable 异常
     */
    private Object invokeByMethod(AopMethodInvocation aopMethodInvocation) throws Throwable {
        Method method = aopMethodInvocation.getMethod();
        if (AopUtils.hasConflictAopAnnotation(method)) {
            throw new RuntimeException("环绕通知和其他三类通知不能同时出现");
        }
        if (method.isAnnotationPresent(Around.class)) {
            AroundAdvice aroundAdvice = (AroundAdvice) getAdviceObject(method.getAnnotation(Around.class).value());
            return aroundAdvice.around(aopMethodInvocation);
        }
        // 其他三种类型通知
        Object result;
        try {
            if (method.isAnnotationPresent(Before.class)) {
                BeforeAdvice beforeAdvice = (BeforeAdvice) getAdviceObject(method.getAnnotation(Before.class).value());
                beforeAdvice.before(aopMethodInvocation);
            }
            result = aopMethodInvocation.proceed();
        } catch (Exception e) {
            if (method.isAnnotationPresent(AfterException.class)) {
                ExceptionAdvice exceptionAdvice = (ExceptionAdvice) getAdviceObject(method.getAnnotation(AfterException.class).value());
                exceptionAdvice.afterException(aopMethodInvocation, e);
            }
            throw new RuntimeException(e);
        }
        if (method.isAnnotationPresent(After.class)) {
            AfterAdvice afterAdvice = (AfterAdvice) getAdviceObject(method.getAnnotation(After.class).value());
            afterAdvice.after(aopMethodInvocation);
        }
        return result;
    }

    /**
     * 根据类全路径名获取对象
     *
     * @param className className
     * @return Advice Object
     * @throws Exception 异常
     */
    private Advice getAdviceObject(String className) throws Exception {
        Class<?> aroundClass = Class.forName(className);
        return (Advice) aroundClass.newInstance();
    }
}
