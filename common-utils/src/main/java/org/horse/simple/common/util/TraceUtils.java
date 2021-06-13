package org.horse.simple.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

import static org.horse.simple.common.constant.Constant.TRACE_TAG;

/**
 * tracer 跟踪工具类
 * @author horse
 * @date 2021/6/6
 */
public class TraceUtils {

    /**
     * 设置线程 traceId
     * @param traceId
     */
    public static void setTraceId(String traceId) {
        // 如果该线程目前没有traceId，则为其生成一个
        if (StringUtils.isEmpty(traceId)) {
            ThreadContext.put(TRACE_TAG, genTraceId());
            return;
        }
        ThreadContext.put(TRACE_TAG, traceId);
    }

    /**
     * 获取当前的 traceId
     * @return traceId
     */
    public static String getTraceId() {
        return ThreadContext.get(TRACE_TAG);
    }

    /**
     * 清空TraceId
     */
    public static void clearTraceId() {
        ThreadContext.remove(TRACE_TAG);
    }

    /**
     * 生成一个traceId
     * @return uuid
     */
    private static String genTraceId() {
        return UUID.randomUUID().toString();
    }
}
