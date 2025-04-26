package com.analia.common.exception;

public enum ErrorCode {

    BAD_REQUEST(400, "The request had bad syntax or was inherently impossible to be satisfied."),
    UNAUTORIZATION(401, "The request had bad syntax or was inherently impossible to be satisfied."),
    INVALID_NONCE(499, "The request had bad syntax or was inherently impossible to be satisfied."),
    FORBIDDEN(403, "The request is for something forbidden. Authorization will not help."),
    NOT_FOUND(404, "The server has not found anything matching the URI given"),
    INTERNAL_SERVER_ERROR(500, "The server encountered an unexpected condition which prevented it from fulfilling the request");

    public String messageCode;
    private int statusCode;

    ErrorCode(int statusCode, String messageCode) {
        this.statusCode = statusCode;
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String toString() {
        return Integer.toString(this.statusCode);
    }
}