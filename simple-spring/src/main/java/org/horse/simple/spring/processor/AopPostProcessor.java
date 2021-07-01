package org.horse.simple.spring.processor;

import org.aopalliance.intercept.MethodInterceptor;
import org.horse.simple.spring.aop.interceptor.DefaultMethodInterceptor;
import org.horse.simple.spring.aop.proxy.AopProxyFactory;
import org.horse.simple.spring.aop.support.AdvisedSupport;
import org.horse.simple.spring.aop.support.TargetSource;
import org.horse.simple.spring.aop.utils.AopUtils;
import org.horse.simple.spring.factory.support.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * AOP 相关BeanPostProcessor
 * @author horse
 * @date 2021/7/1
 */
public class AopPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        Class<?> clazz = bean.getClass();
        // 类上有需要织入的aop注解
        if (AopUtils.hasAopAnnotation(clazz)) {
            return AopProxyFactory.newInstance(genAdviceSupport(clazz, bean)).getProxyObject();
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();
        // 某个方法上有需要织入的aop注解
        for (Method method : declaredMethods) {
            if (AopUtils.hasAopAnnotation(method)) {
                System.out.println("okkkk");
                return AopProxyFactory.newInstance(genAdviceSupport(clazz, bean)).getProxyObject();
            }
        }
        return bean;
    }

    /**
     * 根据bean和targetClass生成AdvisedSupport
     * @param clazz targetClass
     * @param bean bean
     * @return AdvisedSupport
     */
    private AdvisedSupport genAdviceSupport(Class<?> clazz, Object bean) {
        if (AopUtils.isAopProxy(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        TargetSource targetSource = new TargetSource(clazz, bean);
        MethodInterceptor methodInterceptor = new DefaultMethodInterceptor();
        return new AdvisedSupport(targetSource, methodInterceptor);
    }

}
