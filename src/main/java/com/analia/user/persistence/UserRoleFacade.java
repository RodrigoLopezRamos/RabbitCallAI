package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserRole;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;

@ApplicationScoped
public class UserRoleFacade extends JPAPersistenceFacade<UserRole>  {


    private static final String QUERY_GET_USER_ROLE = "getUserRole";
    private static final String PARAM_VENDOR_ID = "vendorId";
    private static final String PARAM_USER_ID = "userId";


    @Inject
    private EntityManager entityManager;

    public UserRoleFacade() {
        super(UserRole.class);
    }





    public UserRole getUserRole(BigInteger vendorId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_USER_ROLE, new JpqlParameter(PARAM_VENDOR_ID,vendorId), new JpqlParameter(PARAM_USER_ID,userId));
    }
}
