package org.horse.simple.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * rpc客户端配置信息
 *
 * @author horse
 * @date 2021/7/26
 */
@Data
@AllArgsConstructor
public class RpcClientConfig {
    /**
     * 注册中心ip
     */
    private final String centerIp;

    /**
     * 注册中心端口
     */
    private final int centerPort;
}
