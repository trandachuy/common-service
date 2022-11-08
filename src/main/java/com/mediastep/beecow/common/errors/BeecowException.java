/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.errors;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
 * </pre>
 *
 * Can be translated with:
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{params[0]}} to {{params[1]}}"
 * </pre>
 */
public class BeecowException extends RuntimeException {

    private static final String PARAM = "param";

    private static final long serialVersionUID = 1L;

    protected final String message;

    protected final Map<String, Object> paramMap = new HashMap<>();

    public BeecowException(String message) {
        this(message, (Exception) null);
    }

    public BeecowException(String message, Exception cause) {
        super(message, cause);
        this.message = message;
    }

    public BeecowException(String message, Object... params) {
        this(message, null, params);
    }

    public BeecowException(String message, Exception cause, Object... params) {
        super(message, cause);
        this.message = message;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramMap.put(PARAM + i, params[i]);
            }
        }
    }

    public BeecowException(String message, Map<String, Object> paramMap) {
        this(message, null, paramMap);
    }

    public BeecowException(String message, Exception cause, Map<String, Object> paramMap) {
        super(message, cause);
        this.message = message;
        if (paramMap != null) {
        	this.paramMap.putAll(paramMap);
        }
    }

    /**
	 * @return the paramMap
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public ParameterizedErrorVM getErrorVM() {
        return new ParameterizedErrorVM(this.getClass(), message, paramMap);
    }
}
