package com.analia.common.cache;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.util.HashMap;
import java.util.Map;

public class CacheKey {
    /**
     *
     */
    private final Map<String, Object> attributes = new HashMap<>();

    public Object get(Object key) {
        return attributes.get(key);
    }

    public boolean containsKey(Object key) {
        return attributes.containsKey(key);
    }

    public Object put(String key, Object value) throws AnaliaException {
        if (key == null) {
            throw new AnaliaException(ExceptionCode.SERVER_ERROR, "Key must not null");
        }
        return attributes.put(key, value);
    }


    public String toString() {
        return attributes.toString();
    }
}
