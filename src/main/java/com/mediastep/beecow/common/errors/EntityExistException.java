/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid input exception.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityExistException extends BeecowException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final Object enity;

    /**
     * Constructor.
     * @param entityName
     * @param message
     * @param params
     */
    public EntityExistException(Class<?> clazz, String message, Object enity) {
        super(message, enity.toString());
        entityName = clazz.getSimpleName();
        this.enity = enity;
    }

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @return the enity
     */
    public Object getEnity() {
        return enity;
    }
}
