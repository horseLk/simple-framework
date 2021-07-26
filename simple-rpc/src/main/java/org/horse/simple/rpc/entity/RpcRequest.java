package org.horse.simple.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * rpc请求封装
 *
 * @author horse
 * @date 2021/7/26
 */
@Data
@AllArgsConstructor
public class RpcRequest {
    /**
     * traceId
     */
    private String traceId;

    /**
     * 接口全限定名
     */
    private String interfaceName;

    /**
     * 如果是spring则有beanName
     */
    private String beanName;

    /**
     * 调用方法
     */
    private String methodName;

    /**、
     * 参数列表
     */
    private List<Object> args;
}
