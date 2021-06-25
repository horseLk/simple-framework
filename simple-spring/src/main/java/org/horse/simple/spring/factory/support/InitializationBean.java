package org.horse.simple.spring.factory.support;

/**
 * 初始化spring中的bean属性
 *
 * @author horse
 * @date 2021/6/15
 */
public interface InitializationBean {
    /**
     * 设置一些不能使用依赖注入进行初始化的属性
     */
    void afterPropertiesSet();
}
