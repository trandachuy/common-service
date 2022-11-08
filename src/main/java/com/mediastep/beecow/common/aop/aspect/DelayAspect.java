/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 21/8/2020
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.aop.aspect;

import com.mediastep.beecow.common.aop.annotation.Delay;
import com.mediastep.beecow.common.aop.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * The type Delay aspect.
 */
@Aspect
@Slf4j
@Configuration
public class DelayAspect {

    /**
     * Annotated method.
     */
    @Pointcut("@annotation(com.mediastep.beecow.common.aop.annotation.Delay)")
    public void annotatedMethod() {}

    /**
     * Delay.
     *
     * @param call the call
     */
    @Before(value = "annotatedMethod()")
    public void delay(JoinPoint call) {
        Delay delay = (Delay) ReflectionUtil.getAnnotationOfJoinPoint(call, Delay.class);
        log.debug("Thread delay process in {} seconds", delay.seconds());
        try {
            Thread.sleep(delay.seconds() * 1000);
        } catch (InterruptedException e) {
            log.error("Delay interrupt", e);
        }
    }
}
