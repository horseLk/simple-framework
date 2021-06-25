package org.horse.simple.spring.factory;

/**
 * 容器接口
 *
 * @author horse
 * @date 2021/6/15
 */
public interface BeanFactory {
    /**
     * 根据beanName获取一个bean实例
     *
     * @param beanName beanName
     * @return bean
     * @throws Exception 异常信息
     */
    Object getBean(String beanName) throws Exception;
}
