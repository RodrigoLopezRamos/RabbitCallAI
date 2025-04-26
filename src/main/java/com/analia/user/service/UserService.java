package com.analia.user.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.location.GeoLocation;
import com.analia.common.model.*;
import com.analia.common.util.ArrayUtils;
import com.analia.common.util.EmailUtil;
import com.analia.common.util.EncryptionUtil;
import com.analia.common.util.PseudoUniqueCodeUtils;
import com.analia.common.util.PseudoUniqueCodeUtils.PseudoUniqueCode;
import com.analia.location.core.impl.LocationCore;
import com.analia.media.core.FileSystemCore;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.user.core.UserActivityCore;
import com.analia.user.core.UserCore;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@ApplicationScoped
public class UserService {

    public static final String ACCOUNT_SID = "AC7cc65293e1c0b9ffdaf3f6b4401c0b78";
    public static final String AUTH_TOKEN = "bafeaf795c14ef8004b2dc78bbc3d741";

    /**
     *
     */
    public static final String PREFIX_GUEST_USER = "__guest:";

    public static final int TWENTY_FOUR_HOURS = 86400000;

    private static final int MAX_VALUE_ADDITIONAL_DATA_SIZE = 4096;

    @Inject
    private LocationCore locationCoreLocal;

    @Inject
    private UserCore userCoreLocal;

    @Inject
    private UserActivityCore userActivityCoreLocal;

    @Inject
    private SettingsCore settingsCoreLocal;

    @Inject
    private FileSystemCore fileSystemCoreLocal;


    @PostConstruct
    public void initTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     *
     */

