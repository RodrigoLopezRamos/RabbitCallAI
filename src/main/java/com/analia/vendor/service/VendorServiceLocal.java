package com.analia.vendor.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.model.resultset.view.VendorLocationDetailView;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface VendorServiceLocal {

    /**
     * @param merchantId
     * @return
     * @throws AnaliaException
     */
    AccountUser getCurrentAccountUser(BigInteger merchantId) throws AnaliaException;

    /**
     * @param vendorId
     * @return
     * @return
     * @throws AnaliaException /**
     * @throws AnaliaException
     */
    Vendor getVendor(BigInteger vendorId) throws AnaliaException;


    /****
     *
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
    VendorLocation saveVendorLocation(BigInteger id, BigInteger vendorId, BigInteger locationId, BigInteger directoryId, String name, String phoneNumber, String locationEmail, boolean mainOffice, BigInteger createdBy, Date startTime, Date endTime, Date createdDatetime) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @param companyName
     * @param merchantName
     * @param address1
     * @param address2
     * @param city
     * @param province
     * @param postalCode
     * @param country
     * @param phoneNumber
     * @param locationName
     * @param email
     * @param latitude
     * @param longitude
     * @return
     * @throws AnaliaException
     */
    Location saveVendorLocation(BigInteger vendorLocationId, String companyName, String merchantName, String address1, String address2, String city, String province, String postalCode, String country, String phoneNumber, String locationName, String email, double latitude, double longitude) throws AnaliaException;

    /**
     * @param userId
     * @param roleId
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param oldPassword
     * @param password
     * @param locations
     * @return
     * @throws AnaliaException
     */
    User saveAccountUserByVendor(BigInteger userId, BigInteger roleId, String title, String firstName, String lastName, String emailAddress, String oldPassword, String password, String phoneNumber, Date dateOfBirth, BigInteger... locations) throws AnaliaException;

    /**
     * @param userId
     * @param roleId
     * @param locations
     * @return
     * @throws AnaliaException
     */
    User saveAccountInfoByVendor(BigInteger userId, BigInteger roleId, BigInteger... locations) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<Role> getVendorListOfRoles() throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<AccountUserView> getListAccountUserForVendor() throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getAllVendorLocationDetailViewForVendor() throws AnaliaException;

    /**
     * @param accountUserId
     * @return
     * @throws AnaliaException
     */
    List<ValidLocation> getValidLocationsForAccountUserWithDefaultLocation(BigInteger accountUserId) throws AnaliaException;

    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocation> getValidLocationForUserAndVendor(BigInteger vendorId) throws AnaliaException;


    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    int setVendorLoginDefaultLocationForUserId(BigInteger vendorLocationId) throws AnaliaException;

    /**
     * @param userId
     * @throws AnaliaException
     */
    void disableAccountUser(BigInteger userId) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocation getVendorLocation(BigInteger vendorLocationId) throws AnaliaException;


    /**
     * @param externalId
     * @return
     */
    Vendor getVendorByExternalId(BigInteger externalId) throws AnaliaException;


    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    List<VendorLocationDetailView> getAllVendorLocationDetailViewForVendor(BigInteger vendorId) throws AnaliaException;

    /**
     * @param name
     * @param title
     * @param description
     * @param address
     * @param website
     * @param city
     * @param province
     * @param phone
     * @param postalOrZipcode
     * @param country
     * @param latitude
     * @param longitude
     * @param bussinesScheduleId
     * @return
     * @throws AnaliaException
     */
    AccountUser signUpVendor(String name, String title, String email, String description, String address, String website, String city, String province, String phone, String postalOrZipcode, String country, double latitude, double longitude, BigInteger bussinesScheduleId, BigInteger userId) throws AnaliaException;
}
