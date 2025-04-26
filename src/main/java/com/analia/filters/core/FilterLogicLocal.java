package com.analia.filters.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;

import java.util.List;

public interface FilterLogicLocal {
    /**
     * @param filterId
     * @return
     * @throws AnaliaException
     */
    Filter getFilterById(int filterId) throws AnaliaException;

    /**
     * @param list
     * @param filters
     * @return
     * @throws AnaliaException
     */
    <T> List<T> doFilter(List<T> list, List<Filter> filters) throws AnaliaException;
}
