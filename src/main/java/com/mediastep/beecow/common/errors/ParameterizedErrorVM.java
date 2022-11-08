package com.mediastep.beecow.common.errors;

import java.io.Serializable;
import java.util.Map;

/**
 * View Model for sending a parameterized error message.
 */
public class ParameterizedErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    private final String message;

    private final Map<String, Object> paramMap;

    public ParameterizedErrorVM(Class<? extends Exception> excClass, String message, Map<String, Object> paramMap) {
        this.name = excClass.getSimpleName();
        this.message = message;
        this.paramMap = paramMap;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getParams() {
        return paramMap;
    }

    @Override
    public String toString() {
        return name + " {message=" + message + ", paramMap=" + paramMap + "}";
    }
}
