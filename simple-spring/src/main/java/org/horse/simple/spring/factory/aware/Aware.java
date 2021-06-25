package org.horse.simple.spring.factory.aware;

/**
 * Aware接口
 *
 * @author horse
 * @date 2021/6/24
 */
public interface Aware<T> {
    void setAwareField(T obj);
}
