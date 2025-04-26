package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.ValidLocation;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface ValidLocationFacadeLocal extends PersistenceFacade<ValidLocation> {
    /**
     * @param accountUserId
     * @throws AnaliaException
     */
    void removeAllValidLocationForUserInAccountUser(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param accountUserId
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    int setDefaultLocation(BigInteger accountUserId, BigInteger vendorLocationId) throws AnaliaException;

    /**
     * @param accountUserId
     * @return
     * @throws AnaliaException
     */
    List<ValidLocation> getListOfValidLocationsWithDefaultLocation(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param accountUserId
     * @throws AnaliaException
     */
    int UnSetAllDefaultLocation(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param accountUserRoleId
     * @return
     * @throws AnaliaException
     */
    List<ValidLocation> getListOfValidLocations(BigInteger accountUserRoleId) throws AnaliaException;

    /**
     * @param voucherCode
     * @return
     * @throws AnaliaException
     */
    List<ValidLocation> getListOfValidLocationsForVoucher(String voucherCode) throws AnaliaException;

    /***
     *
     * @param vendorLocationId
     * @param accountUserId
     * @return
     * @throws AnaliaException
     */
    ValidLocation getValLocForVendorLocIdAndAccountUser(BigInteger vendorLocationId, BigInteger accountUserId) throws AnaliaException;

}
