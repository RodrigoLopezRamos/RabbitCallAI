package com.analia.authorization.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Role;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class RoleFacade extends JPAPersistenceFacade<Role> {
    private static final String QUERY_GET_ALL_ACTIVE_ROLES = "getListOfActiveRoles";
    private static final String PARAM_ROLE_ID_MIN = "minRoleId";
    private static final String PARAM_ROLE_ID_MAX = "maxRoleId";

    @Inject
    private EntityManager entityManager;

    public RoleFacade() {
        super(Role.class);
    }




    public List<Role> getListOfActiveRoles(BigInteger minRoleId, BigInteger maxRoleId) throws AnaliaException {
        JpqlParameter jpqlMinRoleIdParameter = new JpqlParameter(PARAM_ROLE_ID_MIN, minRoleId);
        JpqlParameter jpqlMaxRoleIdParameter = new JpqlParameter(PARAM_ROLE_ID_MAX, maxRoleId);
        return getListForNamedQuery(QUERY_GET_ALL_ACTIVE_ROLES, jpqlMinRoleIdParameter, jpqlMaxRoleIdParameter);
    }
}
