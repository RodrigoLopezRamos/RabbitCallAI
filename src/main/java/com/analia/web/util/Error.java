package com.analia.web.util;

public class Error {

    private String message;
    private int reasonCode;

    public Error(int reasonCode, String messageKey) {
        super();
        this.message = messageKey;
        this.reasonCode = reasonCode;
    }

    public static void main(String[] args) {

    }

    public int getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(int reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
