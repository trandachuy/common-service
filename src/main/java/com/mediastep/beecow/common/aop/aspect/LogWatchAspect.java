/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 21/8/2020
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.aop.aspect;

import com.mediastep.beecow.common.aop.annotation.LogWatch;
import com.mediastep.beecow.common.aop.util.ReflectionUtil;
import com.mediastep.beecow.common.aop.util.SpelParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTimeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Configuration
@Component
public class LogWatchAspect {
    private static final String LOG_FORMATTER = "WATCH '{}' from {} - finished after: {}";
    private final SpelParser spelParser;

    public LogWatchAspect(SpelParser spelParser) {
        this.spelParser = spelParser;
    }

    @Pointcut("@annotation(com.mediastep.beecow.common.aop.annotation.LogWatch)")
    public void annotatedMethod() {}

    @Around("annotatedMethod()")
    public Object watching(ProceedingJoinPoint call) throws Throwable {
        LogWatch watch = (LogWatch) ReflectionUtil.getAnnotationOfJoinPoint(call, LogWatch.class);
        String watchValue = watch.value();
        if (StringUtils.isBlank(watchValue)) {
            Method watchMethod = ReflectionUtil.getMethodOfJoinPoint(call);
            watchValue = watchMethod.getDeclaringClass().getName() + "#" + watchMethod.getName();
        }
        else {
            watchValue = this.spelParser.parseExpression(watchValue, call);
        }

        StopWatch stopWatch = StopWatch.createStarted();
        Object result = call.proceed();
        stopWatch.stop();
        String startTime = DateFormatUtils.format(stopWatch.getStartTime(), "yyyy-MM-dd HH:mm:ss.SSS");
        switch (watch.level()) {
            case TRACE:
                log.trace(LOG_FORMATTER,
                    watchValue,
                    startTime,
                    stopWatch.toString());
                break;
            case ERROR:
                log.error(LOG_FORMATTER,
                    watchValue,
                    startTime,
                    stopWatch.toString());
                break;
            case WARN:
                log.warn(LOG_FORMATTER,
                    watchValue,
                    startTime,
                    stopWatch.toString());
                break;
            case DEBUG:
                log.debug(LOG_FORMATTER,
                    watchValue,
                    startTime,
                    stopWatch.toString());
                break;
            default:
                log.info(LOG_FORMATTER,
                    watchValue,
                    startTime,
                    stopWatch.toString());
                break;
        }
        return result;
    }
}
