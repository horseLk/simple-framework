package org.horse.simple.http.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.horse.simple.http.config.HttpServerConfig;
import org.horse.simple.http.enums.RequestMethodEnum;
import org.horse.simple.http.pojo.HttpRequest;

import java.util.Map;

/**
 * 请求数据解析为请求对象
 * @author horse
 * @date 2021/6/9
 */
public class HttpRequestParseUtils {
    /**
     * 按行分割字符串
     */
    private static final String LINE_SPLIT = "\r\n";

    /**
     * 按空格分割字符串
     */
    private static final String ONE_SPACE = " ";

    /**
     * 按问号分分割字符串
     */
    private static final String QUESTION_MARK = "\\?";

    /**
     * 按照 & 分割字符串
     */
    private static final String AND_SPLIT = "&";

    /**
     * 按照 & 分割字符串
     */
    private static final String EQUALS_SPLIT = "=";

    /**
     * 按照 : 分割字符串
     */
    private static final String COLON_SPLIT = ":";


    /**
     * 解析http报文
     * 解析成功返回HttpRequest对象，否则返回null
     * @param requestStr 报文
     * @return HttpRequest
     */
    public static HttpRequest parse(String requestStr) {
        HttpRequest httpRequest = new HttpRequest();
        // 逐行分个字符串
        String[] messageOfLine = requestStr.split(LINE_SPLIT);
        // 第一行字符（method,version和uri)
        String[] simpleMessages = messageOfLine[0].split(ONE_SPACE);
        httpRequest.setRequestMethod(RequestMethodEnum.getByType(simpleMessages[0]));
        String uri = simpleMessages[1];
        // 分割uri
        String[] uriAndQury = uri.split(QUESTION_MARK);
        // 设置uri
        httpRequest.setUri(uriAndQury[0].substring(HttpServerConfig.getUrlPrefix().length()));
        // 设置query params
        if (uriAndQury.length > 1) {
            httpRequest.setQuery(parseQuery(uriAndQury[1]));
        }
        httpRequest.setVersion(simpleMessages[2]);

        // request body 的字符串开始处
        int bodyIndex = -1;
        httpRequest.setHeaders(Maps.newHashMap());
        for (int i = 1; i < messageOfLine.length; i++) {
            if (StringUtils.isBlank(messageOfLine[i])) {
                bodyIndex = i + 1;
                break;
            }
            // 如果是request header 则一定能按照parseHeader的方法分割
            String[] header = messageOfLine[i].split(COLON_SPLIT);
            httpRequest.getHeaders().put(header[0].trim(), header[1].trim());
        }
        if (httpRequest.getRequestMethod() != RequestMethodEnum.GET) {
            httpRequest.setRequestBody(parseRequestBody(messageOfLine[bodyIndex]));
        }
        return httpRequest;
    }

    /**
     * 将请求体转换为json字符串
     * TODO: 前端表单提交的请求体是 a=b&c=d 格式的，此处暂未实现
     * @param bodyStr 请求体内容
     * @return json
     */
    private static String parseRequestBody(String bodyStr) {
        return bodyStr.replaceAll(" ", "")
                .replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 解析request params中的参数
     * @param queryStr queryStr
     * @return Map<String, String>
     */
    private static Map<String, String> parseQuery(String queryStr) {
        Map<String, String> queryMap = Maps.newHashMap();
        String[] queries = queryStr.split(AND_SPLIT);
        for (String query : queries) {
            int index = query.indexOf(EQUALS_SPLIT);
            queryMap.put(query.substring(0, index), query.substring(index + 1));
        }
        return queryMap;
    }
}
