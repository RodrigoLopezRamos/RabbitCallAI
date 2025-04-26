package com.analia.vendor.service.impl;

import com.analia.authorization.core.AuthorizationCore;
import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.BaseException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.location.core.LocationCoreLocal;
import com.analia.location.service.LocationServiceLocal;
import com.analia.media.core.FileSystemCoreLocal;
import com.analia.user.core.UserCore;
import com.analia.vendor.core.VendorCoreLocal;
import com.analia.vendor.service.VendorServiceLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class VendorService implements VendorServiceLocal {
    @Inject
    private VendorCoreLocal vendorCoreLocal;
    @Inject
    private UserCore userCoreLocal;
    @Inject
    private AuthorizationCore authorizationCoreLocal;
    @Inject
    private LocationCoreLocal locationCoreLocal;
    @Inject
    private FileSystemCoreLocal fileSystemCoreLocal;
    @Inject
    private LocationServiceLocal locationServiceLocal;

    /**
     * @param userId
     * @param roleId
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws AnaliaException
     */
    public User saveUser(BigInteger userId, BigInteger roleId, String title, String firstName, String lastName, String emailAddress, String oldPassword, String newPassword, String phoneNumber, Date dateOfBirth) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        User user = this.userCoreLocal.getUserByIdentifier(emailAddress, vendorId, User.USER_TYPE_EMAIL_ID);
        UserRole userRole = this.userCoreLocal.getUserRole(vendorId, user.getId());
        if ((userId != null && userId.compareTo(BigInteger.ZERO) > 0)) {
            // updating an existing user...
            if (user == null) {
                user = this.userCoreLocal.getUserById(userId);
            } else if (userId != null && userId.compareTo(user.getId()) == 0) {
                throw new AnaliaException(ExceptionCode.DUPLICATE_EMAIL, "Email is invalid");
            } else {
                oldPassword = newPassword;
            }
            if (user != null) {
                // if I am dealing with facebook user then throw an exception...
                if (user.getUserTypeId().compareTo(new BigInteger(Integer.toString(User.USER_TYPE_EMAIL_ID))) != 0) {
                    throw new AnaliaException(ExceptionCode.INVALID_FACEBOOK_EMAIL, "Cannot add or update facebook users");
                }
                if (!user.getPersona().getEmail().equals(emailAddress) && !this.userCoreLocal.isValidNewEmail(emailAddress)) {
                    throw new AnaliaException(ExceptionCode.EMAIL_NOT_VALID, "email is not valid");
                }
                this.checkLesserRoleId(user.getId(), userRole.getRoleId(), roleId);
                User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
                if (user.getId() != currentUser.getId()) {
                    // I am changing someone else...
                    if (oldPassword == null) {
                        throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "User password must be provided in order to make any changes");
                    }
                    if (!user.isPasswordValid(oldPassword)) {
                        throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "User email and/or password is invalid");
                    }
                }

                user.getPersona().setFirstName(firstName);
                user.getPersona().setLastName(lastName);
                user.getPersona().setEmail(emailAddress);
                user.getPersona().setDateOfBirth(dateOfBirth);
                user.getPersona().setPhoneNumber(phoneNumber);
                if (newPassword != null) {
                    user.setLoginToken(newPassword);
                }
                user = this.userCoreLocal.save(user);
            } else {
                throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "User not found");
            }
        } else {
            // it is a new user...
            this.checkLesserRoleId(new BigInteger("0"), new BigInteger("0"), roleId);
            if (newPassword == null) {
                throw new AnaliaException(ExceptionCode.INVALID_PASSWORD, "password is mandatory for new users!");
            }
            if (phoneNumber == null) {
                throw new AnaliaException(ExceptionCode.INVALID_PASSWORD, "phoneNumber is mandatory for new users!");
            }
            if (dateOfBirth == null) {
                throw new AnaliaException(ExceptionCode.INVALID_PASSWORD, "dateOfBirth is mandatory for new users!");
            }
            User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            user = this.userCoreLocal.saveUserAndLogout(new BigInteger("0"), new BigInteger(Integer.toString(User.USER_TYPE_EMAIL_ID)), roleId, false, firstName + " " + lastName, emailAddress, emailAddress, newPassword, "STAFF", firstName, lastName, phoneNumber, dateOfBirth, currentUser.getId(), false);
            Directory directory = fileSystemCoreLocal.saveDirectory(new BigInteger("0"), user.getId(), user.getId(), vendorId);
            userCoreLocal.saveUserDirectory(new BigInteger("0"), directory.getId(), Constants.USER_PROFILE_PICTURE, user.getId(), false);
        }
        return user;
    }

    /**
     *
     */

    public User saveAccountUserByVendor(BigInteger userId, BigInteger roleId, String title, String firstName, String lastName, String emailAddress, String oldPassword, String password, String phoneNumber, Date dateOfBirth, BigInteger... vendorLocationIds) throws AnaliaException {
        User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);

        /**
         *
         */
        boolean changingSelf = userId != null && userId.compareTo(currentUser.getId()) == 0;
        if (changingSelf && vendorLocationIds != null) {
            throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Unable to change your own locations");
        }
        User user = saveUser(userId, roleId, title, firstName, lastName, emailAddress, oldPassword, password, phoneNumber, dateOfBirth);
        AccountUser accountUser = vendorCoreLocal.getAccountUserByUserAndVendorId(user.getId(), vendor.getId(), currentUser.getId());

        if (!changingSelf) {
            AccountUser currentAccountUser = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.ACCOUNT_USER, AccountUser.class);
            this.vendorCoreLocal.validateAvalaiblesLocationsToSaveAccountUserByVendor(currentAccountUser.getId(), vendorLocationIds, currentUser.getId());
            this.vendorCoreLocal.removeAllValidLocationForUserInAccountUser(accountUser.getId());
            for (BigInteger vendorLocationId : vendorLocationIds) {
                vendorCoreLocal.saveValidLocation(new BigInteger("0"), vendorLocationId, accountUser.getId(), false, user.getId(), currentUser.getId());
            }
        }
        return user;
    }


    /**
     *
     */

    public User saveAccountInfoByVendor(BigInteger userId, BigInteger roleId, BigInteger... vendorLocationIds) throws AnaliaException {
        User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);

        User user = this.userCoreLocal.getUserById(userId);

        if (user == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "User not found");
        }
        UserRole userRole = this.userCoreLocal.getUserRole(vendor.getId(), user.getId());

        if (userRole.getRoleId().compareTo(roleId) != 0) {
            this.checkLesserRoleId(userId, userRole.getRoleId(), roleId);
            userRole.setRoleId(roleId);
            this.userCoreLocal.save(user);
            this.userCoreLocal.saveUserRole(userRole);
        }
        boolean changingSelf = userId.compareTo(currentUser.getId()) == 0;
        if (changingSelf && vendorLocationIds != null) {
            throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Unable to change your own locations");
        }
        AccountUser accountUser = vendorCoreLocal.getAccountUserByUserAndVendorId(user.getId(), vendor.getId(), currentUser.getId());
        if (!changingSelf) {
            AccountUser currentUserAccount = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.ACCOUNT_USER, AccountUser.class);
            this.vendorCoreLocal.validateAvalaiblesLocationsToSaveAccountUserByVendor(currentUserAccount.getId(), vendorLocationIds, currentUser.getId());
            this.vendorCoreLocal.removeAllValidLocationForUserInAccountUser(accountUser.getId());
            for (BigInteger merchantLocationId : vendorLocationIds) {
                vendorCoreLocal.saveValidLocation(BigInteger.ZERO, merchantLocationId, accountUser.getId(), false, user.getId(), currentUser.getId());
            }
        }
        return user;
    }

    /**
     * @return
     * @throws AnaliaException
     */

    public List<VendorLocationDetailView> getAllVendorLocationDetailViewForVendor() throws AnaliaException {
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
        return vendorCoreLocal.getVendorLocationViewForVendorId(vendor.getId());
    }

    /***
     *
     */

    public List<Role> getVendorListOfRoles() throws AnaliaException {
        User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        UserRole userRole = userCoreLocal.getUserRole(vendorId, currentUser.getId());
        return authorizationCoreLocal.getListOfActiveRoles(Constants.VENDOR_MIN_ROLE_ID, BigInteger.valueOf(Math.max(Constants.VENDOR_MIN_ROLE_ID.longValue(), userRole.getRoleId().subtract(BigInteger.ONE).longValue())));
    }

    /**
     *
     */

    public List<AccountUserView> getListAccountUserForVendor() throws AnaliaException {
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
        List<AccountUserView> accountUserViews = vendorCoreLocal.getAllAccountUsersForVendorId(vendor.getId());
        if (accountUserViews == null || accountUserViews.isEmpty()) {
            throw new AnaliaException(ExceptionCode.VENDOR_ACCCOUNT_NOT_FOUND);
        }
        return accountUserViews;
    }

    /**
     *
     */

    public void disableAccountUser(BigInteger userId) throws AnaliaException {
        User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
        AccountUser accountUser = vendorCoreLocal.getAccountUserByVendorIdAndCreatedByUserId(currentUser.getId(), userId, vendor.getId());
        if (accountUser == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "No account user found in the database");
        }
        User user = userCoreLocal.getUserById(userId);
        UserRole userRole = this.userCoreLocal.getUserRole(vendor.getId(), user.getId());
        userRole.setRoleId(BigInteger.valueOf(100));
        userCoreLocal.save(user);
        vendorCoreLocal.saveAccountUserRole(accountUser.getId(), accountUser.getVendorId(), accountUser.getUserId(), true, currentUser.getId());
    }

    /**
     *
     */
    public AccountUser getCurrentAccountUser(BigInteger vendorId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = null;
        AccountUser accountUser = null;

        if (user.isSystemUser()) {
            if (vendorId == null || vendorId.compareTo(new BigInteger("0")) == 0) {
                throw new AnaliaException(ExceptionCode.VENDOR_AUTHENTICATION_FAILED, "no vendor code specified");
            }
            accountUser = vendorCoreLocal.getAccountUserByUserAndVendorId(user.getId(), vendorId, user.getId());
            vendor = vendorCoreLocal.getVendorById(vendorId);
        } else {
            vendor = vendorCoreLocal.getVendorForUserId(user.getId());
            if (vendor == null) {
                throw new AnaliaException(ExceptionCode.VENDOR_AUTHENTICATION_FAILED, "no vendor found for this user account ");
            }
            accountUser = vendorCoreLocal.getAccountUserByUserAndVendorId(user.getId(), vendor.getId(), user.getId());
        }
        if (accountUser == null) {
            throw new AnaliaException(ExceptionCode.INVALID_INPUT_VALUES, "Account for user is not found!");
        }
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.VENDOR, vendor);
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.ACCOUNT_USER, accountUser);

        return accountUser;
    }

    /**
     *
     */

    public int setVendorLoginDefaultLocationForUserId(BigInteger vendorLocationId) throws AnaliaException {
        AccountUser accountUser = getCurrentAccountUser(null);
        VendorLocation vendorLocation = vendorCoreLocal.getVendorLocationById(vendorLocationId);
        if (vendorLocation == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "VendorLocation is not found");
        }
        return vendorCoreLocal.setVendorLoginDefaultLocation(accountUser.getId(), vendorLocation.getId());
    }

    /**
     *
     */

    public List<ValidLocation> getValidLocationsForAccountUserWithDefaultLocation(BigInteger accountUserId) throws AnaliaException {
        return vendorCoreLocal.getListOfValidLocationsWithDefaultLocation(accountUserId);
    }

    /**
     *
     */

    public List<VendorLocation> getValidLocationForUserAndVendor(BigInteger vendorId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        AccountUser accountUser = getCurrentAccountUser(vendorId);
        List<VendorLocation> vendorLocations = vendorCoreLocal.getValidLocationsForUserId(accountUser.getId(), user.getId());
        switch (vendorLocations.size()) {
            case 0:
                throw new AnaliaException(ExceptionCode.INVALID_LOCATION_ID_PARAMETER_SPECIFIED, "No valid locations found for user");
            case 1:
                vendorCoreLocal.setVendorLoginDefaultLocation(accountUser.getId(), vendorLocations.get(0).getId());
                break;
            default:
                vendorCoreLocal.UnSetAllDefaultLocations(accountUser.getId());
                break;
        }
        return vendorLocations;
    }

    /**
     *
     */

    public List<VendorLocationDetailView> getVendorLocationViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        List<VendorLocationDetailView> vendorLocationDetailViews = vendorCoreLocal.getVendorLocationViewByVendorLocationVoucherId(vendorLocationVoucherId);

        if (vendorLocationDetailViews == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "There are not location for this VOUCHER :" + vendorLocationVoucherId);
        }
        return vendorLocationDetailViews;
    }

    /**
     *
     */

    public Location saveVendorLocation(BigInteger vendorLocationId, String companyName, String merchantName, String address1, String address2, String city, String province, String postalCode, String country, String phoneNumber, String locationName, String locationEmail, double longitude, double latitude) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);

        VendorLocation vendorLocation = vendorLocationId != null ? vendorCoreLocal.getVendorLocationById(vendorLocationId) : null;
        Location location = vendorLocation != null ? locationCoreLocal.getLocation(vendorLocation.getLocationId()) : null;

        if (location == null) {
            City cityId = locationCoreLocal.getCityByName(city);
            if (cityId == null) {
                throw new AnaliaException(ExceptionCode.BAD_REQUEST, "City was not found!");
            }
            location = new Location();
            location.setAddress1(address1);
            location.setAddress2(address2);
            location.setCity(city);
            location.setCityId(cityId.getId());
            location.setCountry(country);
            location.setPostalOrZipcode(postalCode);
            location.setProvince(province);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setCreatedBy(user.getId());

            locationCoreLocal.saveLocation(location);
            Directory directory = fileSystemCoreLocal.saveDirectory(BigInteger.ZERO, BigInteger.ONE, null, vendor.getId());
            vendorLocation = vendorCoreLocal.saveVendorLocation(BigInteger.ZERO, vendor.getId(), location.getId(), directory.getId(), locationName, phoneNumber, locationEmail, true, new BigInteger(Integer.toString(Constants.SYSTEM_USER_ID)), new Date(), new Date(), new Date());
            vendorLocation.setBusinessScheduleId(BigInteger.ONE);

            return location;
        }

        location.setAddress1(address1);
        location.setAddress2(address2);
        location.setCity(city);
        location.setCountry(country);
        location.setPostalOrZipcode(postalCode);
        location.setProvince(province);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        locationCoreLocal.saveLocation(location);
        return location;
    }

    /**
     *
     */

    public VendorLocation getVendorLocation(BigInteger vendorLocationId) throws AnaliaException {
        return vendorCoreLocal.getVendorLocationById(vendorLocationId);
    }


    public Vendor getVendorByExternalId(BigInteger externalId) throws AnaliaException {
        return vendorCoreLocal.getVendorByExternalId(externalId);
    }

    /**
     *
     */

    public List<VendorLocationDetailView> getAllVendorLocationDetailViewForVendor(BigInteger vendorId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        AccountUser accountUser = getCurrentAccountUser(vendorId);
        List<VendorLocationDetailView> vendorLocationDetailViews = vendorCoreLocal.getValidVendorlocationDetailForUserAndVendor(accountUser.getId(), user.getId());
        switch (vendorLocationDetailViews.size()) {
            case 0:
                throw new AnaliaException(ExceptionCode.INVALID_LOCATION_ID_PARAMETER_SPECIFIED, "No valid locations found for user");
            case 1:
                vendorCoreLocal.setVendorLoginDefaultLocation(accountUser.getId(), vendorLocationDetailViews.get(0).getId());
                break;
            default:
                vendorCoreLocal.UnSetAllDefaultLocations(accountUser.getId());
                break;
        }
        return vendorLocationDetailViews;
    }

    /***
     *
     * @param userId
     * @param currentRole
     * @param newRoleId
     * @throws AnaliaException
     */
    private void checkLesserRoleId(BigInteger userId, BigInteger currentRole, BigInteger newRoleId) throws AnaliaException {
        User currentUser = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
        UserRole userRole = this.userCoreLocal.getUserRole(vendor.getId(), currentUser.getId());

        if (currentUser.getId() != userId) {
            // not myself...
            if (userRole.getRoleId().compareTo(newRoleId) < 0) {
                throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Unable to set a role equal or greater to your own role");
            }
            if (userRole.getRoleId().compareTo(newRoleId) <= 0) {
                // I cannot change someone with same or higher role id...
                throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Unable to change a user with higher or equal role than your own");
            }
        } else {
            // changing to myself
            if (userRole.getRoleId().compareTo(newRoleId) < 0) {
                throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Unable to set a role equal or greater to your own role");
            }
        }
    }


    public Vendor getVendor(BigInteger vendorId) throws AnaliaException {
        return vendorCoreLocal.getVendorById(vendorId);
    }


    public VendorLocation saveVendorLocation(BigInteger id, BigInteger vendorId, BigInteger locationId,
                                             BigInteger directoryId, String name, String phoneNumber, String locationEmail, boolean mainOffice, BigInteger createdBy, Date startTime, Date endTime, Date createdDatetime) throws AnaliaException {
        return vendorCoreLocal.saveVendorLocation(id, vendorId, locationId, directoryId, name, phoneNumber, locationEmail, mainOffice, createdBy, startTime, endTime, createdDatetime);
    }


    @Transactional
    public AccountUser signUpVendor(String name, String title, String email, String description, String address, String website, String city, String province, String phone, String postalOrZipcode, String country, double latitude, double longitude, BigInteger bussinesScheduleId, BigInteger userId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        int vendorId = AnaliaUserContext.getContext().getMandatoryIntForKey(AnaliaUserContext.VENDOR_ID);

        UserRole userRole = this.userCoreLocal.getUserRole(BigInteger.valueOf(vendorId), user.getId());
        userRole.setRoleId(new BigInteger(Integer.toString(Role.VENDOR_ADMIN)));

        userCoreLocal.saveUserRole(userRole);

        Zone zone = locationServiceLocal.getZoneByName(city);

        if (zone == null) {
            zone = new Zone();
            zone.setActive(true);
            zone.setCenterLatitude(latitude);
            zone.setCenterLongitude(longitude);
            zone.setCountry(country);
            zone.setCurrencyType(country.equals("Canada") ? "CAD" : "USD");
            zone.setMaxRadius(100);
            zone.setTimezone("EST");
            zone.setDescription(country + " : " + city);
            zone.setName(city);
            locationServiceLocal.saveZone(zone);
        }
        City cityObj = locationServiceLocal.getCityByName(city);
        if (cityObj == null) {
            cityObj = new City();
            cityObj.setZoneId(zone.getId());
            cityObj.setProvince(province);
            cityObj.setName(city);
            cityObj.setActive(true);
            locationServiceLocal.saveCity(cityObj);
        }

        Location location = new Location();
        location.setAddress1(address);
        location.setCityId(cityObj.getId());
        location.setCity(city);
        location.setProvince(province);
        location.setCountry(country);
        location.setPostalOrZipcode(postalOrZipcode);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        locationServiceLocal.saveLocation(location);

        Directory directory = fileSystemCoreLocal.saveDirectory(new BigInteger("0"), new BigInteger("1"), null, BigInteger.valueOf(vendorId));
        Vendor vendor = vendorCoreLocal.saveVendor(new BigInteger("0"), userId, name, website, website, email, email, null, false);

        VendorLocation vendorLocation = vendorCoreLocal.saveVendorLocation(new BigInteger("0"), vendor.getId(), location.getId(), directory.getId(), name, phone, name, true, new BigInteger(Integer.toString(Constants.SYSTEM_USER_ID)), new Date(), new Date(), new Date());

        AccountUser accountUser = vendorCoreLocal.getAccountUserByUserAndVendorId(userId, vendor.getId(), new BigInteger(Integer.toString(Constants.SYSTEM_USER_ID)));

        vendorCoreLocal.saveValidLocation(new BigInteger("0"), vendorLocation.getId(), accountUser.getId(), true, userId, user.getId());

        return accountUser;
    }

}
