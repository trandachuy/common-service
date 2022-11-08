/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid input exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends BeecowException {

    private static final String CLASS_NAME_KEY = "className";

    private static final String ID_KEY = "id";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param entityName
     * @param message
     * @param params
     */
    public EntityNotFoundException(Class<?> clazz, String message, Object... params) {
        super(message, params);
        setClass(clazz);
    }

    public EntityNotFoundException(Class<?> clazz, String message, Map<String, Object> paramMap) {
    	super(message, null, paramMap);
        setClass(clazz);
    }

    public EntityNotFoundException(Class<?> clazz, String message, Long id) {
    	super(message);
        setClass(clazz);
        setId(id);
    }

    private void setClass(Class<?> clazz) {
    	String clazzName;
        if (clazz != null) {
        	clazzName = clazz.getSimpleName();
        }
        else {
        	clazzName = null;
        }
    	paramMap.put(CLASS_NAME_KEY, clazzName);
    }

    private void setId(Long id) {
    	paramMap.put(ID_KEY, id);
    }
}
