package org.horse.simple.spring.aop.proxy;

import org.horse.simple.spring.aop.support.AdvisedSupport;

/**
 * 创建代理对象的工厂类
 *
 * @author horse
 * @date 2021/6/28
 */
public class AopProxyFactory {
    /**
     * 创建一个代理对象
     * @param advisedSupport AdvisedSupport
     * @return 代理对象
     */
    public static BaseAopProxy newInstance(AdvisedSupport advisedSupport) {
        BaseAopProxy aopProxy;
        if (advisedSupport.getTargetSource().getTargetClass().getInterfaces().length == 0) {
            aopProxy = new CglibDynamicAopProxy();
        } else {
            aopProxy = new JdkDynamicAopProxy();
        }
        aopProxy.setAdvisedSupport(advisedSupport);
        return aopProxy;
    }
}
