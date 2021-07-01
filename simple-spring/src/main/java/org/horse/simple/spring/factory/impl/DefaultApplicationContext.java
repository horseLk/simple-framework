package org.horse.simple.spring.factory.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.common.util.LogUtils;
import org.horse.simple.spring.annotation.Autowire;
import org.horse.simple.spring.annotation.Configuration;
import org.horse.simple.spring.annotation.Value;
import org.horse.simple.spring.factory.BeanDefinitionRegistry;
import org.horse.simple.spring.factory.bean.BeanDefinition;
import org.horse.simple.spring.factory.processor.BeanPostProcessorFactory;
import org.horse.simple.spring.factory.support.BeanPostProcessor;
import org.horse.simple.spring.factory.support.InitializationBean;
import org.horse.simple.spring.parser.ClassParser;
import org.horse.simple.spring.parser.ComponentScanParser;
import org.horse.simple.spring.processor.AopPostProcessor;
import org.horse.simple.spring.processor.ApplicationContextAwarePostProcessor;
import org.horse.simple.spring.processor.BeanNameAwarePostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 默认的ApplicationContext
 *
 * @author horse
 * @date 2021/7/1
 */
public class DefaultApplicationContext extends BaseApplicationContext implements BeanDefinitionRegistry, BeanPostProcessorFactory {
    /**
     * LOG
     */
    private static final Logger LOG = LogManager.getLogger(DefaultApplicationContext.class);

    /**
     * beanDefinition 容器
     */
    private final Map<String, BeanDefinition> BEAN_DEFINITION_MAP = Maps.newHashMap();

    /**
     * 第三方包还未注册完成的半成品
     */
    private final Map<String, Object> THIRD_HALF_BEAN = Maps.newHashMap();

    /**
     * 第三方包中被注册对象的映射
     */
    private final Map<String, Class<?>> THIRD_BEAN_CLASS = Maps.newHashMap();

    private final Class<?> configClass;

    public DefaultApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        LogUtils.info(LOG, "start initialize appication context");
        try {
            initializeApplicationContext();
        } catch (Exception e) {
            LogUtils.error(LOG, e, "initial application context error.");
            System.exit(-1);
        }

    }

    /**
     * 初始化容器
     */
    private void initializeApplicationContext() throws Exception {
        // 容器初始化完成，直接返回
        if (isInitial) {
            return;
        }
        // 把框架中已经有的BeanPostProcessor注册到容器
        registerSystemBeanPostProcessor();

        // 查询ComponentScan扫描到的类
        List<Class<?>> scanClassList = ComponentScanParser.findScanClassSet(configClass);
        for (Class<?> clazz : scanClassList) {
            // BeanPostProcessor 则注册到BeanPostProcessor容器
            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                registerBeanPostProcessor(beanPostProcessor);
                continue;
            }
            // Configuration 配置类
            if (clazz.isAnnotationPresent(Configuration.class)) {
                registerThirdBeanHalf(clazz);
            }
            // 普通类
            BeanDefinition beanDefinition = ClassParser.getBeanDefinition(clazz);
            //不需要容器托管
            if (beanDefinition == null) {
                continue;
            }
            String beanName = ClassParser.getBeanName(clazz);
            registerBeanDefinition(beanName, beanDefinition);
        }
        // 遍历所有的beanDefinition
        for (Map.Entry<String, BeanDefinition> entry : BEAN_DEFINITION_MAP.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            // 单例且非懒加载才在容器初始化的时候就实例化
            if (!beanDefinition.isLazy() && ClassParser.SINGLETON.equals(beanDefinition.getScope())) {
                createBean(entry.getKey(), beanDefinition);
            }
        }
        // 遍历所有的三方包中的对象并注入到容器中
        for (Map.Entry<String, Class<?>> entry : THIRD_BEAN_CLASS.entrySet()) {
            Object bean = THIRD_HALF_BEAN.get(entry.getKey());
            Class<?> beanClazz = entry.getValue();
            Field[] declaredFields = beanClazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldObj = getBean(fieldName);
                if (fieldObj == null) {
                    fieldObj = THIRD_HALF_BEAN.get(fieldName);
                }
                field.set(bean, fieldObj);
            }
            addSingleton(entry.getKey(), bean);
        }
        // 更改状态为已经初始化
        isInitial = true;
    }

    /**
     * 注册第三方包中的类对象到容器
     * @param clazz Configuration类
     */
    private void registerThirdBeanHalf(Class<?> clazz) throws Exception {
        // 只注册public方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String beanName = method.getName();
            Class<?> returnType = method.getReturnType();
            THIRD_HALF_BEAN.put(beanName, returnType.getDeclaredConstructor().newInstance());
            THIRD_BEAN_CLASS.put(beanName, returnType);
        }
    }

    /**
     * 注册系统的 BeanPostProcessor到容器
     */
    private void registerSystemBeanPostProcessor() {
        registerBeanPostProcessor(new BeanNameAwarePostProcessor());
        registerBeanPostProcessor(new ApplicationContextAwarePostProcessor(this));
        registerBeanPostProcessor(new AopPostProcessor());
    }


    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition) throws Exception {
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 真正执行创建实例的方法
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return bean
     */
    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws Exception {
        if (beanDefinition == null) {
            throw new Exception("beanDefinition cannot be null.");
        }
        Class<?> beanClass = beanDefinition.getBeanClass();
        // 实例化
        Object bean = beanClass.newInstance();
        // 依赖注入
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType() == String.class && field.isAnnotationPresent(Value.class)) {
                field.setAccessible(true);
                field.set(bean, field.getAnnotation(Value.class).value());
                continue;
            }
            if (!field.isAnnotationPresent(Autowire.class)) {
                continue;
            }
            Autowire autowireAnno = field.getAnnotation(Autowire.class);
            String injectBeanName = autowireAnno.value();
            if (StringUtils.isBlank(injectBeanName)) {
                injectBeanName = field.getName();
            }
            field.setAccessible(true);
            field.set(bean, getBean(injectBeanName));
        }

        // BeanPostProcessor前置处理
        for (BeanPostProcessor beanPostProcessor : BEAN_POST_PROCESSOR_LIST) {
            bean = beanPostProcessor.postProcessBeforeInitialization(beanName, bean);
        }
        // bean initialization
        if (bean instanceof InitializationBean) {
            ((InitializationBean) bean).afterPropertiesSet();
        }
        // BeanPostProcessor后置处理
        for (BeanPostProcessor beanPostProcessor : BEAN_POST_PROCESSOR_LIST) {
            bean = beanPostProcessor.postProcessAfterInitialization(beanName, bean);
        }
        // 如果是多例对象则不加入到单例池
        if (!StringUtils.equals(beanDefinition.getScope(), ClassParser.PROTOTYPE)) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        BEAN_DEFINITION_MAP.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = BEAN_DEFINITION_MAP.get(beanName);
        if (beanDefinition == null) {
            throw new RuntimeException("don't have bean definition named " + beanName);
        }
        return beanDefinition;
    }
}
