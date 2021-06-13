package org.horse.simple.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import static org.horse.simple.common.constant.Constant.MAX_CONNECTION;

public class NioUtils {
    /**
     * 日志
     */
    private static final Logger LOG = LogManager.getLogger(NioUtils.class);

    public static Selector openService(int port, String serviceName) throws IOException {
        LogUtils.info(LOG, "try open service {} at port {}", serviceName, port);
        // 开启selector
        Selector selector = Selector.open();
        // 开启监听管道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        // 绑定端口和最大连接数
        socketChannel.socket().bind(new InetSocketAddress(port), MAX_CONNECTION);
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 监听管道注册到selector
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        LogUtils.info(LOG, "open {} service at port {}.", serviceName, port);
        return selector;
    }
}
