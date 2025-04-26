package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Vendor;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;

public interface VendorFacadeLocal extends PersistenceFacade<Vendor> {
    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Vendor getVendorByUserId(BigInteger userId) throws AnaliaException;

    /**
     *
     * @param externalId
     * @return
     * @throws AnaliaException
     */
    Vendor getVendorByExternalId(BigInteger externalId)throws  AnaliaException;



}
