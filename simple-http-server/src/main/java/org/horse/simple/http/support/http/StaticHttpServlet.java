package org.horse.simple.http.support.http;

import org.horse.simple.http.enums.ResponseStatusEnum;
import org.horse.simple.http.pojo.HttpRequest;
import org.horse.simple.http.pojo.HttpResponse;
import org.horse.simple.http.utils.HttpResponseBuildUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 请求静态资源的servlet
 * @author horse
 * @date 2021/6/9
 */
public class StaticHttpServlet extends BaseHttpServlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        // 去掉uri前面的 / 符号
        URL resource = HttpResponseBuildUtils.class.getClassLoader().getResource(request.getUri().substring(1));
        // 请求的静态文件存在不为空
        if (resource != null) {
            try {
                // 根据静态文件生成OutputStream
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resource.getFile()));
                ByteArrayOutputStream outputStream = genOutputStream(inputStream);
                // 给response设置OutputStream
                response.setBody(outputStream);
                // 根据请求文件类型设置浏览器显示文件的格式
                if (request.getUri().endsWith(HttpResponseBuildUtils.HTML_END) || request.getUri().endsWith(HttpResponseBuildUtils.HTM_END)) {
                    HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.OK, HttpResponseBuildUtils.HTML_DATA);
                } else if (request.getUri().endsWith(HttpResponseBuildUtils.JS_END)) {
                    HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.OK, HttpResponseBuildUtils.ORIGIN_DATA);
                } else if (request.getUri().endsWith(HttpResponseBuildUtils.PNG_END)){
                    HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.OK, HttpResponseBuildUtils.PNG_DATA);
                } else {
                    HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.OK, HttpResponseBuildUtils.ORIGIN_DATA);
                }
            } catch (Exception e) {
                // 异常处理暂时统一为文件不存在
                response.write(ResponseStatusEnum.NOT_FOUND);
                HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.NOT_FOUND, HttpResponseBuildUtils.HTML_DATA);
            }
        } else { // 请求的静态文件不存在
            response.write(ResponseStatusEnum.NOT_FOUND);
            HttpResponseBuildUtils.buildResponse(response, request.getVersion(), ResponseStatusEnum.NOT_FOUND, HttpResponseBuildUtils.HTML_DATA);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        doGet(request, response);
    }

    /**
     * 把数据从文件中读取到OutputStream
     * @param inputStream 静态文件数据
     */
    private ByteArrayOutputStream genOutputStream(BufferedInputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bys = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(bys)) != -1) {
            outputStream.write(bys, 0, len);
        }
        return outputStream;
    }
}
