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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends BeecowException {

    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message, Object... params) {
        super(message, params);
    }

    public InvalidInputException(String message, Exception cause, Object... params) {
        super(message, cause, params);
    }
}
