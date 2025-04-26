package com.analia.authorization.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Permission;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;

/**
 * Session Bean implementation class Permission
 */


@ApplicationScoped
public class PermissionFacade extends JPAPersistenceFacade<Permission>  {
    public static final String QUERY_NAMED_GET_PERMISSION_BY_ENDPOINT = "getPermissionByEndpoint";
    public static final String PARAM_METHOD = "method";
    public static final String PARAM_RESOURCE = "resource";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_VENDOR_ID = "vendorId";


    private EntityManager entityManager;

    public PermissionFacade() {
        super(Permission.class);
    }




    /**
     *
     */
    public Permission getPermissionByEndpoint(String enpoint, String method) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_NAMED_GET_PERMISSION_BY_ENDPOINT, new JpqlParameter(PARAM_METHOD, method), new JpqlParameter(PARAM_RESOURCE, enpoint));
    }

    public Permission getPermissionByEndpoint(String enpoint, String method, BigInteger userId, BigInteger vendorId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_NAMED_GET_PERMISSION_BY_ENDPOINT,
                new JpqlParameter(PARAM_METHOD, method),
                new JpqlParameter(PARAM_USER_ID, userId),
                new JpqlParameter(PARAM_VENDOR_ID, vendorId),
                new JpqlParameter(PARAM_RESOURCE, enpoint));
    }
}
