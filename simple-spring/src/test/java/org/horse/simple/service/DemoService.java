package org.horse.simple.service;

import org.horse.simple.spring.annotation.Service;

@Service
public class DemoService {

    public String find(String str) {
        return str;
    }
}
