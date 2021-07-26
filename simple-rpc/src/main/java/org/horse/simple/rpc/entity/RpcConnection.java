package org.horse.simple.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 封装rpc连接的上下文信息
 * @author horse
 * @date 2021/7/26
 */
@Data
@AllArgsConstructor
public class RpcConnection {
    /**
     * 请求源地址
     */
    private String src;

    /**
     * fd
     */
    private SelectionKey key;

    /**
     * 通信管道
     */
    private SocketChannel channel;

    /**
     * 请求信息
     */
    private RpcRequest request;

    /**
     * 响应信息
     */
    private RpcResponse response;
}
