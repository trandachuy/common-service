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
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BeecowException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message, String... params) {
        super(message, params);
    }

    public UnauthorizedException(String message, Exception cause, String... params) {
        super(message, cause, params);
    }
}
