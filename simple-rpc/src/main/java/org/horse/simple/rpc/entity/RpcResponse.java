package org.horse.simple.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装rpc响应信息
 *
 * @author horse
 * @date 2021/7/26
 */
public class RpcResponse {
    /**
     * rpc调用结果封装
     */
    private Result result;

    public RpcResponse success(Object data) {
        this.result = new Result(200, data);
        return this;
    }

    public RpcResponse fail(String msg) {
        this.result = new Result(500, msg);
        return this;
    }

    public Result getResult() {
        return result;
    }

    @Data
    @AllArgsConstructor
    public class Result implements Serializable {
        /**
         * 调用结果代码
         * 200 == 成功, 500 == 失败, 505 == 超时
         */
        private int code;

        /**
         * 调用返回结果
         */
        private Object data;
    }
}
