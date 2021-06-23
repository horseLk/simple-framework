package org.horse.simple.http.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.horse.simple.http.enums.SimpleHttpExceptionEnum;

/**
 * 统一的异常处理
 *
 * @author horse
 * @date 2021/6/9
 */
@Data
@AllArgsConstructor
public class SimpleHttpException extends Exception {
    /**
     * 未知错误码
     */
    private static final String UNKNOW_EXCEPTION_CODE = "unknow_exception_code";

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String errorMsg;

    public SimpleHttpException(SimpleHttpExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.errorMsg = exceptionEnum.getErrorMsg();
    }

    /**
     * 根据已经抛出的异常构造 SimpleHttpException
     *
     * @param e exception
     */
    public SimpleHttpException(Throwable e) {
        this.code = UNKNOW_EXCEPTION_CODE;
        this.errorMsg = e.getMessage();
    }


}
