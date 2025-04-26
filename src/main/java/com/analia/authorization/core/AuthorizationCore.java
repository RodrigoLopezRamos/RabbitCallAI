package com.analia.authorization.core;

import com.analia.authorization.persistence.ClientFacade;
import com.analia.authorization.persistence.PermissionFacade;
import com.analia.authorization.persistence.RoleFacade;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Client;
import com.analia.common.model.Permission;
import com.analia.common.model.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.math.BigInteger;
import java.util.List;


@ApplicationScoped
public class AuthorizationCore {
    @Inject
    PermissionFacade permissionFacadeLocal;

    @Inject
    ClientFacade clientFacadeLocal;

    @Inject
    RoleFacade roleFacadeLocal;


    public Permission getPermission(String method, String endpoint, BigInteger userId, BigInteger vendorId) throws AnaliaException {
        return permissionFacadeLocal.getPermissionByEndpoint(endpoint, method, userId, vendorId);
    }

    public Client validateDevice(String platform, String appVersion, String deviceName, String deviceVersion) throws AnaliaException {
        throw new UnsupportedOperationException();
    }

    public Client getClientByCode(String code) throws AnaliaException {
        return clientFacadeLocal.getClientByCode(code);
    }

    public List<Role> getListOfActiveRoles(BigInteger min, BigInteger max) throws AnaliaException {
        return roleFacadeLocal.getListOfActiveRoles(min, max);
    }

}
