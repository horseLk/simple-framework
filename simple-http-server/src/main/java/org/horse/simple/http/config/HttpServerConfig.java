package org.horse.simple.http.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 开启的http服务配置信息类
 * @author horse
 * @date 2021/6/6
 */
public class HttpServerConfig {
    /**
     * http 应用名称
     */
    private static String appName;

    /**
     * http 端口
     */
    private static int port;

    /**
     * url前缀
     */
    private static String urlPrefix = "";

    /**
     * session过期时间，单位：秒
     */
    private static long sessionTimeout;

    /**
     * servlet映射
     */
    private static final Map<String, HttpServletMapping> SERVLET_MAPPING_MAP = Maps.newHashMap();

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        HttpServerConfig.appName = appName;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        HttpServerConfig.port = port;
    }

    public static String getUrlPrefix() {
        return urlPrefix;
    }

    public static void setUrlPrefix(String urlPrefix) {
        HttpServerConfig.urlPrefix = urlPrefix;
    }

    public static long getSessionTimeout() {
        return sessionTimeout;
    }

    public static void setSessionTimeout(long sessionTimeout) {
        HttpServerConfig.sessionTimeout = sessionTimeout;
    }

    /**
     * 添加 url 映射
     * @param servletMapping servletMapping
     */
    public static void put(HttpServletMapping servletMapping) {
        SERVLET_MAPPING_MAP.put(servletMapping.getUri(), servletMapping);
    }

    /**
     * 根据uri找到对应的servlet
     * @param uri uri
     * @return HttpServletMapping
     */
    public static HttpServletMapping get(String uri) {
        return SERVLET_MAPPING_MAP.get(uri);
    }
}
