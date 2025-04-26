package com.analia.vendor.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.AccountUser;
import com.analia.common.model.ValidLocation;
import com.analia.common.model.Vendor;
import com.analia.common.model.VendorLocation;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.model.resultset.view.VendorLocationDetailView;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface VendorCoreLocal {


    Vendor saveVendor(BigInteger id, BigInteger userId,
                             String name, String corporateName, String website, String email,
                             String voucherEmail, BigInteger externalId, boolean disabled) throws AnaliaException;

    /**
     * @param userId
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    AccountUser getAccountUserByUserAndVendorId(BigInteger userId, BigInteger vendorId, BigInteger createdBy) throws AnaliaException;

    /**
     * @param vendorLocationIds
     */
    void validateAvalaiblesLocationsToSaveAccountUserByVendor(BigInteger accountUserId, BigInteger[] vendorLocationIds, BigInteger userId) throws AnaliaException;

    /**
     * @param accountUserId
     * @throws AnaliaException
     */
    void removeAllValidLocationForUserInAccountUser(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param validLocationId
     * @param vendorLocationId
     * @param accountUserId
     * @param userId
     * @throws AnaliaException
     */
    ValidLocation saveValidLocation(BigInteger validLocationId, BigInteger vendorLocationId, BigInteger accountUserId, boolean defaultLocation, BigInteger userId, BigInteger createdBy) throws AnaliaException;


    /**
     * v
     * @param id
     * @param vendorId
     * @param locationId
     * @param directoryId
     * @param name
     * @param phoneNumber
     * @param locationEmail
     * @param mainOffice
     * @param createdBy
     * @param startTime
     * @param endTime
     * @param createdDatetime
     * @return
     * @throws AnaliaException
     */
    VendorLocation saveVendorLocation(BigInteger id, BigInteger vendorId, BigInteger locationId, BigInteger directoryId, String name, String phoneNumber, String locationEmail, boolean mainOffice, BigInteger createdBy, Date startTime, Date endTime, Date createdDatetime)throws  AnaliaException;


    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getVendorLocationViewForVendorId(BigInteger vendorId) throws AnaliaException;

    /**
     * @param vendorId
     * @return
     */
    List<AccountUserView> getAllAccountUsersForVendorId(BigInteger vendorId) throws AnaliaException;

    /**
     * @param currentUserId
     * @param createdBy
     * @param vendorId
     * @return
     */
    AccountUser getAccountUserByVendorIdAndCreatedByUserId(BigInteger currentUserId, BigInteger createdBy, BigInteger vendorId) throws AnaliaException;

    /**
     * @param accountUserId
     * @param vendorId
     * @param userId
     * @param disabled
     * @throws AnaliaException
     */
    AccountUser saveAccountUserRole(BigInteger accountUserId, BigInteger vendorId, BigInteger userId, boolean disabled, BigInteger createdBy) throws AnaliaException;

    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    Vendor getVendorById(BigInteger vendorId) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Vendor getVendorForUserId(BigInteger userId) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocation getVendorLocationById(BigInteger vendorLocationId) throws AnaliaException;

    /**
     * @param accountUserId
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    int setVendorLoginDefaultLocation(BigInteger accountUserId, BigInteger vendorLocationId) throws AnaliaException;

    /**
     * @param accountUserId
     * @return
     * @throws AnaliaException
     */
    List<ValidLocation> getListOfValidLocationsWithDefaultLocation(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param accountUserId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocation> getValidLocationsForUserId(BigInteger accountUserId, BigInteger userId) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getValidVendorlocationDetailForUserAndVendor(BigInteger vendorId, BigInteger userId) throws AnaliaException;


    /**
     * @param accountUserId
     */
    void UnSetAllDefaultLocations(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

    /**
     *
     * @param externalId
     * @return
     * @throws AnaliaException
     */
    Vendor getVendorByExternalId(BigInteger externalId)throws AnaliaException;

}
