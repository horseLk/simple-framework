package org.horse.simple.spring.processor;

import org.horse.simple.spring.factory.aware.BaseApplicationContextAware;
import org.horse.simple.spring.factory.impl.BaseApplicationContext;
import org.horse.simple.spring.factory.support.BeanPostProcessor;

/**
 * 感知BaseApplicationContextAware的PostProcessor
 *
 * @author horse
 * @date 2021/6/25
 */
public class ApplicationContextAwarePostProcessor implements BeanPostProcessor {
    /**
     * 上下文数据
     */
    private final BaseApplicationContext context;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ApplicationContextAwarePostProcessor(BaseApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (bean instanceof BaseApplicationContextAware) {
            ((BaseApplicationContextAware) bean).setAwareField(this.context);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        return bean;
    }
}
