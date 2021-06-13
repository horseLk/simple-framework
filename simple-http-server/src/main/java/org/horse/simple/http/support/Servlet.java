package org.horse.simple.http.support;

import org.horse.simple.http.pojo.BaseRequest;
import org.horse.simple.http.pojo.BaseResponse;

/**
 * servlet 接口类
 * @author horse
 * @date 2021/6/7
 */
public interface Servlet {
    /**
     * servlet初始化方法
     */
    void init();

    /**
     * service 方法
     * @param request 请求数据
     * @param response 响应数据
     * @return 返回数据
     */
    void service(BaseRequest request, BaseResponse response);

    /**
     * 销毁servlet
     */
    void destroy();
}
