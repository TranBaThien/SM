/*
 * @(#) LogAppender.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/03
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This appender will capture all logging statements written by a test case,
 * however the executing test case must initialize and configure this appender
 * for it to work.
 */
public class LogAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> events = new ArrayList<>();

    public LogAppender() {
        start();
    }

    /**
     * Factory method for reducing the noise of initializing and configuring a
     * appender in a test case.
     *
     * @return
     */
    public static LogAppender initialize(String... loggers) {
        LogAppender localAppender = new LogAppender();
        for (String loggerName : loggers) {
            localAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
            Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
            logger.addAppender(localAppender);
        }
        return localAppender;
    }

    /**
     * Logback can sometimes take a moment to initialize, this method will return
     * false until Logbackis ready.
     */
    public static void pauseTillLogbackReady() {
        do {

        } while (!isLogbackReady());
    }

    private static boolean isLogbackReady() {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        } catch (ClassCastException castException) {
            return false;
        }
        return true;
    }

    public static void cleanup(LogAppender localAppender) {
        localAppender.stop();
        localAppender.clearEvents();
    }

    @Override
    public void append(ILoggingEvent e) {
        events.add(e);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }

    public void clearEvents() {
        events.clear();
    }
}
