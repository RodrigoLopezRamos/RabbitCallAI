package com.analia.common.exception;

public enum ExceptionCode {
    // Request
    UNKNOWN_USER_AGENT(ErrorCode.BAD_REQUEST, 99, "Missing Parameter"),

    PARAMETER_IS_MANDATORY(ErrorCode.BAD_REQUEST, 100, "Parameter is mandatory"),

    BAD_REQUEST(ErrorCode.BAD_REQUEST, 110, "Bad Request"),

    INVALID_INPUT_VALUES(ErrorCode.BAD_REQUEST, 101, "Invalid input values"),

    INVALID_EMAIL(ErrorCode.BAD_REQUEST, 200, "Email format is invalid"),

    DUPLICATE_EMAIL(ErrorCode.BAD_REQUEST, 201, "User already exist"),

    INVALID_PASSWORD(ErrorCode.BAD_REQUEST, 202, "Password is invalid"),

    INVALID_FACEBOOK_EMAIL(ErrorCode.BAD_REQUEST, 203, "Facebook email"),

    INVALID_DATE_FORMAT(ErrorCode.BAD_REQUEST, 204, "Invalid date format"),

    GPS_IS_NOT_ENABLED(ErrorCode.BAD_REQUEST, 205, "GPS is not enabled"),

    USER_IS_OUT_OF_RANGE(ErrorCode.BAD_REQUEST, 206, "User's location is out of range"),

    ALREADY_CHECKIN(ErrorCode.BAD_REQUEST, 207, "Already check in at this location , please try again later, Have good day ."),

    PAYMENT_NOT_AUTHORIZED(ErrorCode.BAD_REQUEST, 208, "Payment not authorized from user"),

    PAYMENT_WAS_PROCCESED(ErrorCode.BAD_REQUEST, 209, "Payment was proccessed already!"),

    /*
     * 400 SERIES
     */
    AUTHENTICATION_FAILED(ErrorCode.UNAUTORIZATION, 410, "Authentication failed "),

    INVALID_NONCE(ErrorCode.INVALID_NONCE, 499, "Nonce is invalid"),

    ENTITY_NOT_FOUND(ErrorCode.NOT_FOUND, 412, "Entity not found"),

    AUTHORIZATION_FAILED(ErrorCode.UNAUTORIZATION, 413, "Analia says : Authorization failed.!"),

    EXCEEDED_SET_PROFILE_KEY_LIMIT(ErrorCode.BAD_REQUEST, 414, "You cannot save more than 20 keys"),

    VENDOR_AUTHENTICATION_FAILED(ErrorCode.BAD_REQUEST, 415, "Not vendor code specified"),

    UNKNOWN_VENDOR_LOCATION(ErrorCode.BAD_REQUEST, 409, "Unkown vendor location"),

    VENDOR_ACCCOUNT_NOT_FOUND(ErrorCode.BAD_REQUEST, 408, "Not account found or users not into an account"),


    PURCHASE_DETAIL_STATE_INVALID(ErrorCode.BAD_REQUEST, 416, "purchase is not valid"),

    PURCHASE_DETAIL_STATE_USED(ErrorCode.BAD_REQUEST, 417, "purchase has already been redeemed"),

    PURCHASE_DETAIL_NOT_VALID_FOR_LOCATION(ErrorCode.BAD_REQUEST, 418, "purchase not available for location"),

    PURCHASE_DETAIL_REDEEMABLE_BY_NOT_SET(ErrorCode.BAD_REQUEST, 419, "the redeemable-by name for the chatbot was not found in the database"),

    INVALID_MULTIPLE_PARAMETERS_SEPCIFIED(ErrorCode.BAD_REQUEST, 420, "mutiple parameters specified"),

    INVALID_LOCATION_ID_PARAMETER_SPECIFIED(ErrorCode.BAD_REQUEST, 421, "invalid location ids specified"),

    INVALID_OPERATION(ErrorCode.BAD_REQUEST, 422, "Invalid Operation"),

    USER_WRONG_TYPE(ErrorCode.BAD_REQUEST, 423, "Wrong type"),

    EMAIL_NOT_VALID(ErrorCode.BAD_REQUEST, 424, "email is not valid"),

    PROMO_CODE_IS_INVALID(ErrorCode.BAD_REQUEST, 425, "Promo code is invalid"),

    VOUCHER_IS_INVALID(ErrorCode.BAD_REQUEST, 426, "voucher is not valid"),

    VOUCHER_SOLD_OUT(ErrorCode.BAD_REQUEST, 427, "voucher is sold out"),

    VOUCHER_INSUFICIENT_INVENTORY(ErrorCode.BAD_REQUEST, 428, "voucher has insuficient inventory "),

