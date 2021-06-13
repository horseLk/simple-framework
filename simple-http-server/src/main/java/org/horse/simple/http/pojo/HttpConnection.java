package org.horse.simple.http.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 封装http连接信息
 * @author horse
 * @date 2021/6/6
 */
@Data
@AllArgsConstructor
public class HttpConnection {
    /**
     * 被监听的fd key
     */
    private SelectionKey key;

    /**
     * 通信channel
     */
    private SocketChannel channel;

    /**
     * http 请求对象
     */
    private HttpRequest request;

    /**
     * http响应对象
     */
    private HttpResponse response;
}
