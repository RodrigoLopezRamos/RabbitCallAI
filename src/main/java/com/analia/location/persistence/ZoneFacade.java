package com.analia.location.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Zone;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.location.persistence.ZoneFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class ZoneFacade extends JPAPersistenceFacade<Zone> implements ZoneFacadeLocal {

    public static final String QUERY_GET_ZONE_BY_NAME = "getZoneByName";
    public static final String PARAM_ZONE_NAME = "name";

    @Inject
    private EntityManager entityManager;

    public ZoneFacade() {
        super(Zone.class);
    }





    public Zone getZoneByName(String name) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_ZONE_BY_NAME, new JpqlParameter(PARAM_ZONE_NAME, name));
    }

}
