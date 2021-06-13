package org.horse.simple.common.util;

import org.apache.logging.log4j.Logger;

/**
 * 日志工具类
 * @author horse
 * @date 2021/6/5
 */
public class LogUtils {
    /**
     * info 日志打印
     * @param logger Logger
     * @param format String
     * @param args Object[]
     */
    public static void info(Logger logger, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(format, args);
        }
    }

    /**
     * info 日志打印
     * @param logger Logger
     * @param message String
     */
    public static void info(Logger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    /**
     * error 日志打印
     * @param logger Logger
     * @param throwable Throwable
     * @param format String
     * @param args Object[]
     */
    public static void error(Logger logger, Throwable throwable, String format, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(format, args, throwable);
        }
    }

    /**
     * error 日志打印
     * @param logger Logger
     * @param throwable Throwable
     * @param message String
     */
    public static void error(Logger logger, Throwable throwable, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message, throwable);
        }
    }
}
