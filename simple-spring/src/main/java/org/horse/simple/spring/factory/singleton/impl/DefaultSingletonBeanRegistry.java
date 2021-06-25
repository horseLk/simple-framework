package org.horse.simple.spring.factory.singleton.impl;

import com.google.common.collect.Maps;
import org.horse.simple.spring.factory.singleton.SingletonBeanRegistry;

import java.util.Map;

/**
 * 默认的单例池实现类
 *
 * @author horse
 * @date 2021/6/15
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 单例池
     */
    private final Map<String, Object> SINGLE_BEAN_MAP = Maps.newHashMap();

    @Override
    public Object getSingleton(String beanName) {
        return SINGLE_BEAN_MAP.get(beanName);
    }

    /**
     * 向单例池注册bean
     *
     * @param beanName beanName
     * @param bean     bean对象
     */
    public void addSingleton(String beanName, Object bean) {
        SINGLE_BEAN_MAP.put(beanName, bean);
    }
}
