package com.analia.common.infrastructure;

import com.analia.common.exception.AnaliaException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class BeanFactory<T> {

    @SuppressWarnings("unchecked")
    public T getBeanComponent(String jndiName) throws AnaliaException {
        T t = null;
        try {
            Context c = new InitialContext();
            t = ((T) c.lookup(jndiName));
        } catch (NamingException e) {
            throw new AnaliaException(com.analia.common.exception.ExceptionCode.SERVER_CRITICAL_ERROR, e.getMessage(), e);
        }
        return t;
    }
}
