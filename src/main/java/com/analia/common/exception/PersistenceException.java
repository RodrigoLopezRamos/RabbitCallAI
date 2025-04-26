package com.analia.common.exception;

public class PersistenceException extends BaseException {
    private static final long serialVersionUID = 1L;

    public PersistenceException(ExceptionCode reasonCode, String message, Exception e) {
        super(reasonCode, message, e);
    }

}
