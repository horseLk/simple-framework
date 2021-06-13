package org.horse.simple.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.http.connector.HttpConnector;
import org.horse.simple.http.utils.XmlParseUtils;
import org.horse.simple.common.trace.TraceForRunnable;
import org.horse.simple.common.util.LogUtils;
import org.horse.simple.common.util.TraceUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http服务启动类
 * @author horse
 * @date 2021/6/6
 */
public class HttpServer {
    /**
     * 日志
     */
    private static final Logger LOG = LogManager.getLogger(HttpServer.class);

    /**
     * http 服务线程池
     */
    private static final ExecutorService SINGLE_POOL = Executors.newSingleThreadExecutor();

    /**
     * 开启http服务
     */
    public static void start() {
        TraceUtils.setTraceId(null);
        try {
            // 载入xml文件中的http配置
            XmlParseUtils.loadConfig();
            // 开启服务
            SINGLE_POOL.submit(new TraceForRunnable(new HttpConnector()));
        } catch (Exception e) {
            LogUtils.error(LOG, e, "start http server error.");
            System.exit(-1);
        }
    }
}
