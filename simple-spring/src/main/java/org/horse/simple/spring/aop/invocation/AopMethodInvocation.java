package org.horse.simple.spring.aop.invocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;
import org.horse.simple.spring.aop.support.TargetSource;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 方法调用类
 *
 * @author horse
 * @date 2021/6/26
 */
@Data
@AllArgsConstructor
public class AopMethodInvocation implements MethodInvocation {
    /**
     * 被代理对象
     */
    private TargetSource targetSource;

    /**
     * 方法
     */
    private Method method;

    /**
     * 参数列表
     */
    private Object[] args;

    /**
     * cglib中的方法代理
     */
    private MethodProxy methodProxy;

    /**
     * 代理对象
     */
    private Object proxy;

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        if (methodProxy == null) {
            return method.invoke(targetSource.getTarget(), args);
        }
        return methodProxy.invokeSuper(proxy, args);
    }

    @Override
    public Object getThis() {
        return targetSource.getTarget();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
