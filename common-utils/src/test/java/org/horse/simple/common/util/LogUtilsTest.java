package org.horse.simple.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogUtilsTest {
    private static final Logger LOG = LogManager.getLogger(LogUtilsTest.class);

    @Test
    public void testInfoLog1() {
        TraceUtils.setTraceId(null);
        LogUtils.info(LOG, "test info log. {}", "test");
        TraceUtils.clearTraceId();
    }

}
