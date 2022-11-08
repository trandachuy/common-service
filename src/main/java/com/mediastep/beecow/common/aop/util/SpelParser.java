/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 18/5/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.aop.util;

import org.aspectj.lang.JoinPoint;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * The type Spel parser.
 */
@Component
public class SpelParser {

    private final ExpressionParser expressionParser;

    /**
     * Instantiates a new Spel parser.
     */
    public SpelParser() {
        this.expressionParser = new SpelExpressionParser();
    }

    /**
     * Parse expression string.
     *
     * @param expression the expression
     * @param call       the call
     * @return the string
     */
    public String parseExpression(String expression, JoinPoint call) {
        StandardEvaluationContext context = new MethodBasedEvaluationContext(
                call.getTarget(), ReflectionUtil.getMethodOfJoinPoint(call), call.getArgs(), new DefaultParameterNameDiscoverer());
        try {
            Object target = this.expressionParser.parseExpression(expression).getValue(context);
            if (target != null) {
                return target.toString();
            }
            else {
                return expression;
            }
        }
        catch (SpelEvaluationException e) {
            return expression;
        }
    }
}