    VOUCHER_MAXIMUN_ALLOWANCE_REACHED(ErrorCode.BAD_REQUEST, 429, "voucher maximum quantity was reached"),

    ENCRYPTION_ERROR(ErrorCode.BAD_REQUEST, 430, "Encryption error"),

    SERVER_ERROR(ErrorCode.BAD_REQUEST, 431, "Server error"),

    INVALID_VOUCHER_ID(ErrorCode.BAD_REQUEST, 432, "Invalid voucher Id value of 0 provided"),

    NO_MERCHANT_LOCATION_IDS_PROVIDED(ErrorCode.BAD_REQUEST, 433, "No merchant location ids provided"),

    INVALID_MAXIMUM_PER_USER_QUANTITY(ErrorCode.BAD_REQUEST, 434, "Invalid quantity for, maximum per user field"),

    INVALID_PASSWORD_VALUE(ErrorCode.BAD_REQUEST, 456, "Sorry, your password must be at least 7 characters long with no spaces at the beginning or end."),

    EXPIRED_OPERATION(ErrorCode.BAD_REQUEST, 457, "The operation requested has already expired or is no longer applicable"),

    CURRENT_PASSWORD_INVALID(ErrorCode.BAD_REQUEST, 458, "Current password value is incorrect"),

    USER_IS_DEACTIVATED(ErrorCode.UNAUTORIZATION, 459, "User is deactivated"),

    MULTIPLE_USE_REFERRAL_CODE_ERROR(ErrorCode.BAD_REQUEST, 459, "The referral code can only be used once"),

    INVALID_CLIENT_VERSION(ErrorCode.BAD_REQUEST, 600, "Upgrade app to continue."),

    SHARE_UNKNOWN_TYPE(ErrorCode.BAD_REQUEST, 1055, "Unknown Sharing type."),

    SHARE_DETAILS_TOO_LONG(ErrorCode.BAD_REQUEST, 1056, "Sharing details longer than 255 characters"),

    SHARE_MISSING_PARAM(ErrorCode.BAD_REQUEST, 1057, "Missing sharing parameter: tradeVendorLocationId, or vendorLocationVoucherId"),

    INVALID_SOURCE(ErrorCode.BAD_REQUEST, 1058, "Invalid Source"),

    INVALID_DEFAULT_SOURCE(ErrorCode.BAD_REQUEST, 1059, "Invalid Default Source"),

    SHARE_UNEXPECTED_PARAM(ErrorCode.BAD_REQUEST, 1060, " Only ONE of tradeVendorLocationId or vendorLocationVoucherId must be provided at a time"),

    STRIPE_ERROR(ErrorCode.BAD_REQUEST, 1061, "Stripe Issue"),

    CREDITS_REQUIRED(ErrorCode.BAD_REQUEST, 2001, "This voucher requires the use of credits for purchases"),

    INVALID_VOUCHER_QUANTITY_PARAM(ErrorCode.BAD_REQUEST, 2011, "The quantity parameter is not valid (less than 1 or greater than currently system maximum allowed)"),

    VOUCHER_CREDITS_REQUIRED(ErrorCode.BAD_REQUEST, 2012, "This voucher requires the use of credits for purchases"),

    INSUFICIENT_CREDITS(ErrorCode.BAD_REQUEST, 2013, "insuficient credits"),

    VOUCHER_IS_CASH_ONLY(ErrorCode.BAD_REQUEST, 2014, "voucher requieres cash only"),

    INSUFICIENT_INVENTORY(ErrorCode.BAD_REQUEST, 2015, "insuficient inventory"),

    SHOPPING_CART_IS_EMPTY(ErrorCode.BAD_REQUEST, 2016, "Shopping Cart is Empty"),

    /*
     * This refers to the fact that the chatbot has a state not known to the server
     */
    INDETERMINED_VOUCHER_STATE(ErrorCode.BAD_REQUEST, 433, "invalid voucher state"),

    INVALID_UA(ErrorCode.INTERNAL_SERVER_ERROR, 505, "Invalid Client Code"),

    /*
     * 500 SERIES
     */
    // Server

    PERSISTENCE_EXCEPTION(ErrorCode.INTERNAL_SERVER_ERROR, 501, "Persistence Exception"),

    SERVER_CRITICAL_ERROR(ErrorCode.INTERNAL_SERVER_ERROR, 503, "Server critical error "),
    ;

    private ErrorCode errorCode;
    private int statusCode;
    private final String message;

    ExceptionCode(ErrorCode errorCode, int statusCode, String messageCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.message = messageCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return Integer.toString(this.statusCode);
    }
}
