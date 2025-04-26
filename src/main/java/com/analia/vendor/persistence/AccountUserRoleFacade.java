package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.AccountUser;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.AccountUserRoleFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;

@ApplicationScoped
public class AccountUserRoleFacade extends JPAPersistenceFacade<AccountUser> implements AccountUserRoleFacadeLocal {
    private static final String QUERY_GET_ACCOUNT_USER_FOR_USER_AND_VENDOR_ID = "getAccountUserRoleForUserAndVendorId";
    private static final String QUERY_GET_ACCOUNT_BY_VENDOR_ID_AND_CREATED_BY_ID = "getAccountUserByVendorIdAndCreatedByUserId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_VENDOR_ID = "vendorId";
    private static final String PARAM_CREATED_BY = "createdByUserId";
    @Inject
    private EntityManager entityManager;

    public AccountUserRoleFacade() {
        super(AccountUser.class);
    }



    public AccountUser getAccountUserRoleForUserAndVendorId(BigInteger userId, BigInteger vendorId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_ACCOUNT_USER_FOR_USER_AND_VENDOR_ID, new JpqlParameter(PARAM_USER_ID, userId), new JpqlParameter(PARAM_VENDOR_ID, vendorId));
    }


    public AccountUser getAccountUserByVendorIdAndCreatedByUserId(BigInteger currentUserId, BigInteger createdBy, BigInteger vendorId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_ACCOUNT_BY_VENDOR_ID_AND_CREATED_BY_ID, new JpqlParameter(PARAM_USER_ID, currentUserId), new JpqlParameter(PARAM_VENDOR_ID, vendorId), new JpqlParameter(PARAM_CREATED_BY, createdBy));
    }



}
