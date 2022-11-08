/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 21/8/2020
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation Delay.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Delay {
    /**
     * Seconds long.
     *
     * @return the long
     */
    long seconds() default 0L;


}
