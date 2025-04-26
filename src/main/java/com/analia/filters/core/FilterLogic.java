package com.analia.filters.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.BeanFactory;
import com.analia.common.model.Filter;
import com.analia.filters.handler.FilterHandler;
import com.analia.filters.persistence.FilterFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.util.List;

@ApplicationScoped
public class FilterLogic implements FilterLogicLocal {

    @Inject
    private FilterFacadeLocal filterFacadeLocal;


    public Filter getFilterById(int filterId) throws AnaliaException {
        return filterFacadeLocal.find(filterId);
    }

    /**
     *
     */

    public <T> List<T> doFilter(List<T> list, List<Filter> filters) throws AnaliaException {
        if (filters == null || filters.isEmpty()) {
            throw new AnaliaException(ExceptionCode.BAD_REQUEST, "There are not filters enabled!");
        }
        for (Filter filter : filters) {
            BeanFactory<FilterHandler<T>> JNDIFactory = new BeanFactory<>();
            FilterHandler<T> filterHandler = JNDIFactory.getBeanComponent(filter.getFilterHandler());
            list = filterHandler.doFilter(list, filter);
        }
        return list;
    }
}
