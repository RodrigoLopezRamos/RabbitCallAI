package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface VendorLocationDetailViewFacadeLocal extends PersistenceFacade<VendorLocationDetailView> {
    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getVendorLocationViewForVendorId(BigInteger vendorId) throws AnaliaException;

    /**
     * @param accountUserId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getValidVendorlocationDetailForUserAndVendor(BigInteger accountUserId, BigInteger userId) throws AnaliaException;

    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

}
