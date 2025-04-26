package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Vendor;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.VendorFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigInteger;

@ApplicationScoped
public class VendorFacade extends JPAPersistenceFacade<Vendor> implements VendorFacadeLocal {
    private static final String QUERY_GET_VENDOR_FOR_USER_ID = "getVendorByUserId";
    private static final String QUERY_GET_VENDOR_BY_EXTERNAL_ID = "getVendorByExternalId";

    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_EXTERNAL_ID = "externalId";

    @Inject
    private EntityManager entityManager;

    public VendorFacade() {
        super(Vendor.class);
    }





    public Vendor getVendorByUserId(BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_VENDOR_FOR_USER_ID, new JpqlParameter(PARAM_USER_ID, userId));
    }


    public Vendor getVendorByExternalId(BigInteger externalId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_VENDOR_BY_EXTERNAL_ID, new JpqlParameter(PARAM_EXTERNAL_ID, externalId));
    }
}
