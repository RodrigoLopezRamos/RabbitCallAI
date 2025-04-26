package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserDirectory;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;

/**
 * Session Bean implementation class UserGalleryFacade
 */
@ApplicationScoped
public class UserDirectoryFacade extends JPAPersistenceFacade<UserDirectory>  {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DIRECTORY_ID = "directoryId";
    private static final String PARAM_USER_ID = "userId";
    private static final String QUERY_GET_USER_DIRECTORY = "getUserDirectory";
    private static final String QUERY_GET_USER_DIRECTORY_BY_DIRECTORY = "getUserDirectoryByDirectoryId";

    @Inject
    private EntityManager entityManager;

    public UserDirectoryFacade() {
        super(UserDirectory.class);
    }


    public UserDirectory getUserDirectory(BigInteger userId, String name) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_NAME, name);
        JpqlParameter jplParameterName = new JpqlParameter(PARAM_USER_ID, userId);
        return getPersistForNamedQuery(QUERY_GET_USER_DIRECTORY, jpqlParameterUserId, jplParameterName);
    }


    public UserDirectory getUserDirectory(BigInteger userId, BigInteger directoryId) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_DIRECTORY_ID, directoryId);
        JpqlParameter jplParameterName = new JpqlParameter(PARAM_USER_ID, userId);
        return getPersistForNamedQuery(QUERY_GET_USER_DIRECTORY_BY_DIRECTORY, jpqlParameterUserId, jplParameterName);
    }




}
