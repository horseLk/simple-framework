package org.horse.simple.service;

import org.horse.simple.spring.annotation.Before;
import org.horse.simple.spring.annotation.Service;

@Service
public class DemoService {

    @Before("org.horse.simple.aop.DemoBeforeAdvice")
    public String find(String str) {
        return str;
    }
}
