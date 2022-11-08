package com.mediastep.beecow.common.util;

import javax.validation.ConstraintValidatorContext;

/**
 * The type Validator util.
 */
public final class ValidatorUtil {

    /**
     * Add constraint violation boolean.
     *
     * @param context      the context
     * @param propertyNode the property node
     * @param msg          the msg
     * @return the boolean
     */
    public static boolean addConstraintViolation(ConstraintValidatorContext context, String propertyNode, String msg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg).addPropertyNode(propertyNode).addConstraintViolation();
        return false;
    }
}
