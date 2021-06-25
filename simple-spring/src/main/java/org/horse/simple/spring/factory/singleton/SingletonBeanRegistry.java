package org.horse.simple.spring.factory.singleton;

/**
 * 单例bean获取接口
 *
 * @author horse
 * @date 2021/6/15
 */
public interface SingletonBeanRegistry {
    /**
     * 从单例池获取对象
     *
     * @param beanName beanName
     * @return bean
     */
    Object getSingleton(String beanName);
}
