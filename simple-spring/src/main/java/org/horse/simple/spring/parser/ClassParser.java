package org.horse.simple.spring.parser;

import org.apache.commons.lang3.StringUtils;
import org.horse.simple.spring.annotation.Component;
import org.horse.simple.spring.annotation.Controller;
import org.horse.simple.spring.annotation.Lazy;
import org.horse.simple.spring.annotation.Repository;
import org.horse.simple.spring.annotation.Scope;
import org.horse.simple.spring.annotation.Service;
import org.horse.simple.spring.factory.bean.BeanDefinition;

import java.lang.annotation.Annotation;

/**
 * 类信息解析类
 *
 * @author horse
 * @date 2021/6/22
 */
public class ClassParser {
    /**
     * 单例
     */
    public static final String SINGLETON = "singleton";

    /**
     * 多例
     */
    public static final String PROTOTYPE = "prototype";

    /**
     * 构造BeanDefinition对象
     *
     * @param clazz 类对象
     * @return BeanDefinition
     */
    public static BeanDefinition getBeanDefinition(Class<?> clazz) {
        // 获取类上的注解对象
        Annotation beanAnnotation = getBeanAnnotation(clazz);
        // 为空表示不交给容器，不处理
        if (beanAnnotation == null) {
            return null;
        }
        // 构建BeanDefinition
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        if (clazz.isAnnotationPresent(Lazy.class)) {
            beanDefinition.setLazy(true);
        }
        beanDefinition.setScope(SINGLETON);
        if (clazz.isAnnotationPresent(Scope.class) && PROTOTYPE.equals(clazz.getAnnotation(Scope.class).value())) {
            beanDefinition.setScope(PROTOTYPE);
        }
        return beanDefinition;
    }

    /**
     * 获取beanName
     *
     * @param clazz 被容器管理的类
     * @return beanName
     */
    public static String getBeanName(Class<?> clazz) {
        Annotation beanAnnotation = getBeanAnnotation(clazz);
        if (beanAnnotation == null) {
            throw new RuntimeException("the class does't need to get bean name, because context don't create it.");
        }
        String value = getValue(beanAnnotation);
        if (StringUtils.isNoneBlank(value)) {
            return value;
        }
        return genDefaultBeanName(clazz.getSimpleName());
    }

    /**
     * 获取一个类的默认beanName
     *
     * @param simpleName 类的simpleName
     * @return beanName
     */
    private static String genDefaultBeanName(String simpleName) {
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    /**
     * 根据beanAnnotation获取 value 值
     *
     * @param beanAnnotation 创建bean的注解
     * @return value值
     */
    private static String getValue(Annotation beanAnnotation) {
        // Component
        if (beanAnnotation instanceof Component) {
            return ((Component) beanAnnotation).value();
        }
        // Repository
        if (beanAnnotation instanceof Repository) {
            return ((Repository) beanAnnotation).value();
        }
        // Service
        if (beanAnnotation instanceof Service) {
            return ((Service) beanAnnotation).value();
        }
        // Controller
        if (beanAnnotation instanceof Controller) {
            return ((Controller) beanAnnotation).value();
        }
        return "";
    }

    /**
     * 获取class使用的注解对象
     *
     * @param clazz class
     * @return 注解对象
     */
    private static Annotation getBeanAnnotation(Class<?> clazz) {
        // Component
        if (clazz.isAnnotationPresent(Component.class)) {
            return clazz.getAnnotation(Component.class);
        }
        // Repository
        if (clazz.isAnnotationPresent(Repository.class)) {
            return clazz.getAnnotation(Repository.class);
        }
        // Service
        if (clazz.isAnnotationPresent(Service.class)) {
            return clazz.getAnnotation(Service.class);
        }
        // Controller
        if (clazz.isAnnotationPresent(Controller.class)) {
            return clazz.getAnnotation(Controller.class);
        }
        return null;
    }
}
