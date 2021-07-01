package org.horse.simple.spring.aop.advice.impl;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.common.util.LogUtils;
import org.horse.simple.spring.aop.advice.AroundAdvice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

/**
 * 日志切面类
 *
 * @author horse
 * @date 2021/6/27
 */
public class LogAroundAdvice implements AroundAdvice {
    /**
     * logger
     */
    private static final Logger LOG = LogManager.getLogger(LogAroundAdvice.class);

    @Override
    public Object around(AopMethodInvocation methodInvocation) {
        long startTime = System.currentTimeMillis();
        LogUtils.info(LOG, "call {}#{} at {}, params: {}", methodInvocation.getTargetSource().getTargetClass(),
                methodInvocation.getMethod().getName(), startTime, JSON.toJSONString(methodInvocation.getArgs()));
        Object result;
        try {
            result = methodInvocation.proceed();
        } catch (Throwable e) {
            LogUtils.error(LOG, e, "call {}#{} fail", methodInvocation.getTargetSource().getTargetClass(),
                    methodInvocation.getMethod().getName());
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        LogUtils.info(LOG, "finish {}#{} at {}, use time: {}, result: {}", methodInvocation.getTargetSource().getTargetClass(),
                methodInvocation.getMethod().getName(), endTime, endTime - startTime, JSON.toJSONString(result));
        return result;
    }
}
