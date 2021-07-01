package org.horse.simple.spring.aop.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用jdk实现的动态代理
 * @author horse
 * @date 2021/6/27
 */
public class JdkDynamicAopProxy extends BaseAopProxy implements InvocationHandler {
    @Override
    public Object getProxyObject() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), this.advisedSupport.getTargetSource().getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return advisedSupport.getMethodInterceptor().invoke(new AopMethodInvocation(advisedSupport.getTargetSource(), method, args, null, proxy));
    }
}
