package com.analia.filters.service.impl;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;
import com.analia.filters.core.FilterLogicLocal;
import com.analia.filters.service.FilterServiceLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class FilterService implements FilterServiceLocal {
    @Inject
    private FilterLogicLocal filterLogicLocal;


    public Filter getFilterById(int filterId) throws AnaliaException {
        return filterLogicLocal.getFilterById(filterId);
    }
}
