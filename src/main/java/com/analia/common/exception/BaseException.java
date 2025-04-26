package com.analia.common.exception;

//import javax.ejb.ApplicationException;

//@ApplicationException(rollback = true)
public class BaseException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BaseException(ExceptionCode reasonCode, String message, Exception e) {
        super(message, e);
    }

    public BaseException(ExceptionCode reasonCode, String message) {
        super(message);
    }

    /**
     * @param componentException
     * @param reasonCode
     * @param message
     * @throws T
     */
    public static <T extends BaseException> void throwException(Class<T> componentException, ExceptionCode reasonCode, String message) throws T {
        T e = null;
        try {
            e = componentException.getConstructor(ExceptionCode.class, String.class).newInstance(reasonCode, message);

        } catch (Exception ex) {
            throw new IllegalStateException("MISSING CONSTRUCTOR FOR CLASS " + componentException + "ErrorCode and String (exceptionText)", ex);
        }
        throw e;
    }
}
