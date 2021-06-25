package org.horse.simple;

import org.horse.simple.controller.DemoController;
import org.horse.simple.spring.annotation.ComponentScan;
import org.horse.simple.spring.factory.impl.BaseApplicationContext;
import org.horse.simple.spring.factory.impl.DefaultApplicationContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@ComponentScan("org.horse.simple")
public class ApplicationContextTest {
    private BaseApplicationContext context;

    @Before
    public void init() {
        context = new DefaultApplicationContext(ApplicationContextTest.class);
    }

    @Test
    public void testIoc() throws Exception {
        DemoController demoController = (DemoController) context.getBean("demoController");
        Assert.assertEquals("hello", demoController.callService("hello"));
        Assert.assertEquals("horse", demoController.getName());
        Assert.assertEquals(24, demoController.getAge());
        Assert.assertEquals("demoController", demoController.getBeanName());
    }
}
