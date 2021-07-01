package org.horse.simple.controller;

import org.horse.simple.service.DemoService;
import org.horse.simple.service.TestService;
import org.horse.simple.spring.annotation.Autowire;
import org.horse.simple.spring.annotation.Controller;
import org.horse.simple.spring.annotation.Value;
import org.horse.simple.spring.factory.aware.BaseApplicationContextAware;
import org.horse.simple.spring.factory.aware.BeanNameAware;
import org.horse.simple.spring.factory.impl.BaseApplicationContext;
import org.horse.simple.spring.factory.support.InitializationBean;

@Controller
public class DemoController implements InitializationBean, BeanNameAware {
    @Autowire
    private DemoService demoService;

    @Autowire
    private TestService testServiceImpl;

    @Value("horse")
    private String name;

    private int age;

    private String beanName;

    public void callTestService() {
        testServiceImpl.callTestService();
    }

    public String callService(String str) {
        return demoService.find(str);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void afterPropertiesSet() {
        this.age = 24;
    }

    @Override
    public void setAwareField(String obj) {
        this.beanName = obj;
    }
}
