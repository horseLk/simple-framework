package org.horse.simple.common.trace;

import org.horse.simple.common.util.TraceUtils;

import java.util.concurrent.Callable;

public class TraceForCallable implements Callable {
    /**
     * 被执行任务
     */
    private final Callable task;

    /**
     * traceId
     */
    private String traceId;

    public TraceForCallable(Callable task) {
        this.task = task;
    }

    public TraceForCallable(Callable task, String traceId) {
        this.task = task;
        this.traceId = traceId;
    }

    @Override
    public Object call() throws Exception {
        // 将traceId与执行线程绑定
        TraceUtils.setTraceId(this.traceId);
        // 执行任务线程
        Object ret = this.task.call();
        // 清空这个线程的traceId信息
        // 下一个任务从线程池拿到线程后才能设置本身的traceId
        TraceUtils.clearTraceId();
        return ret;
    }
}
