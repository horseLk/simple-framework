package org.horse.simple.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * rpc服务端配置信息
 *
 * @author horse
 * @date 2021/7/26
 */
@Data
@AllArgsConstructor
public class RpcServerConfig {
    /**
     * 本地rpc服务端口
     */
    private final int localPort;

    /**
     * 注册中心ip
     */
    private final String centerIp;

    /**
     * 注册中心端口
     */
    private final int centerPort;
}
