package com.analia.cache.handler;

import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;

import java.util.List;

public interface CacheHandler<T> {
    /**
     * @param cacheKey
     * @return
     * @throws AnaliaException
     */
    List<T> execute(CacheKey cacheKey) throws AnaliaException;

}
