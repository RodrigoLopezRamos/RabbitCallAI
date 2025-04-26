package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.VendorLocation;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface VendorLocationFacadeLocal extends PersistenceFacade<VendorLocation> {
    /**
     * @param accountUserId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocation> getValidLocationsForUserId(BigInteger accountUserId, BigInteger userId) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocation getVendorLocationById(BigInteger vendorLocationId) throws AnaliaException;


    /**
     *
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocation getMainVendorLocationByVendor(BigInteger vendorLocationId) throws AnaliaException;


}
