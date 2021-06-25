package org.horse.simple.spring.processor;

import org.horse.simple.spring.factory.aware.BeanNameAware;
import org.horse.simple.spring.factory.support.BeanPostProcessor;

/**
 * 感知BeanNameAware的BeanPostProcessor
 *
 * @author horse
 * @date 2021/6/25
 */
public class BeanNameAwarePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setAwareField(beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        return bean;
    }
}
