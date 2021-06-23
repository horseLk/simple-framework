package org.horse.simple.http.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求方法枚举类型，目前仅支持GET, POST
 *
 * @author horse
 * @date 2021/6/9
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnum {
    /**
     * get方法
     */
    GET("GET", "get"),

    /**
     * post方法
     */
    POST("POST", "post");

    /**
     * 请求方法标志符号
     */
    private final String type;

    /**
     * 请求方法描述
     */
    private final String desc;

    /**
     * 根据请求方法获取枚举对象
     *
     * @param type type
     * @return RequestMethodEnum
     */
    public static RequestMethodEnum getByType(String type) {
        for (RequestMethodEnum methodEnum : values()) {
            if (StringUtils.equalsIgnoreCase(type, methodEnum.getType())) {
                return methodEnum;
            }
        }
        return null;
    }
}
