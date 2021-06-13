package org.horse.simple.http.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 已知异常枚举类
 * @author horse
 * @date 2021/6/9
 */
@Getter
@AllArgsConstructor
public enum SimpleHttpExceptionEnum {

    /**
     * 解析http请求异常
     */
    PARSE_REQUEST_ERROR("parse_request_error", "解析http请求失败"),

    /**
     * 请求资源不是文件
     */
    NOT_FILE_ERROR("not_file_error", "请求资源不是文件"),

    /**
     * 写回响应数据异常
     */
    RESPONSE_WRITE_ERROR("write_response_error", "写回数据异常"),


    RELEASE_RESOURCE_ERROR("release_resource_error", "释放资源失败")
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String errorMsg;
}
