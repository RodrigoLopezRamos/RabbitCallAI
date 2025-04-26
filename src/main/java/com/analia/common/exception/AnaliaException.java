package com.analia.common.exception;

//import javax.ejb.ApplicationException;

//@ApplicationException(rollback = true)
public class AnaliaException extends BaseException {

    private static final long serialVersionUID = 1L;
    private ExceptionCode reasonCode;
    private String message;

    public AnaliaException(ExceptionCode reasonCode, Exception e) {
        super(reasonCode, reasonCode.getMessage(), e);
        this.setReasonCode(reasonCode);
    }

    public AnaliaException(ExceptionCode reasonCode, String message, Exception e) {
        super(reasonCode, message, e);
        this.setReasonCode(reasonCode);
    }

    public AnaliaException(ExceptionCode reasonCode, String message) {
        super(reasonCode, message);
        this.setReasonCode(reasonCode);
    }

    public AnaliaException(ExceptionCode reasonCode) {
        super(reasonCode, reasonCode.getMessage());
        this.setReasonCode(reasonCode);
    }

    public AnaliaException(String message, ExceptionCode reasonCode) {
        super(reasonCode, reasonCode.getMessage());
        this.setReasonCode(reasonCode);
        this.message = message;
    }

    /**
     * @return the reasonCode
     */
    public ExceptionCode getExceptionCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode the reasonCode to set
     */
    public void setReasonCode(ExceptionCode reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getMessage() {
        if (message == null) {
            message = getExceptionCode().getMessage();
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
