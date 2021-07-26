package org.horse.simple.rpc.config;

/**
 * rpc配置存储，包括客户端配置和服务端配置
 *
 * @author horse
 * @date 2021/7/26
 */
public class RpcConfigCache {
    private static RpcServerConfig localServerConfig;
    private static RpcClientConfig localClientConfig;

    public static void init(RpcServerConfig serverConfig, RpcClientConfig clientConfig) {
        if (serverConfig != null) {
            localServerConfig = new RpcServerConfig(serverConfig.getLocalPort(), serverConfig.getCenterIp(), serverConfig.getCenterPort());
        }
        if (clientConfig != null) {
            localClientConfig = new RpcClientConfig(clientConfig.getCenterIp(), clientConfig.getCenterPort());
        }
    }

    public static RpcServerConfig getServerConfig() {
        return localServerConfig;
    }

    public static RpcClientConfig getClientConfig() {
        return localClientConfig;
    }

    private RpcConfigCache(){
    }
}
