package com.analia.filters.handler;

import com.analia.common.exception.AnaliaException;

import java.util.List;

public interface FilterHandler<T> {
    List<T> doFilter(List<T> list, com.analia.common.model.Filter filter) throws AnaliaException;
}
