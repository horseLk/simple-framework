package org.horse.simple.spring.factory.bean;

import lombok.Data;

import java.lang.annotation.Annotation;

/**
 * bean 信息封装
 *
 * @author horse
 * @date 2021/6/15
 */
@Data
public class BeanDefinition {
    /**
     * bean 对应的class对象
     */
    private Class<?> beanClass;

    /**
     * bean使用的注解
     */
    private Class<? extends Annotation> annotation;

    /**
     * 单例/多例
     */
    private String scope;

    /**
     * 是否懒加载
     */
    private boolean isLazy;
}
