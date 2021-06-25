package org.horse.simple.spring.factory;

import org.horse.simple.spring.factory.bean.BeanDefinition;

/**
 * BeanDefinition 注册接口
 *
 * @author horse
 * @date 2021/6/15
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册 BeanDefinition
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
