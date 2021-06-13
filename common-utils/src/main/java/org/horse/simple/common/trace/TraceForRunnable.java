package org.horse.simple.common.trace;

import org.horse.simple.common.util.TraceUtils;

public class TraceForRunnable implements Runnable {
    /**
     * 被执行任务
     */
    private final Runnable task;

    /**
     * traceId
     */
    private String traceId;

    public TraceForRunnable(Runnable task) {
        this.task = task;
    }

    public TraceForRunnable(Runnable task, String traceId) {
        this.task = task;
        this.traceId = traceId;
    }

    @Override
    public void run() {
        // 为线程绑定traceId
        TraceUtils.setTraceId(this.traceId);
        // 执行线程任务
        this.task.run();
        // 清空traceId信息
        TraceUtils.clearTraceId();

    }
}
