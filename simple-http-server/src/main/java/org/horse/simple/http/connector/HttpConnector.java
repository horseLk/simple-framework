package org.horse.simple.http.connector;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.common.constant.Constant;
import org.horse.simple.http.config.HttpServerConfig;
import org.horse.simple.http.pojo.HttpConnection;
import org.horse.simple.http.process.HttpProcessor;
import org.horse.simple.common.trace.TraceForRunnable;
import org.horse.simple.common.util.LogUtils;
import org.horse.simple.common.util.NioUtils;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * http 服务 端口监听线程
 * @author horse
 * @date 2021/6/9
 */
public class HttpConnector implements Runnable {
    /**
     * 日志
     */
    private static final Logger LOG = LogManager.getLogger(HttpConnector.class);

    /**
     * 业务处理线程池
     */
    private ExecutorService processorThreadPool;

    public HttpConnector() {
        this.processorThreadPool = new ThreadPoolExecutor(Constant.CORE_POOL_SIZE, Constant.MAXIMUM_POOL_SIZE, Constant.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }


    @Override
    public void run() {
        Selector selector = null;
        try {
            // 打开一个端口并监听这个端口的连接请求，返回监听器
            selector = NioUtils.openService(HttpServerConfig.getPort(), HttpServerConfig.getAppName());
            LogUtils.info(LOG, "http uri prefix: {}", HttpServerConfig.getUrlPrefix());
        } catch (IOException e) {
            LogUtils.error(LOG, e, "open service {} at port {} fail", HttpServerConfig.getAppName(), HttpServerConfig.getPort());
        }

        Preconditions.checkNotNull(selector, "open service fail.");
        while (true) {
            try {
                selector.selectNow();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    handle(selector, key);
                }
            } catch (Throwable ignored) {
                continue;
            }
        }
    }

    /**
     * 连接处理线程
     * @param selector 监听器
     * @param key key
     * @throws IOException exception
     */
    private void handle(Selector selector, SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            SocketChannel channel = ((ServerSocketChannel)key.channel()).accept();
            channel.configureBlocking(false);
            SelectionKey readKey = channel.register(selector, SelectionKey.OP_READ);
            readKey.attach(new HttpConnection(readKey, null, null, null));
        }
        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            HttpConnection httpConnection = (HttpConnection) key.attachment();
            httpConnection.setChannel(socketChannel);
            LogUtils.info(LOG, "build a connection. ");
            // 获取到key的通信管道后就不再监听key了，避免后续不必要的空请求
            key.cancel();
            processorThreadPool.execute(new TraceForRunnable(new HttpProcessor(httpConnection)));
        }
    }
}
