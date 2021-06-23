package org.horse.simple.http.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ServletMapping 数据
 *
 * @author horse
 * @date 2021/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpServletMapping {
    /**
     * servletName
     */
    private String servletName;
    /**
     * className
     */
    private Class<?> servletClass;
    /**
     * uri
     */
    private String uri;
}
