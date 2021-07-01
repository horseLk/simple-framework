package org.horse.simple.aop;

import org.horse.simple.spring.aop.advice.BeforeAdvice;
import org.horse.simple.spring.aop.invocation.AopMethodInvocation;

public class DemoBeforeAdvice implements BeforeAdvice {
    @Override
    public void before(AopMethodInvocation methodInvocation) {
        System.out.println("this is aop Demo");
    }
}
