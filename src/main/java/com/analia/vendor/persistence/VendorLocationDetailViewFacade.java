package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.VendorLocationDetailViewFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class VendorLocationDetailViewFacade extends JPAPersistenceFacade<VendorLocationDetailView> implements VendorLocationDetailViewFacadeLocal {

    private static final String QUERY_GET_VENDOR_LOCATION_VIEW_FOR_VENDOR_ID = "getAllVendorLocationsViewForVendorId";

    private static final String QUERY_GET_VENDOR_LOCATIONS_FOR_USER_AND_VENDOR_ID = "getValidVendorLocationDetailViewForUserAndVendor";

    private static final String PARAM_VENDOR_ID = "vendorId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_ACCOUNT_USER_ID = "accountUserId";

    @Inject
    private EntityManager entityManager;

    public VendorLocationDetailViewFacade() {
        super(VendorLocationDetailView.class);
    }





    public List<VendorLocationDetailView> getVendorLocationViewForVendorId(BigInteger vendorId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_VENDOR_LOCATION_VIEW_FOR_VENDOR_ID, new JpqlParameter(PARAM_VENDOR_ID, vendorId));
    }


    public List<VendorLocationDetailView> getValidVendorlocationDetailForUserAndVendor(BigInteger accountUserId, BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_VENDOR_LOCATIONS_FOR_USER_AND_VENDOR_ID, new JpqlParameter(PARAM_ACCOUNT_USER_ID, accountUserId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return null;
    }

}
