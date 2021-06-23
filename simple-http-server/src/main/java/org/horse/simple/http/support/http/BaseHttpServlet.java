package org.horse.simple.http.support.http;

import org.apache.commons.lang3.StringUtils;
import org.horse.simple.http.enums.RequestMethodEnum;
import org.horse.simple.http.enums.ResponseStatusEnum;
import org.horse.simple.http.pojo.BaseRequest;
import org.horse.simple.http.pojo.BaseResponse;
import org.horse.simple.http.pojo.HttpRequest;
import org.horse.simple.http.pojo.HttpResponse;
import org.horse.simple.http.support.Servlet;
import org.horse.simple.http.utils.HttpResponseBuildUtils;

/**
 * HttpServlet 基类
 *
 * @author horse
 * @date 2021/6/7
 */
public abstract class BaseHttpServlet implements Servlet {
    /**
     * get请求方法调用
     *
     * @param request  request
     * @param response response
     */
    public abstract void doGet(HttpRequest request, HttpResponse response);

    /**
     * post请求方法调用
     *
     * @param request  request
     * @param response response
     */
    public abstract void doPost(HttpRequest request, HttpResponse response);

    @Override
    public void init() {
        // init
    }

    @Override
    public void service(BaseRequest request, BaseResponse response) {
        HttpRequest httpRequest = (HttpRequest) request;
        HttpResponse httpResponse = (HttpResponse) response;
        if (httpRequest.getRequestMethod() == RequestMethodEnum.GET) {
            doGet(httpRequest, httpResponse);
        } else {
            doPost(httpRequest, httpResponse);
        }
        // 如果是静态请求则response已经被构建完成
        if (StringUtils.isEmpty(httpResponse.getVersion())) {
            // 构建 response 数据
            HttpResponseBuildUtils.buildResponse(httpResponse, httpRequest.getVersion(), ResponseStatusEnum.OK,
                    HttpResponseBuildUtils.JSON_DATA);
        }
    }

    @Override
    public void destroy() {
        // destroy
    }
}
