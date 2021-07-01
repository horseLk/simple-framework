package org.horse.simple.spring.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

import java.lang.reflect.Method;

/**
 * cdlib实现的动态代理类
 *
 * @author horse
 * @date 2021/6/28
 */
public class CglibDynamicAopProxy extends BaseAopProxy implements MethodInterceptor {
    @Override
    public Object getProxyObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return advisedSupport.getMethodInterceptor().invoke(new AopMethodInvocation(advisedSupport.getTargetSource(), method, objects, methodProxy, proxy));
    }
}
