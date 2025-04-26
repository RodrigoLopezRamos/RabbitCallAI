package com.analia.common.enumeration;

public enum PurchaseDetailStatus {
    INVALID("invalid chatbot"),
    VALID("valid chatbot"),
    NOT_AVAILABLE_AT_LOCATION("not available at current location"),
    USED("chatbot already used");

    private String statusDescription;

    PurchaseDetailStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}