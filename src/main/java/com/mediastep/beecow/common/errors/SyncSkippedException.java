package com.mediastep.beecow.common.errors;

public class SyncSkippedException extends BeecowException {

    private static final long serialVersionUID = 1L;

    public SyncSkippedException(String message) {
        super(message);
    }

    public SyncSkippedException(String message, Throwable cause) {
        super(message, cause);
    }
}
