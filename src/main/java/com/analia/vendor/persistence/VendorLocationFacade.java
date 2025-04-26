package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.VendorLocation;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.VendorLocationFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class VendorLocationFacade extends JPAPersistenceFacade<VendorLocation> implements VendorLocationFacadeLocal {
    public static final String QUERY_GET_VALID_LOCATIONS_FOR_USER_ID_AND_ACCOUNT = "getValidVendorLocationsForUserId";

    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_ACCOUNT_USER_ID = "accountUserId";

    @Inject
    private EntityManager entityManager;

    public VendorLocationFacade() {
        super(VendorLocation.class);
    }





    public List<VendorLocation> getValidLocationsForUserId(BigInteger accountUserId, BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_VALID_LOCATIONS_FOR_USER_ID_AND_ACCOUNT, new JpqlParameter(PARAM_USER_ID, userId), new JpqlParameter(PARAM_ACCOUNT_USER_ID, accountUserId));
    }


    public VendorLocation getVendorLocationById(BigInteger vendorLocationId) throws AnaliaException {
        return find(vendorLocationId);
    }


    public VendorLocation getMainVendorLocationByVendor(BigInteger vendorId) throws AnaliaException {
        Query query = entityManager.createQuery("select vl from VendorLocation vl where vl.vendorId =? and vl.mainOffice =true");
        query.setParameter(0, vendorId);
        VendorLocation vendorLocation = (VendorLocation) query.getSingleResult();
        return vendorLocation;
    }

}