    public User authenticateUser(String token) throws AnaliaException {
        User user = userCoreLocal.getUserByToken(token);
        if (user == null) {
            throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "Token invalid");
        }
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, user);
        return user;
    }


    public User externalUser(String token) throws AnaliaException {
        User user = userCoreLocal.getUserByExternalToken(token);
        if (user == null) {
            throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "Token invalid");
        }
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, user);
        return user;
    }

    /**
     *
     */
    @Transactional
    public User authenticateUser(int userTypeId, String email, String token) throws AnaliaException {
        Device device = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.DEVICE, Device.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        User user = userCoreLocal.getUserByIdentifier(email, vendorId, userTypeId);

        if (user == null) {
                throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "Wrong UserName or Password! Please check your credentials!");
        }
        if (user.isDisabled()) {
            throw new AnaliaException(ExceptionCode.USER_IS_DEACTIVATED, "User is deactivated");
        }
        if (user.getUserTypeId().compareTo(new BigInteger(Integer.toString(User.USER_TYPE_FACEBOOK_ID))) == 0) {
            if (!user.isPasswordValid(token)) {
                user.setLoginToken(token);
            }
        }
        user.generateSessionToken(token);
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, userCoreLocal.save(user));
        userCoreLocal.setDeviceToUser(device.getId(), user.getId());
        return user;
    }

    /**
     *
     */

    @Transactional
    public User authenticateGuest() throws AnaliaException {
        Device device = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.DEVICE, Device.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        String token = PREFIX_GUEST_USER + device.getUuid();
        User user = userCoreLocal.getUserByIdentifier(token, vendorId, User.USER_TYPE_EMAIL_ID);
        if (user == null) {
            Persona persona = new Persona();
            user = new User();
            persona.setEmail(token);
            persona.setTitle(PREFIX_GUEST_USER);
            persona.setFirstName(PREFIX_GUEST_USER);
            persona.setName(PREFIX_GUEST_USER);
            persona.setLastName(PREFIX_GUEST_USER);
            persona.setPhoneNumber(PREFIX_GUEST_USER);
            persona.setDateOfBirth(new Date());
            persona.setCreatedDatetime(new Date());
            persona.setGender("guest");
            persona.setTimestamp(new Timestamp(System.currentTimeMillis()));
            user.setSystemUser(false);
            user.setUserIdentifier(token);
            user.setUserTypeId(BigInteger.valueOf(User.USER_TYPE_EMAIL_ID));
            user.setCreatedDatetime(new Date());
            user.setCreatedBy(BigInteger.valueOf(Constants.SYSTEM_USER_ID));
            user.setConfirmationCode(PREFIX_GUEST_USER);
            user.setPersona(persona);
            user.setTimestamp(new Timestamp(System.currentTimeMillis()));
            userCoreLocal.save(persona);
            user.setPersonaId(persona.getId());
        }
        user.setLoginToken(token);
        user.generateSessionToken(token);
        userCoreLocal.save(user);
        userCoreLocal.setDeviceToUser(device.getId(), user.getId());
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, user);
        return user;
    }

    /**
     *
     */

    public void logout() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        user.logout();
        userCoreLocal.save(user);
    }

    /**
     *
     */

    public User validateEmailAndRetrieveUser(String email) throws AnaliaException {
        if (!EmailUtil.validate(email)) {
            throw new AnaliaException(ExceptionCode.EMAIL_NOT_VALID, "Email is not valid!");
        }
        return userCoreLocal.getUserByUserEmail(email);
    }

    /**
     *
     */

    public boolean isValidNewEmail(String email) throws AnaliaException {
        return userCoreLocal.isValidNewEmail(email);
    }

    /**
     *
     */

    @Transactional
    public User signUp(int userTypeId, String firstName, String lastName, String name, String email, String token,
                       String externalUserId, String phoneNumber, Date dateOfBirth, String gender) throws AnaliaException {
        BigInteger roleId = UserRole.ROLE_GUEST;
        String userIdentifier = email;
        boolean unconfirmedEmail = false;

        switch (userTypeId) {
            case User.USER_TYPE_EMAIL_ID:
                email = email.toLowerCase();
                userIdentifier = email;
                unconfirmedEmail = true;
                roleId = UserRole.ROLE_NOT_EMAIL_VALIDATED;
                break;
            case User.USER_TYPE_GMAIL_ID:
                roleId = UserRole.ROLE_EMAIL_VALIDATED;
                break;
            default:
                throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "UserType is not valid!");
        }

        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        UserRole userRole = userCoreLocal.getUserRole(vendorId, user.getId());

        if (this.validateEmailAndRetrieveUser(email) != null) {
            throw new AnaliaException(ExceptionCode.DUPLICATE_EMAIL, "User already exists");
        }
        if (userRole.getRoleId().compareTo(UserRole.ROLE_GUEST) != 0) {
            throw new AnaliaException(ExceptionCode.INVALID_OPERATION, "MUST BE GUEST!");
        }
        user.setUserTypeId(BigInteger.valueOf(userTypeId));
        user.setUnconfirmedEmail(unconfirmedEmail);
        user.getPersona().setName(name);
        user.getPersona().setFirstName(firstName);
        user.getPersona().setDateOfBirth(dateOfBirth);
        user.getPersona().setLastName(lastName);
        user.getPersona().setPhoneNumber(phoneNumber);
        user.getPersona().setEmail(email);
        user.getPersona().setGender(gender);
        user.setUserIdentifier(userIdentifier);
        user.setConfirmationCode(PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_SHORT));

        switch (userTypeId) {
            case User.USER_TYPE_EMAIL_ID:
                user.setLoginToken(token);
                user = userCoreLocal.save(user);
             //   sentSMS(user.getPersona().getPhoneNumber(),"Please insert the code :  ".concat(user.getConfirmationCode())); //TODO Welcome message
                break;
            case User.USER_TYPE_GMAIL_ID:
            case User.USER_TYPE_APPLE_ID:
                user = userCoreLocal.save(user);
                UserToken userToken = userCoreLocal.getUserToken(token);
                if (userToken == null) {
                    userToken = new UserToken();
                    userToken.setToken(token);
                }
                userToken.setUserId(user.getId());
                userCoreLocal.saveUserToken(userToken);
                break;
        }
        userRole.setRoleId(roleId);
        userCoreLocal.saveUserRole(userRole);
        return user;
    }

    public void sentSMS(String destinationNumber, String body) {
        Message
                .creator(
                        new PhoneNumber(destinationNumber),
                        new PhoneNumber("19493424934"),
                        body
                )
                .create();
    }

    /**
     *
     */

    public boolean resolveDeviceLocationAndTimeZone(double latitude, double longitude) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        boolean gpsOverriden = false;
        UserLocation userLocation = userCoreLocal.getDefaultUserLocation(user.getId());
        Zone zone = null;
        if (userLocation == null && (latitude == 0 || longitude == 0)) {
            throw new AnaliaException(ExceptionCode.USER_IS_OUT_OF_RANGE, "Cannot determine user location");
        } else if (userLocation == null) {
            List<Zone> zones = locationCoreLocal.getZonesFromCache();
            zone = ArrayUtils.sortByDistance(GeoLocation.getDistances(zones, latitude, longitude)).get(0);
            userLocation = new UserLocation();
            userLocation.setZoneId(zone.getId());
            if (!GeoLocation.isOutSideOfTheRadius(zone.getCenterLatitude(), zone.getCenterLongitude(), latitude, longitude, zone.getMaxRadius())) {
                userLocation.setLatitude(latitude);
                userLocation.setLongitude(longitude);
            } else {
                userLocation.setLatitude(zone.getCenterLatitude());
                userLocation.setLongitude(zone.getCenterLongitude());
                gpsOverriden = true;
            }
        }
        zone = zone == null ? locationCoreLocal.getZoneById(userLocation.getZoneId()) : zone;
        TimeZone timeZone = TimeZone.getTimeZone(zone.getTimezone());
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.add(Calendar.MILLISECOND, timeZone.getOffset(System.currentTimeMillis()));

        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_DATE_TIME, calendar.getTime());
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_LOCATION, userLocation);
        userCoreLocal.setUserLocation(userLocation.getLatitude(), userLocation.getLongitude(), userLocation.getZoneId(), user.getId());
        return gpsOverriden;
    }

    /**
     *
     */

    public UserLocation setUserLocation(double latitude, double longitude, BigInteger zoneId) throws AnaliaException {
        if (zoneId != null) {
            Zone zone = locationCoreLocal.getZoneById(zoneId);
            zoneId = zone.getId();
            latitude = zone.getCenterLatitude();
            longitude = zone.getCenterLongitude();
        }
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userCoreLocal.setUserLocation(latitude, longitude, zoneId, user.getId());
    }

    /**
     *
     */

    public UserTrending saveUserTrending(BigInteger tradeId, BigInteger voucherId, BigInteger storyId, boolean loved) throws AnaliaException {
        return userCoreLocal.saveUserTrending(tradeId, voucherId, storyId, loved, AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
    }

    /**
     *
     */

    public UserTrending getUserTrending(BigInteger tradeId, BigInteger voucherId) throws AnaliaException {
        return userCoreLocal.getUserTrending(tradeId, voucherId, AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
    }

    /**
     *
     */

    public List<UserTrending> getUsersTrending(BigInteger userId) throws AnaliaException {
        return userCoreLocal.getUsersTrending(userId);
    }


    public void deactiveAccount() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        user.setDisabled(true);
        userCoreLocal.save(user);
    }


    public List<UserProfile> getUserProfile() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userCoreLocal.getUserProfile(user.getId());
    }


    public List<Tag> getUserTags() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userCoreLocal.getUserTags(user.getId());
    }


    public void setUserTags(List<String> tags) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        userCoreLocal.setUserTags(user.getId(), tags);
    }


    public String forgotPassword(String email) throws AnaliaException {
        User user = userCoreLocal.getUserByUserEmail(email);
        if (user == null || user.isDisabled() || user.getUserTypeId().compareTo(BigInteger.valueOf(User.USER_TYPE_EMAIL_ID)) != 0) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "User account with specified email not found, it is not of type EMAIL, or it is not Active");
        }
        String newPassword = PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_STANDARD).substring(2);
        long validUntil = System.currentTimeMillis() + TWENTY_FOUR_HOURS;
        long userTimestamp = user.getTimestamp().getTime();
        String resetCode = newPassword + "|" + user.getId() + "|" + userTimestamp + "|" + validUntil;
        EncryptionUtil encryptor = new EncryptionUtil();
        return encryptor.encrypt(resetCode);
    }

    /**
     *
     */

    public void changeUsername(String username) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        user.getPersona().setName(username);
        this.userCoreLocal.save(user);
    }

    /**
     *
     */

    public User changeEmail(String newEmail) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        User existing = this.validateEmailAndRetrieveUser(newEmail);
        if (existing != null && existing.getId() != user.getId()) {
            throw new AnaliaException(ExceptionCode.EMAIL_NOT_VALID, "Email already exists on system!");
        } else if (existing == null) {
            user.getPersona().setEmail(newEmail);
            user.setUnconfirmedEmail(true);
            if (user.getUserTypeId().compareTo(BigInteger.valueOf(User.USER_TYPE_EMAIL_ID)) == 0) {
                user.setUserIdentifier(newEmail);
            }
            user.setConfirmationCode(PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_XTRA_LARGE));
            return this.userCoreLocal.save(user);
        } else {
            return user;
        }
    }

    /**
     *
     */

    public boolean confirmUser(String code) throws AnaliaException {

        User user = userCoreLocal.getUserByConfirmationCode(code);
        if (user == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND);
        }
        if (user.isUnconfirmedEmail()) {
            user.setUnconfirmedEmail(false);
            userCoreLocal.saveUser(user);
        }
        return true;
    }


    /**
     *
     */

    public User changePassword(String oldPassword, String newPassword) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        if (!user.isPasswordValid(oldPassword)) {
            throw new AnaliaException(ExceptionCode.CURRENT_PASSWORD_INVALID);
        }
        if (oldPassword.equals(newPassword)) {
            throw new AnaliaException(ExceptionCode.INVALID_PASSWORD_VALUE, "Sorry, your new password must be different from old password, and at least 7 characters long with no spaces at the beginning or end.");
        }
        user.setLoginToken(newPassword);
        user.setTempPassword(false);
        userCoreLocal.save(user);
        return user;
    }

    /**
     *       // newPassword + "|" + userId + "|" + userTimestamp + "|" + validUntil TODO comeback to previous versin
     */

    /**
     *
     */

    public String resetPassword(String resetCode) throws AnaliaException {

        EncryptionUtil encryptor = new EncryptionUtil();
        // newPassword + "|" + userId + "|" + userTimestamp + "|" + validUntil
        String decrypted = encryptor.decrypt(resetCode).trim();
        String[] parts = decrypted.split("\\|");
        String newPassword = parts[0];
        BigInteger userId = BigInteger.valueOf(Integer.parseInt(parts[1].trim()));
        long userTimeStamp = Long.parseLong(parts[2]);
        long validUntil = Long.parseLong(parts[3]);
        long currentTime = System.currentTimeMillis();

        if (validUntil < currentTime) {
            throw new AnaliaException(ExceptionCode.EXPIRED_OPERATION, "reset code is invalid");
        }
        User user = this.userCoreLocal.getUserById(userId);
        if (user == null || user.isDisabled() || user.getUserTypeId().compareTo(BigInteger.valueOf(User.USER_TYPE_EMAIL_ID)) != 0 || user.getTimestamp().getTime() != userTimeStamp) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "User account with specified email not found, it is not of type EMAIL, or it is not Active");
        }

        user.setLoginToken(newPassword);
        user.setTempPassword(true);

        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        //TODO disable credit cards when users resetPassword
        this.userCoreLocal.save(user);
        String link = settingsCoreLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_PASSWORD_RESET_LINK);
        link = link.replace("${code}", newPassword);
        return link;
    }

    /**
     * @param newUserProfile
     * @throws AnaliaException
     */
    @Transactional
    public void saveUserProfile(List<UserProfile> newUserProfile) throws AnaliaException {
        int maxKeys = 100;
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        List<UserProfile> listAdditionalData = userCoreLocal.getUserProfile(user.getId());
        Map<String, UserProfile> mapAddionalData = new HashMap<String, UserProfile>();

        for (UserProfile userProfile : listAdditionalData) {
            mapAddionalData.put(userProfile.getKey(), userProfile);
        }

        int counter = listAdditionalData.size();
        for (UserProfile userProfile : newUserProfile) {
            if (userProfile.getValue() != null && userProfile.getValue().length() > MAX_VALUE_ADDITIONAL_DATA_SIZE) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Value not allowed");
            }
            if (mapAddionalData.containsKey(userProfile.getKey())) {
                UserProfile userProfileEntity = mapAddionalData.get(userProfile.getKey());

                if (userProfile.getValue() == null) {
                    userCoreLocal.deleteUserProfile(userProfileEntity.getId());
                } else {
                    userProfileEntity.setValue(userProfile.getValue());
                    userCoreLocal.saveUserProfile(userProfileEntity);
                }
            } else {
                if (userProfile.getValue() != null) {
                    userProfile.setType(BigInteger.valueOf(UserProfile.PROFILE_DATA_TYPE));
                    userProfile.setKey(userProfile.getKey());
                    userProfile.setUserId(user.getId());
                    userProfile.setValue(userProfile.getValue());
                    userProfile.setTimestamp(new Date());
                    userCoreLocal.saveUserProfile(userProfile);
                }
                counter++;
            }
            if (counter > maxKeys) {
                throw new AnaliaException(ExceptionCode.EXCEEDED_SET_PROFILE_KEY_LIMIT, "Counter keys is " + counter);
            }
        }
    }

    /**
     *
     */

    public void saveUserProfile(String firstName, String lastName, String title, String phoneNumberAgent, String agentEmail) throws AnaliaException {
        // TODO GENERATE CODE

    }

    /***
     *
     */

    public UserDirectory getUserDirectory() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        UserDirectory userDirectory = userCoreLocal.getUserDirectory(user.getId(), Constants.USER_PROFILE_PICTURE);
        if (userDirectory != null) {
            return userDirectory;
        }
        Directory directory = fileSystemCoreLocal.saveDirectory(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), user.getId(), null, vendorId);
        userDirectory = userCoreLocal.saveUserDirectory(directory.getId(), user.getId(), Constants.USER_PROFILE_PICTURE, user.getId(), false);
        return userDirectory;
    }

    /**
     * @param directoryId
     * @return
     * @throws AnaliaException
     */

    public UserDirectory getUserDirectory(BigInteger directoryId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        UserDirectory userDirectory = userCoreLocal.getUserDirectory(user.getId(), directoryId);
        if (userDirectory == null) {
            throw new AnaliaException(ExceptionCode.INVALID_OPERATION, "Not directory associated to the current user");
        }
        return userDirectory;
    }

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */

    public UserDirectory getUserDirectoryByUserId(BigInteger userId) throws AnaliaException {
        return userCoreLocal.getUserDirectory(userId, Constants.USER_PROFILE_PICTURE);
    }

    /**
     *
     */

    public ShoppingCart manageCart(BigInteger voucherId, BigInteger amount) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userCoreLocal.manageCart(voucherId, amount, user.getId());
    }

    public List<User> getUserByIds(BigInteger[] ids) throws AnaliaException {
        return userCoreLocal.getUserByIds(ids);
    }

    public List<UserProfile> getUserProfileForPossibleMatchingUsers(List<BigInteger> userIds) throws AnaliaException {
        return userCoreLocal.getUserProfileForPossibleMatchingUsers(userIds);
    }

    @Transactional
    public UserRole getUserRole() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        return userCoreLocal.getUserRole(vendorId, user.getId());
    }
}
