package org.horse.simple.spring.aop.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 被代理元数据
 *
 * @author horse
 * @date 2021/6/26
 */
@Data
@AllArgsConstructor
public class AdvisedSupport {
    /**
     * 被代理对象数据
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;
}
