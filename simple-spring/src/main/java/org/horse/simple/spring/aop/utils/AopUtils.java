package org.horse.simple.spring.aop.utils;

import org.horse.simple.spring.annotation.After;
import org.horse.simple.spring.annotation.AfterException;
import org.horse.simple.spring.annotation.Around;
import org.horse.simple.spring.annotation.Before;
import org.horse.simple.spring.aop.proxy.JdkDynamicAopProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * AOP相关工具类
 *
 * @author horse
 * @date 2021/6/27
 */
public class AopUtils {
    private AopUtils() {
    }

    /**
     * 判断是否是代理对象
     *
     * @param obj 对象
     * @return true/false
     */
    public static boolean isAopProxy(Object obj) {
        return isJdkProxy(obj) || isCglibProxy(obj);
    }

    /**
     * 判断是否是Jdk动态代理生成的代理对象
     *
     * @param obj obj
     * @return true/false
     */
    public static boolean isJdkProxy(Object obj) {
        return Proxy.isProxyClass(obj.getClass());
    }

    /**
     * 判断是否是cglib代理对象
     *
     * @param obj obj
     * @return true/false
     */
    public static boolean isCglibProxy(Object obj) {
        return obj.getClass().getName().contains("$$");
    }

    /**
     * 解析一个对象的原始被代理类，避免多重代理错误
     *
     * @param obj 对象
     * @return 原始被代理类
     */
    public static Class<?> getTargetClass(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!isAopProxy(obj)) {
            return obj.getClass();
        }
        if (isCglibProxy(obj)) {
            return obj.getClass().getSuperclass();
        }
        try {
            return getTargetClassFromJdkProxy(obj);
        } catch (Exception e) {
            throw new RuntimeException("原始被代理类解析异常");
        }
    }

    /**
     * 解析一个jdk代理对象的原始被代理类，避免多重代理错误
     *
     * @param proxy 对象
     * @return 原始被代理类
     */
    private static Class<?> getTargetClassFromJdkProxy(Object proxy) throws Exception {
        while (Proxy.isProxyClass(proxy.getClass())) {
            Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
            field.setAccessible(true);
            proxy = field.get(proxy);
        }
        JdkDynamicAopProxy jdkProxy = (JdkDynamicAopProxy) proxy;
        return jdkProxy.getAdvisedSupport().getTargetSource().getTargetClass();
    }



    /**
     * 判断方法上是否存在AOP注解
     * @param method 方法
     * @return true/false
     */
    public static boolean hasAopAnnotation(Method method) {
        return method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(After.class)
                || method.isAnnotationPresent(AfterException.class) ||method.isAnnotationPresent(Around.class);
    }

    /**
     * 判断类上是否存在AOP注解
     * @param clazz 类对象
     * @return true/false
     */
    public static boolean hasAopAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Before.class) || clazz.isAnnotationPresent(After.class)
                || clazz.isAnnotationPresent(AfterException.class) ||clazz.isAnnotationPresent(Around.class);
    }

    /**
     * 判断aop相关的注解是否冲突
     * 规则：Around和其他三类不能同时出现
     *
     * @param method 方法
     * @return 是否冲突
     */
    public static boolean hasConflictAopAnnotation(Method method) {
        if (!method.isAnnotationPresent(Around.class)) {
            return false;
        }
        return method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(After.class) || method.isAnnotationPresent(AfterException.class);
    }

    /**
     * 判断aop相关的注解是否冲突
     * 规则：Around和其他三类不能同时出现
     *
     * @param clazz 类对象
     * @return 是否冲突
     */
    public static boolean hasConflictAopAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Around.class)) {
            return false;
        }
        return clazz.isAnnotationPresent(Before.class) || clazz.isAnnotationPresent(After.class) || clazz.isAnnotationPresent(AfterException.class);
    }
}
