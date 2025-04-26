package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Grouping;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;

@ApplicationScoped
public class GroupingFacade extends JPAPersistenceFacade<Grouping> {
    /**
     *
     */
    private static final String QUERY_GET_GROUPING_BY_NAME = "getGroupingByName";

    private static final String QUERY_GET_GROUPING_BY_NAME_WITH_USER_ID = "getGroupingByNameWithUserId";

    private static final String PARAM_GROUPING_NAME = "name";

    private static final String PARAM_GROUPING_USER_ID = "userId";


    @Inject
    private EntityManager entityManager;


    public GroupingFacade() {
        super(Grouping.class);
    }






    public Grouping getGroupingByName(String name) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_GROUPING_BY_NAME, new JpqlParameter(PARAM_GROUPING_NAME, name));
    }


    public Grouping getGroupingByNameWithUserId(String name, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_GROUPING_BY_NAME_WITH_USER_ID, new JpqlParameter(PARAM_GROUPING_NAME, name), new JpqlParameter(PARAM_GROUPING_USER_ID, userId));
    }

}
