package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserLocation;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class UserLocationFacade extends JPAPersistenceFacade<UserLocation>  {
    private static final String QUERY_GET_USER_LOCATION = "getUserLocation";
    private static final String QUERY_GET_DEFAULT_USER_LOCATION = "getDefaultUserLocation";
    private static final String QUERY_GET_USER_LOCATIONS = "getUserLocations";
    private static final String QUERY_RESET_USER_LOCATIONS = "resetUserLocations";
    private static final String PARAM_ZONE_ID = "zoneId";
    private static final String PARAM_USER_ID = "userId";

    @Inject
    private EntityManager entityManager;

    public UserLocationFacade() {
        super(UserLocation.class);
    }





    public UserLocation getUserLocation(BigInteger zoneId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_USER_LOCATION, new JpqlParameter(PARAM_ZONE_ID, zoneId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public UserLocation getDefaultUserLocation(BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_DEFAULT_USER_LOCATION, new JpqlParameter(PARAM_USER_ID, userId));
    }


    public List<UserLocation> getUserLocations(BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_LOCATIONS, new JpqlParameter(PARAM_USER_ID, userId));
    }


    public void resetUserLocations(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_RESET_USER_LOCATIONS);
        query.setParameter(PARAM_USER_ID, userId);
        query.executeUpdate();
    }
}
