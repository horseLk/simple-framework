package org.horse.simple.http.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * response 状态枚举类
 * @author horse
 * @date 2021/6/9
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {
    /**
     * 200
     */
    OK(200, "OK"),

    /**
     * 404
     */
    NOT_FOUND(404, "Resource Not Found"),

    /**
     * 500
     */
    SERVER_ERROR(500, "App Service ERROR"),
    ;

    /**
     * 状态值
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;
}
