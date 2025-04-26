package com.analia.common.context;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Rodrigo Lopez Ramos
 */
public abstract class AnaliaContext {
    /**
     * Check if the Context in the request is still valid!
     */
    protected abstract void validate();

    /**
     * Cache for properties on the time request is valid !
     *
     * @return
     */
    protected abstract HashMap<String, Object> getProperties();

    /**
     * @param key
     * @return
     */
    public boolean isValidOnContext(String key) {
        this.validate();
        return getProperties().containsKey(key);
    }

    /**
     * @param key
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getValueForKey(String key, Class<T> t) {
        Object object = getProperties().get(key);
        if (object == null) {
            return null;
        }
        return (T) getProperties().get(key);
    }

    /**
     * @param key
     * @return
     * @throws AnaliaException
     */
    public int getMandatoryIntForKey(String key) throws AnaliaException {
        int result = 0;
        if (!getProperties().containsKey(key)) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY,
                    "Parameter not found for key :" + key);
        }
        result = Integer.parseInt(getProperties().get(key).toString());
        return result;
    }

    /**
     * @param key
     * @return
     * @throws AnaliaException
     */
    public Double getMandatoryDoubleForKey(String key) throws AnaliaException {
        Double result = null;
        if (!getProperties().containsKey(key)) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY,
                    "Parameter not found for key :" + key);
        }
        result = Double.parseDouble(getProperties().get(key).toString());
        return result;
    }

    /**
     * @param key
     * @param value
     */
    public <T extends Serializable> void setAttribute(String key, T t) {
        this.validate();
        this.getProperties().put(key, t);
    }

    /**
     * @param key
     * @param t
     * @return
     * @throws AnaliaException
     */
    @SuppressWarnings("unchecked")
    public <T> T getMandatoryValueForKey(String key, Class<T> t) throws AnaliaException {
        try {
            Object object = getProperties().get(key);
            if (object == null) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Not Data found for  :" + key);
            }
            return (T) getProperties().get(key);
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.SERVER_CRITICAL_ERROR, "Server error :" + e.getMessage());
        }
    }

}
