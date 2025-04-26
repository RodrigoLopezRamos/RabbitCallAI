package com.analia.location.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.City;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.util.List;

@ApplicationScoped
public class CityFacade extends JPAPersistenceFacade<City> implements CityFacadeLocal {
    public static final String PARAM_SEARCH = "search";
    private static final String QUERY_GET_CITY_BY_SEARCH_PARAM = "getCitiesBySearchParam";
    private static final String QUERY_GET_CITY_BY_NAME = "getCityByName";
    private static final String PARAM_CITY_NAME = "name";

    @Inject
    private EntityManager entityManager;

    public CityFacade() {
        super(City.class);
    }





    public City getCityByName(String name) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_CITY_BY_NAME, new JpqlParameter(PARAM_CITY_NAME, name));
    }

    @SuppressWarnings("unchecked")

    public List<City> getCities(String querySearchParam) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_CITY_BY_SEARCH_PARAM);
        query.setParameter(PARAM_SEARCH, "%" + querySearchParam + "%");
        return query.getResultList();
    }

}
