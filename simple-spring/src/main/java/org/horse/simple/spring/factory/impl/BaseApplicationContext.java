package org.horse.simple.spring.factory.impl;

import com.google.common.collect.Lists;
import org.horse.simple.spring.factory.BeanFactory;
import org.horse.simple.spring.factory.bean.BeanDefinition;
import org.horse.simple.spring.factory.processor.BeanPostProcessorFactory;
import org.horse.simple.spring.factory.singleton.impl.DefaultSingletonBeanRegistry;
import org.horse.simple.spring.factory.support.BeanPostProcessor;

import java.util.List;

/**
 * ApplicationContext的基类
 *
 * @author horse
 * @date 2021/6/15
 */
public abstract class BaseApplicationContext extends DefaultSingletonBeanRegistry implements BeanFactory, BeanPostProcessorFactory {
    /**
     * 容器是否初始化
     */
    protected boolean isInitial = false;

    /**
     * BeanPostProcessor 容器
     */
    protected final List<BeanPostProcessor> BEAN_POST_PROCESSOR_LIST = Lists.newArrayList();

    @Override
    public Object getBean(String beanName) throws Exception {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        BEAN_POST_PROCESSOR_LIST.add(beanPostProcessor);
    }

    /**
     * 根据BeanDefinition创建bean实例
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return bean
     * @throws Exception 异常信息
     */
    public abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws Exception;

    /**
     * 获取指定的beandefinition
     *
     * @param beanName beanName
     * @return BeanDefinition
     */
    public abstract BeanDefinition getBeanDefinition(String beanName);
}
