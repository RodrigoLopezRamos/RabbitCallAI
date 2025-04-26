package com.analia.common.context;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Rodrigo Lopez Ramos
 * MagnetarLabs Corporation.
 */
public class AnaliaUserContext extends AnaliaContext implements Serializable {
    /**
     *
     */
    public static final String VALID_LOCATION = "@!#!@#!@#!VALID_LOCATION###@?#";
    /**
     *
     */
    public static final String USER_ATTRIBUTE = "^$#@@#USER_ATTRIBUTE^&*%";
    /**
     *
     */
    public static final String USER_LOCATION = "#!$!$!$#@USER_LOCATION#!!#";
    /**
     *
     */
    public static final String USER_DATE_TIME = "#$@#$USER_DATE_TIME#@!#!@";
    /**
     *
     */
    public static final String VENDOR_LOCATION = "#$@#$VENDOR_LOCATION#@!#!@";
    /**
     *
     */
    public static final String VENDOR = "#$@#$VENDOR#@!#!@";


    public static final String VENDOR_ID = "#$@#$VENDOR_ID#@!#!@";

    /**
     *
     */
    public static final String DEVICE = "@#$@#$DEVICE#$#@$@#";

    /**
     *
     */
    public static final String ACCOUNT_USER = "##!@#!@ACCOUNT_USER!#!@#!@";


    /**
     *
     */
    public static final String ORDERS = "##!@#!@ACCOUNT_USER!#!@#!@";

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private static final ThreadLocal<AnaliaUserContext> threadLocal = new ThreadLocal<AnaliaUserContext>();
    /**
     *
     */
    private boolean invalid = false;
    /**
     *
     */
    private final HashMap<String, Object> userProperties = new HashMap<>();

    /**
     * @return
     */
    public static boolean isContextAlive() {
        AnaliaUserContext context = threadLocal.get();
        return (context != null);
    }

    /**
     * @return
     */
    public static AnaliaUserContext createUserContext() {
        if (threadLocal.get() != null) {
            throw new IllegalStateException("UserContext is created already!");
        }
        AnaliaUserContext result = new AnaliaUserContext();
        threadLocal.set(result);
        return result;
    }

    /**
     * @return
     */
    public static AnaliaUserContext getContext() {
        AnaliaUserContext context = threadLocal.get();
        if (context == null) {
            throw new IllegalStateException("UserContext is not created");
        }
        context.validate();
        return context;
    }

    /**
     *
     */

    protected void validate() {
        if (isInvalid()) {
            throw new IllegalStateException("UserContext has been invalidated!");
        }
    }

    /**
     *
     */

    protected HashMap<String, Object> getProperties() {
        return userProperties;
    }

    /**
     *
     */
    public void invalidate() {
        this.validate();
        this.invalid = true;
        threadLocal.remove();
    }

    /**
     * Getters and Setters
     */
    public boolean isInvalid() {
        return invalid;
    }

    /**
     * @param invalid
     */
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}
