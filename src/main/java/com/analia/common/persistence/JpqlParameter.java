package com.analia.common.persistence;

/**
 * @author Rodrigo Lopez
 */
public class JpqlParameter {
    private final String parameterName;
    private final Object parameterContent;

    public JpqlParameter(String parameterName, Object parameterContent) {
        this.parameterContent = parameterContent;
        this.parameterName = parameterName;
    }

    public Object getParameterContent() {
        return parameterContent;
    }

    public String getParameterName() {
        return parameterName;
    }

}
