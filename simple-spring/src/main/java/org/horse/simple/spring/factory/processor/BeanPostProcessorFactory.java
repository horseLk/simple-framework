package org.horse.simple.spring.factory.processor;

import org.horse.simple.spring.factory.support.BeanPostProcessor;

/**
 * BeanPostProcessor 注册接口
 *
 * @author horse
 * @date 2021/6/15
 */
public interface BeanPostProcessorFactory {
    /**
     * 将BeanPostProcessor对象注册到容器
     *
     * @param beanPostProcessor BeanPostProcessor
     */
    void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
