package org.horse.simple.http.config;

import com.google.common.collect.Maps;
import org.horse.simple.http.utils.HttpResponseBuildUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * 告知浏览器如何解析内容
 *
 * @author horse
 * @date 2021/6/20
 */
public class ContentTypeConfig {
    /**
     * properties 文件缓存
     */
    private static final Map<String, String> CONTENT_TYPE_MAPPING = Maps.newHashMap();

    /**
     * 告知浏览器用原格式展示数据
     */
    private static final String ORIGIN_DATA = "text/plain";

    static {
        try {
            Properties prop = new Properties();
            prop.load(HttpResponseBuildUtils.class.getClassLoader().getResourceAsStream("content-type-mapping.properties"));
            Enumeration<?> enumeration = prop.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = prop.getProperty(key);
                CONTENT_TYPE_MAPPING.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("content-type映射关系装载失败");
        }
    }

    /**
     * 根据文件结尾获取Content-Type类型
     *
     * @param suffix 文件类型
     * @return Content-Type
     */
    public static String get(String suffix) {
        return CONTENT_TYPE_MAPPING.getOrDefault(suffix, ORIGIN_DATA);
    }
}
