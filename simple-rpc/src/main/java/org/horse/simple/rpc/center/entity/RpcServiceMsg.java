package org.horse.simple.rpc.center.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RPC服务信息封装类
 *
 * @author horse
 * @date 2021/7/26
 */
@Data
@AllArgsConstructor
public class RpcServiceMsg {
    /**
     * 开启服务所在机器的ip
     */
    private String ip;

    /**
     * 启服务所在机器的端口
     */
    private int port;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 如果是Spring框架，则使用会传入beanName
     */
    private String beanName;

    public RpcServiceMsg(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
