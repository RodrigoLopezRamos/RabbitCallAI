package com.analia.location.persistence;

import com.analia.common.model.Location;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.location.persistence.LocationFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;


@NamedQuery(name = "getCityByName",
        query = "select c from City c where (c.name =:name)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getZoneByName",
        query = "select z from Zone z where (z.name =:name)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getCitiesBySearchParam",
        query = "select c from City c where (c.name  like :search)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@ApplicationScoped
public class LocationFacade extends JPAPersistenceFacade<Location> implements LocationFacadeLocal {

    public static final String LOCATION_ID = "locationId";

    @Inject
    private EntityManager entityManager;

    public LocationFacade() {
        super(Location.class);
    }




}
