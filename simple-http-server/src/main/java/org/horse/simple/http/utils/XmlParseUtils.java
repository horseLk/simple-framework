package org.horse.simple.http.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.horse.simple.http.config.HttpServerConfig;
import org.horse.simple.http.config.HttpServletMapping;
import org.horse.simple.common.util.LogUtils;

import java.net.URL;
import java.util.Iterator;

/**
 * 解析xml文件的工具类
 *
 * @author horse
 * @date 2021/6/7
 */
public class XmlParseUtils {
    /**
     * logger
     */
    private static final Logger LOG = LogManager.getLogger(XmlParseUtils.class);

    /**
     * xml文件数据
     */
    private static Document document;

    static {
        try {
            init();
        } catch (Exception e) {
            LogUtils.error(LOG, e, "load web.xml error.");
            System.exit(-1);
        }
    }

    private static void init() throws Exception {
        SAXReader saxReader = new SAXReader();
        URL resource = XmlParseUtils.class.getClassLoader().getResource("web.xml");
        if (resource == null) {
            throw new Exception("找不到web.xml文件进行初始化.");
        }
        document = saxReader.read(resource.getFile());
    }

    public static void loadConfig() throws ClassNotFoundException {
        Element rootElement = document.getRootElement();
        String appName = rootElement.elementText("display-name");
        // 设置appName
        HttpServerConfig.setAppName(appName);
        Element configElement = rootElement.element("config");
        int port = Integer.parseInt(configElement.element("start-config").elementText("port"));
        // 设置port
        HttpServerConfig.setPort(port);
        // 设置uri前缀
        HttpServerConfig.setUrlPrefix(configElement.elementText("url-pre"));
        long sessionTimeout = Long.parseLong(configElement.element("session-config").elementText("timeout"));
        // 设置session到期时间
        HttpServerConfig.setSessionTimeout(sessionTimeout);

        Element servletElement = rootElement.element("servlets");
        Iterator<?> iterator = servletElement.elementIterator();
        while (iterator.hasNext()) {
            Element cur = (Element) iterator.next();
            String servletName = cur.elementText("servlet-name");
            String className = cur.elementText("servlet-class");
            String uri = cur.elementText("uri-mapping");
            Class<?> clazz = Class.forName(className);
            // 插入 servlet 映射
            HttpServerConfig.put(new HttpServletMapping(servletName, clazz, uri));
        }
    }
}
