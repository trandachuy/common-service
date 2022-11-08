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
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConfigurationMissingException extends BeecowException {

    private static final long serialVersionUID = 1L;

    public ConfigurationMissingException(String message, String... params) {
        super(message, params);
    }

    public ConfigurationMissingException(String message, Exception cause, String... params) {
        super(message, cause, params);
    }

    public ConfigurationMissingException(String message, Exception cause, Map<String, Object> paramMap) {
    	super(message, cause, paramMap);
    }
}
