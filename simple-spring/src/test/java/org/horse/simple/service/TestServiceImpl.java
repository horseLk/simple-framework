package org.horse.simple.service;

import org.horse.simple.spring.annotation.Before;
import org.horse.simple.spring.annotation.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    @Before("org.horse.simple.aop.DemoBeforeAdvice")
    public void callTestService() {
        System.out.println("jdk proxy");
    }
}
