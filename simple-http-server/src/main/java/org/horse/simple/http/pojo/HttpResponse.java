package org.horse.simple.http.pojo;

import com.google.common.collect.Maps;
import lombok.Data;
import org.horse.simple.http.utils.HttpResponseBuildUtils;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 响应数据封装
 *
 * @author horse
 * @date 2021/6/6
 */
@Data
public class HttpResponse extends BaseResponse {
    /**
     * http版本
     */
    private String version;

    /**
     * 响应代码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应头
     */
    private Map<String, String> headers;

    /**
     * 响应体
     */
    private ByteArrayOutputStream body;

    /**
     * 构造函数
     */
    public HttpResponse() {
        this.headers = Maps.newHashMap();
        body = new ByteArrayOutputStream();
    }

    /**
     * 用于封装结果
     *
     * @param obj obj
     */
    public void write(Object obj) {
        this.body = HttpResponseBuildUtils.genOutputStream(obj);
    }
}
