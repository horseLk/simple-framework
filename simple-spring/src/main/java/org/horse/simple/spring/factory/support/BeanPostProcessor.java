package org.horse.simple.spring.factory.support;

/**
 * 初始化前后处理 bean
 *
 * @author horse
 * @date 2021/6/15
 */
public interface BeanPostProcessor {
    /**
     * bean 初始化前对bean的操作
     *
     * @param beanName beanName
     * @param bean     bean对象
     * @return bean对象
     */
    Object postProcessBeforeInitialization(String beanName, Object bean);

    /**
     * bean 初始化后对bean的操作
     *
     * @param beanName beanName
     * @param bean     bean对象
     * @return bean对象
     */
    Object postProcessAfterInitialization(String beanName, Object bean);
}
