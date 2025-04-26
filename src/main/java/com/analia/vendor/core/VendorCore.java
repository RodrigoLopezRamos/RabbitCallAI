package com.analia.vendor.core;

import com.analia.common.constants.Constants;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.BaseException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.AccountUser;
import com.analia.common.model.ValidLocation;
import com.analia.common.model.Vendor;
import com.analia.common.model.VendorLocation;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.common.util.MapUtil;
import com.analia.vendor.core.VendorCoreLocal;
import com.analia.vendor.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VendorCore implements VendorCoreLocal {
    @Inject
    private ValidLocationFacadeLocal validLocationFacadeLocal;
    @Inject
    private VendorLocationFacadeLocal vendorLocationFacadeLocal;
    @Inject
    private VendorFacadeLocal vendorFacadeLocal;
    @Inject
    private AccountUserRoleFacadeLocal accountUserRoleFacadeLocal;
    @Inject
    private VendorLocationDetailViewFacadeLocal vendorLocationDetailViewFacadeLocal;
    @Inject
    private AccountUserViewFacadeLocal accountUserViewFacadeLocal;


    @Transactional
    public Vendor saveVendor(BigInteger id, BigInteger userId,
                             String name, String corporateName, String website, String email,
                             String voucherEmail, BigInteger externalId, boolean disabled) throws AnaliaException {
        Vendor vendor = null;
        try {
            // Check if the vendor already exists
            if ((vendor = getVendorById(id)) == null) {
                vendor = new Vendor(); // Assuming Vendor is the correct entity class
                vendor.setId(id);
                vendor.setCreatedBy(userId);
                vendor.setCreatedDatetime(new Date());
            }
            // Set the vendor attributes
            vendor.setName(name);
            vendor.setCorporateName(corporateName);
            vendor.setPhoneNumber("23232323223");
            vendor.setWebsite(website);
            vendor.setEmail(email);
            vendor.setVoucherEmail(voucherEmail);
            vendor.setExternalId(externalId);
            vendor.setDirectoryId(BigInteger.ONE);
            vendor.setDisabled(disabled);
            vendor.setTimestamp(new Timestamp(System.currentTimeMillis()));

            // Save the vendor
            vendorFacadeLocal.save(vendor);
        } catch (BaseException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
        return vendor;
    }

    /**
     *
     */
    @Transactional
    public AccountUser getAccountUserByUserAndVendorId(BigInteger userId, BigInteger vendorId, BigInteger createdBy) throws AnaliaException {
        AccountUser accountUser = accountUserRoleFacadeLocal.getAccountUserRoleForUserAndVendorId(userId, vendorId);
        if (accountUser == null) {
            accountUser = new AccountUser();
            accountUser.setUserId(userId);
            accountUser.setVendorId(vendorId);
            accountUser.setCreatedDatetime(new Date());
            accountUser.setCreatedBy(createdBy);
        }
        if (accountUser.isDisabled()) {
            accountUser.setDisabled(false);
        }
        try {
            accountUserRoleFacadeLocal.save(accountUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e);
        }
        return accountUser;

    }

    /**
     *
     */

    public void validateAvalaiblesLocationsToSaveAccountUserByVendor(BigInteger accountUserId, BigInteger[] vendorLocationIds, BigInteger userId) throws AnaliaException {
        if (vendorLocationIds.length > 0) {
            List<VendorLocation> listAvailableMerchantLocations = this.getValidLocationsForUserId(accountUserId, userId);
            Map<?, ?> hashMap = MapUtil.convertListToBooleanMapKey(listAvailableMerchantLocations);
            for (BigInteger locationId : vendorLocationIds) {
                if (!hashMap.containsKey(locationId)) {
                    throw new AnaliaException(ExceptionCode.INVALID_LOCATION_ID_PARAMETER_SPECIFIED, "invalid location id specified");
                }
            }
        }
    }

    /**
     *
     */

    public void removeAllValidLocationForUserInAccountUser(BigInteger accountUserId) throws AnaliaException {
        this.validLocationFacadeLocal.removeAllValidLocationForUserInAccountUser(accountUserId);
    }

    /**
     *
     */

    public ValidLocation saveValidLocation(BigInteger validLocationId, BigInteger vendorLocationId, BigInteger accountUserId, boolean defaultLocation, BigInteger userId, BigInteger createdBy) throws AnaliaException {
        ValidLocation validLocation = null;
        try {
            if ((validLocationFacadeLocal.find(validLocationId)) == null) {
                validLocation = new ValidLocation();
                validLocation.setCreatedBy(createdBy);
                validLocation.setCreatedDatetime(new Date());
            }
            validLocation.setId(validLocationId);
            validLocation.setVendorLocationId(vendorLocationId);
            validLocation.setAccountUserId(accountUserId);
            validLocation.setDefaultLocation(defaultLocation);

            validLocationFacadeLocal.save(validLocation);
            validLocationFacadeLocal.flush();
        } catch (AnaliaException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
        return validLocation;
    }


    public VendorLocation saveVendorLocation(BigInteger id, BigInteger vendorId, BigInteger locationId, BigInteger directoryId, String name, String phoneNumber, String locationEmail, boolean mainOffice, BigInteger createdBy, Date startTime, Date endTime, Date createdDatetime) throws AnaliaException {
        VendorLocation vendorLocation = null;
        try {
            if ((vendorLocation = vendorLocationFacadeLocal.find(id)) == null) {
                vendorLocation = new VendorLocation();
                vendorLocation.setBusinessScheduleId(BigInteger.ONE);
                vendorLocation.setCreatedBy(createdBy);
                vendorLocation.setCreatedDatetime(new Date());
            }

            vendorLocation.setVendorId(vendorId);
            vendorLocation.setLocationId(locationId);
            vendorLocation.setDirectoryId(directoryId);
            vendorLocation.setEndTime(endTime);
            vendorLocation.setLocationEmail(locationEmail);
            vendorLocation.setMainOffice(mainOffice);
            vendorLocation.setName(name);
            vendorLocation.setPhoneNumber(phoneNumber);
            vendorLocation.setStartTime(startTime);

            vendorLocationFacadeLocal.save(vendorLocation);
            vendorLocationFacadeLocal.flush();
        } catch (AnaliaException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
        return vendorLocation;
    }

    @Transactional
    public AccountUser saveAccountUserRole(BigInteger id, BigInteger vendorId, BigInteger userId, boolean disabled, BigInteger createdBy) throws AnaliaException {
        AccountUser accountUserRole = null;
        try {
            if ((accountUserRole = accountUserRoleFacadeLocal.find(id)) == null) {
                accountUserRole = new AccountUser();
                accountUserRole.setId(id);
                accountUserRole.setCreatedBy(createdBy);
                accountUserRole.setCreatedDatetime(new Date());
            }
            accountUserRole.setVendorId(vendorId);
            accountUserRole.setUserId(userId);
            accountUserRole.setDisabled(disabled);
            accountUserRoleFacadeLocal.save(accountUserRole);
            accountUserRoleFacadeLocal.flush();
        } catch (AnaliaException baseException) {
            baseException.printStackTrace();
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
        return accountUserRole;
    }

    /**
     *
     */

    public List<VendorLocationDetailView> getVendorLocationViewForVendorId(BigInteger vendorId) throws AnaliaException {
        return vendorLocationDetailViewFacadeLocal.getVendorLocationViewForVendorId(vendorId);
    }


    public List<AccountUserView> getAllAccountUsersForVendorId(BigInteger vendorId) throws AnaliaException {
        return accountUserViewFacadeLocal.getAllAccountUsersForVendorId(vendorId);
    }


    public AccountUser getAccountUserByVendorIdAndCreatedByUserId(BigInteger currentUserId, BigInteger createdBy, BigInteger vendorId) throws AnaliaException {
        return accountUserRoleFacadeLocal.getAccountUserByVendorIdAndCreatedByUserId(currentUserId, createdBy, vendorId);
    }


    public Vendor getVendorById(BigInteger vendorId) throws AnaliaException {
        return vendorFacadeLocal.find(vendorId);
    }


    public Vendor getVendorForUserId(BigInteger userId) throws AnaliaException {
        return vendorFacadeLocal.getVendorByUserId(userId);
    }


    public VendorLocation getVendorLocationById(BigInteger vendorLocationId) throws AnaliaException {
        return vendorLocationFacadeLocal.getVendorLocationById(vendorLocationId);
    }


    public int setVendorLoginDefaultLocation(BigInteger accountUserId, BigInteger vendorLocationId) throws AnaliaException {
        return validLocationFacadeLocal.setDefaultLocation(accountUserId, vendorLocationId);
    }


    public List<ValidLocation> getListOfValidLocationsWithDefaultLocation(BigInteger accountUserId) throws AnaliaException {
        return validLocationFacadeLocal.getListOfValidLocationsWithDefaultLocation(accountUserId);
    }


    public List<VendorLocation> getValidLocationsForUserId(BigInteger accountUserId, BigInteger userId) throws AnaliaException {
        return vendorLocationFacadeLocal.getValidLocationsForUserId(accountUserId, userId);
    }


    public List<VendorLocationDetailView> getValidVendorlocationDetailForUserAndVendor(BigInteger accountUserId, BigInteger userId) throws AnaliaException {
        return vendorLocationDetailViewFacadeLocal.getValidVendorlocationDetailForUserAndVendor(accountUserId, userId);
    }


    public void UnSetAllDefaultLocations(BigInteger accountUserId) throws AnaliaException {
        validLocationFacadeLocal.UnSetAllDefaultLocation(accountUserId);

    }


    public List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return vendorLocationDetailViewFacadeLocal.getVendorLocationViewByVendorLocationVoucherId(vendorLocationVoucherId);
    }


    @Transactional
    public Vendor getVendorByExternalId(BigInteger externalId) throws AnaliaException {
        return vendorFacadeLocal.getVendorByExternalId(externalId);
    }
}
