/*
 * Created on : 9/1/2019
 * Author: Dai Mai <email: dai.maithanh@gmail.com>
 */

package com.mediastep.beecow.common.aop.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The type Reflection util.
 */
public class ReflectionUtil {

    /**
     * Gets annotation of join point.
     *
     * @param joinPoint          the join point
     * @param selectedAnnotation the selected annotation
     * @return the annotation of join point
     */
    public static Object getAnnotationOfJoinPoint(JoinPoint joinPoint, Class<? extends Annotation> selectedAnnotation) {
        Method method = getMethodOfJoinPoint(joinPoint);
        return method.getAnnotation(selectedAnnotation);
    }

    /**
     * Gets method of join point.
     *
     * @param joinPoint the join point
     * @return the method of join point
     */
    public static Method getMethodOfJoinPoint(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
