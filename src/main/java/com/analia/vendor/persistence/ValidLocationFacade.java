package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.ValidLocation;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.ValidLocationFacadeLocal;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.List;


/**
 * Session Bean implementation class ValidLocationFacade
 */
@ApplicationScoped
public class ValidLocationFacade extends JPAPersistenceFacade<ValidLocation> implements ValidLocationFacadeLocal {
    private static final String QUERY_GET_VALID_LOCATIONS_FOR_ACCOUNT_USER_ROLE_ID = "getListOfValidLocations";
    private static final String QUERY_UPDATE_SET_ZERO_FOR_DEFAULT_LOCATION = "UnSetAllDefaultLocation";
    private static final String QUERY_UPDATE_SET_VENDOR_LOGIN_DEFAULT_LOCATION = "setVendorDefaultLocation";
    private static final String QUERY_GET_ALL_VALID_LOCATIONS_FOR_VOUCHER = "getListOfValidLocationsForVoucher";
    private static final String QUERY_GET_VALID_LOCATION_FOR_VENDOR_LOCATION_ID_ACCOUNT_USER_ID = "";
    private static final String QUERY_GET_VALID_LOCATIONS_WITH_DEFAULT_LOCATION_FOR_ACCOUNT_USER_ROLE_ID = "getValidLocationsWithDefaultLocationForAccountUserRoleId";
    private static final String QUERY_REMOVE_ALL_VALID_LOCATIONS_FOR_USER = "removeAllValidLocationForUserInAccountUser";


    private static final String PARAM_DIGITAL_VOUCHER = null;
    private static final String PARAM_VENDOR_LOCATION_ID = "vendorLocationId";
    private static final String PARAM_ACCOUNT_USER_ROLE_ID = "accountUserId";


    @Inject
    private EntityManager entityManager;

    public ValidLocationFacade() {
        super(ValidLocation.class);
    }




    /**
     *
     */

    public List<ValidLocation> getListOfValidLocations(BigInteger accountUserRoleId) throws AnaliaException {
        JpqlParameter jpqlAccountUserRoleIdParameter = new JpqlParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserRoleId);
        return getListForNamedQuery(QUERY_GET_VALID_LOCATIONS_FOR_ACCOUNT_USER_ROLE_ID, jpqlAccountUserRoleIdParameter);
    }

    /**
     *
     */

    public int UnSetAllDefaultLocation(BigInteger accountUserRoleId) throws AnaliaException {
        JpqlParameter jpqlAccountUserRoleIdParameter = new JpqlParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserRoleId);
        return updateEntity(QUERY_UPDATE_SET_ZERO_FOR_DEFAULT_LOCATION, jpqlAccountUserRoleIdParameter);
    }

    /**
     *
     */
     @Transactional
    public int setDefaultLocation(BigInteger accountUserRoleId, BigInteger vendorLocationId) throws AnaliaException {
        JpqlParameter jpqlAccountUserRoleIdParameter = new JpqlParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserRoleId);
        JpqlParameter jpqlVendorLocationIdParameter = new JpqlParameter(PARAM_VENDOR_LOCATION_ID, vendorLocationId);
        return updateEntity(QUERY_UPDATE_SET_VENDOR_LOGIN_DEFAULT_LOCATION, jpqlAccountUserRoleIdParameter, jpqlVendorLocationIdParameter);
    }

    /**
     *
     */

    public List<ValidLocation> getListOfValidLocationsForVoucher(String voucherCode) throws AnaliaException {
        JpqlParameter jpqlVoucherCodeParameter = new JpqlParameter(PARAM_DIGITAL_VOUCHER, voucherCode);
        return getListForNamedQuery(QUERY_GET_ALL_VALID_LOCATIONS_FOR_VOUCHER, jpqlVoucherCodeParameter);
    }

    /**
     *
     */

    public ValidLocation getValLocForVendorLocIdAndAccountUser(BigInteger vendorLocationId, BigInteger accountUserId) throws AnaliaException {
        JpqlParameter jpqlVendorLocationIdParameter = new JpqlParameter(PARAM_VENDOR_LOCATION_ID, vendorLocationId);
        JpqlParameter jpqlAccountUserParameter = new JpqlParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserId);
        return getPersistForNamedQuery(QUERY_GET_VALID_LOCATION_FOR_VENDOR_LOCATION_ID_ACCOUNT_USER_ID, jpqlVendorLocationIdParameter, jpqlAccountUserParameter);
    }

    /**
     *
     */

    public List<ValidLocation> getListOfValidLocationsWithDefaultLocation(BigInteger accountUserRoleId) throws AnaliaException {
        JpqlParameter jpqlAccountUserRoleIdParameter = new JpqlParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserRoleId);
        return getListForNamedQuery(QUERY_GET_VALID_LOCATIONS_WITH_DEFAULT_LOCATION_FOR_ACCOUNT_USER_ROLE_ID, jpqlAccountUserRoleIdParameter);
    }

    /**
     *
     */

    public void removeAllValidLocationForUserInAccountUser(BigInteger accountUserId) {
        Query query = entityManager.createNamedQuery(QUERY_REMOVE_ALL_VALID_LOCATIONS_FOR_USER);
        query.setParameter(PARAM_ACCOUNT_USER_ROLE_ID, accountUserId);
        query.executeUpdate();
    }


}
