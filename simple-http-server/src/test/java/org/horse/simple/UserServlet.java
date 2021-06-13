package org.horse.simple;

import org.horse.simple.http.pojo.HttpRequest;
import org.horse.simple.http.pojo.HttpResponse;
import org.horse.simple.http.support.http.BaseHttpServlet;

/**
 * 测试 http servlet
 */
public class UserServlet extends BaseHttpServlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

        response.write(request);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
