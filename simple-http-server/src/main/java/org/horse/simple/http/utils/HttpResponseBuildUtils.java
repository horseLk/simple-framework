package org.horse.simple.http.utils;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.http.enums.ResponseStatusEnum;
import org.horse.simple.http.pojo.HttpResponse;
import org.horse.simple.common.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 构建response工具类
 * @author horse
 * @date 2021/6/10
 */
public class HttpResponseBuildUtils {
    /**
     * 日志
     */
    private static final Logger LOG = LogManager.getLogger(HttpResponseBuildUtils.class);

    /**
     *
     * 告知浏览器用 json 格式展示数据
     */
    public static final String JSON_DATA = "application/json";

    /**
     * 告知浏览器用 html 格式展示数据
     */
    public static final String HTML_DATA = "text/html";

    /**
     * 告知浏览器用原格式展示数据
     */
    public static final String ORIGIN_DATA = "text/plain";

    /**
     * png图片格式展示数据
     */
    public static final String PNG_DATA = "image/png";

    /**
     * html 文件结尾
     */
    public static final String HTML_END = ".html";

    /**
     * htm 文件结尾
     */
    public static final String HTM_END = ".htm";

    /**
     * js 文件结尾
     */
    public static final String JS_END = ".js";

    /**
     * png图片结尾
     */
    public static final String PNG_END = ".png";

    /**
     * 给response赋值
     *
     * @param response 被赋值的response
     * @param version http版本
     * @param status status
     * @param dataFormat dataFormat
     */
    public static void buildResponse(HttpResponse response, String version, ResponseStatusEnum status, String dataFormat) {
        response.setVersion(version);
        response.setCode(status.getCode());
        response.setMsg(status.getMsg());

        buildResponseHeaders(response, dataFormat);
    }

    /**
     * 构建响应头
     * @param response 响应
     * @param dataFormat 数据格式
     */
    private static void buildResponseHeaders(HttpResponse response, String dataFormat) {
        response.getHeaders().put("Content-Type", dataFormat + "\r\n");
        response.getHeaders().put("Accept-Ranges", "bytes\r\n");
        response.getHeaders().put("Connection", "close\r\n");

        // 开启跨域的响应头
        response.getHeaders().put("Access-Control-Allow-Origin", "*\r\n");
        response.getHeaders().put("Access-Control-Allow-Credentials", "true\r\n");
        response.getHeaders().put("Access-Control-Allow-Method", "GET,POST,OPTION\r\n");
        response.getHeaders().put("Access-Control-Allow-Headers", "Access-Control-Allow-Headers,Origin,Accept,X-Request-With," +
                "Content-Type,Access-Control-Allow-Method,Access-Control-Request-Headers");
    }

    /**
     * 把数据对象json格式化后转换为OutputStream
     * @param obj object
     * @return OutputStream
     */
    public static ByteArrayOutputStream genOutputStream(Object obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            if (obj == null) {
                return outputStream;
            }
            String json = JSON.toJSONString(obj);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));

            return outputStream;
        } catch (Throwable e) {
            LogUtils.error(LOG, e, "generate OutputStream by Object {} fail.", JSON.toJSONString(obj));
            return new ByteArrayOutputStream();
        }
    }

    /**
     * 将 response 数据写入到byteBuffer中
     * @param response response
     * @return ByteBuffer
     */
    public static ByteBuffer genByteBuffer(HttpResponse response) {
        // builder 中保存首行和头部信息
        StringBuilder builder = new StringBuilder();
        builder.append(response.getVersion()).append(" ").append(response.getCode()).append(" ").append(response.getMsg()).append("\r\n");
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            builder.append(header.getKey()).append(": ").append(header.getValue());
        }
        builder.append("\r\n");
        byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        // body中的数据
        int outputStreamSize = response.getBody().size();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length + outputStreamSize);
        buffer.put(bytes);
        buffer.put(response.getBody().toByteArray());

        return buffer;
    }
}
