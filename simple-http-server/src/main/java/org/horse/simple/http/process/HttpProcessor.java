package org.horse.simple.http.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.http.config.HttpServerConfig;
import org.horse.simple.http.config.HttpServletMapping;
import org.horse.simple.http.enums.ResponseStatusEnum;
import org.horse.simple.http.enums.SimpleHttpExceptionEnum;
import org.horse.simple.http.pojo.HttpConnection;
import org.horse.simple.http.pojo.HttpRequest;
import org.horse.simple.http.pojo.HttpResponse;
import org.horse.simple.http.pojo.SimpleHttpException;
import org.horse.simple.http.support.Servlet;
import org.horse.simple.http.support.http.StaticHttpServlet;
import org.horse.simple.http.utils.HttpRequestParseUtils;
import org.horse.simple.http.utils.HttpResponseBuildUtils;
import org.horse.simple.common.util.LogUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;


/**
 * http 业务转发处理
 * @author horse
 * @date 2021/6/8
 */
public class HttpProcessor implements Runnable {

    /**
     * 日志
     */
    private static final Logger LOG = LogManager.getLogger(HttpProcessor.class);

    /**
     * http 上下文连接对象
     */
    private final HttpConnection connection;

    /**
     * constructor
     * @param connection HttpConnection
     */
    public HttpProcessor(HttpConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        LogUtils.info(LOG, "build connection successful, start process request.");
        try {
            /**
             * 解析 http 报文
             */
            HttpRequest httpRequest = decode();
            System.out.println(httpRequest);
            this.connection.setRequest(httpRequest);
            this.connection.setResponse(new HttpResponse());
            // 业务处理
            process();
            // 数据写回
            doWrite();

        } catch (SimpleHttpException e) {
            LogUtils.error(LOG, e, "process request fail. {}", e.getMessage());
        } finally {
            releaseResource();
        }
    }

    /**
     * 数据写回到客户端
     */
    private void doWrite() throws SimpleHttpException {
        try {
            //生成buffer并写进管道
            ByteBuffer buffer = HttpResponseBuildUtils.genByteBuffer(this.connection.getResponse());
            buffer.flip();
            connection.getChannel().write(buffer);
        } catch (Throwable e) {
            LogUtils.error(LOG, e, "write response data to client fail.");
            throw new SimpleHttpException(SimpleHttpExceptionEnum.RESPONSE_WRITE_ERROR);
        }
    }

    /**
     * 具体业务处理函数
     */
    private void process() {
        HttpRequest request = this.connection.getRequest();
        HttpResponse response = this.connection.getResponse();
        // 检查是否配置了 uri 映射
        HttpServletMapping servletMapping = HttpServerConfig.get(request.getUri());
        if (servletMapping != null || isStaticRequest(request.getUri())) {
            try {
                // 初始化 servlet 并执行service 方法
                Servlet servlet;
                if (servletMapping != null) {
                    servlet = (Servlet) servletMapping.getServletClass().newInstance();
                } else {
                    servlet = new StaticHttpServlet();
                }
                servlet.service(request, response);
            } catch (Throwable e) {
                // 构建 response 数据（服务器异常）
                response.write(ResponseStatusEnum.SERVER_ERROR);
                HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.SERVER_ERROR, HttpResponseBuildUtils.HTML_DATA);
            }
        } else {
            // 404 找不到资源异常
            response.write(ResponseStatusEnum.NOT_FOUND);
            HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.NOT_FOUND, HttpResponseBuildUtils.HTML_DATA);
        }
    }

    /**
     * 判断是否是请求静态资源
     * @param uri uri
     * @return boolean
     */
    private boolean isStaticRequest(String uri) {
        if (uri.endsWith(HttpResponseBuildUtils.HTM_END) || uri.endsWith(HttpResponseBuildUtils.HTML_END)
                || uri.endsWith(HttpResponseBuildUtils.JS_END)) {
            return true;
        }
        return false;
    }

    /**
     * 解析请求报文生成HttpRequest
     * @return 返回HttpRequest对象
     */
    private HttpRequest decode() throws SimpleHttpException {
        try {
            // 读取数据到StringBuilder中
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder builder = new StringBuilder();
            while (this.connection.getChannel().read(buffer) > 0) {
                buffer.flip();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
                builder.append(charBuffer);
                buffer.clear();
            }
            String requestStr = URLDecoder.decode(builder.toString(), "UTF-8");
            // 解析数据并返回
            return HttpRequestParseUtils.parse(requestStr);
        } catch (Throwable e) {
            LogUtils.error(LOG, e, "decode request data fail.");
            throw new SimpleHttpException(SimpleHttpExceptionEnum.PARSE_REQUEST_ERROR);
        }
    }

    /**
     * 释放连接中的资源
     */
    private void releaseResource() {
        try {
            if (this.connection.getChannel() != null) {
                this.connection.getChannel().close();
            }
            if (this.connection.getKey() != null) {
                this.connection.getKey().cancel();
            }
        } catch (IOException e) {
            LogUtils.error(LOG, e, "release resource fail.");
        }
    }
}
