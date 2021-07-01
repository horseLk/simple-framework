package org.horse.simple.spring.aop.proxy;

import lombok.Data;
import org.horse.simple.spring.aop.support.AdvisedSupport;

/**
 * 创建代理类的基类
 *
 * @author horse
 * @date 2021/6/27
 */
@Data
public abstract class BaseAopProxy {
    /**
     * AdvisedSupport
     */
    protected AdvisedSupport  advisedSupport;

    /**
     * 获取代理对象
     * @return 代理对象
     */
    public abstract Object getProxyObject();
}
