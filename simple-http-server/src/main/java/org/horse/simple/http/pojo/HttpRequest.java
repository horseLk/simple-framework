package org.horse.simple.http.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.horse.simple.http.enums.RequestMethodEnum;

import java.util.Map;

/**
 * 请求数据封装
 *
 * @author horse
 * @date 2021/6/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequest extends BaseRequest {
    /**
     * 请求方法
     */
    private RequestMethodEnum requestMethod;

    /**
     * 请求资源地址
     */
    private String uri;

    /**
     * http版本
     */
    private String version;

    /**
     * 请求头数据
     */
    private Map<String, String> headers;

    /**
     * 请求参数
     */
    private Map<String, String> query;

    /**
     * 请求体数据
     */
    private String requestBody;

}
