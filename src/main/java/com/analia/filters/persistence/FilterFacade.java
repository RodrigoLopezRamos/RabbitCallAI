package com.analia.filters.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;




@ApplicationScoped
public class FilterFacade extends JPAPersistenceFacade<Filter> implements FilterFacadeLocal {
    public static final String PARAM_NAME = "name";
    private static final String QUERY_GET_FILTER_BY_NAME = "getFilterByName";

    @Inject
    private EntityManager entityManager;

    public FilterFacade() {
        super(Filter.class);
    }



    public Filter getFilterByName(String name) throws AnaliaException {
        JpqlParameter jpqlParameterFilterName = new JpqlParameter(PARAM_NAME, name);
        return getPersistForNamedQuery(QUERY_GET_FILTER_BY_NAME, jpqlParameterFilterName);
    }
}
