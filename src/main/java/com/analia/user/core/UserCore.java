package com.analia.user.core;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.BaseException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.Trending;
import com.analia.common.model.*;
import com.analia.common.model.resultset.ShoppingCartResultSet;
import com.analia.common.util.DataTypeUtil.DataType;
import com.analia.common.util.EmailUtil;
import com.analia.common.util.PseudoUniqueCodeUtils;
import com.analia.common.util.PseudoUniqueCodeUtils.PseudoUniqueCode;
import com.analia.user.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class UserCore {

    public static final String STRIPE_CLIENT_ID_PROFILE_KEY = "user.profile.stripe.key";

    @Inject
    private UserFacade userFacadeLocal;


    @Inject
    private TagFacade tagFacade;

    @Inject
    private TagFacade tagFacadeLocal;

    @Inject
    private UserTagFacade userTagFacade;

    @Inject
    private UserDeviceFacade userDeviceFacadeLocal;

    @Inject
    private DeviceFacade deviceFacadeLocal;

    @Inject
    private UserProfileFacade userProfileFacadeLocal;

    @Inject
    private UserTrendingFacade userTrendingLocal;

    @Inject
    private UserLocationFacade userLocationFacade;

    @Inject
    private UserDirectoryFacade userDirectoryFacadeLocal;

    @Inject
    private ShoppingCartFacade shoppingCartFacadeLocal;

    @Inject
    private UserRoleFacade userRoleFacadeLocal;

    @Inject
    private PersonaFacade personaFacadeLocal;

    @Inject
    private UserTokenFacade userTokenFacade;


    /**
     * @param token
     * @return
     * @throws AnaliaException
     */
    public User getUserByToken(String token) throws AnaliaException {
        User user = userFacadeLocal.getUserByToken(token);
        if (user == null) {
            return null;
        }
        Persona persona = personaFacadeLocal.find(user.getPersonaId());
        user.setPersona(persona);
        return user;
    }

    public User getUserByExternalToken(String token) throws AnaliaException {
        return userFacadeLocal.getUserByExternalToken(token);
    }

    /**
     * @param identifier
     * @param userTypeId
     * @return
     * @throws AnaliaException
     */
    public User getUserByIdentifier(String identifier, BigInteger vendorId, int userTypeId) throws AnaliaException {
        User user = userFacadeLocal.getUserByIdentifier(identifier, vendorId, userTypeId);
        if (user == null) {
            return null;
        }
        UserRole userRole = getUserRole(vendorId, user.getId());
        if (userRole.isDisabled()) {
            return null;
        }
        Persona persona = personaFacadeLocal.find(user.getPersonaId());
        user.setPersona(persona);
        return user;
    }

    /**
     * @param user
     * @return
     * @throws AnaliaException
     */
    @Transactional
    public User save(User user) throws AnaliaException {
        personaFacadeLocal.save(user.getPersona());
        personaFacadeLocal.flush();

        userFacadeLocal.save(user);
        userFacadeLocal.flush();
        return user;
    }
    @Transactional
    public User saveUser(User user) throws AnaliaException {
        userFacadeLocal.save(user);
        userFacadeLocal.flush();
        return user;
    }

    public Persona save(Persona persona) throws AnaliaException {
        personaFacadeLocal.save(persona);
        personaFacadeLocal.flush();
        return persona;
    }
    /**
     *
     */
    @Transactional
    public UserDevice setDeviceToUser(BigInteger deviceId, BigInteger userId) throws AnaliaException {
        UserDevice userDevice = null;
        if ((userDevice = userDeviceFacadeLocal.getUserDevice(deviceId, userId)) == null) {
            userDevice = new UserDevice();
        }
        userDevice.setDeviceId(deviceId);
        userDevice.setUserId(userId);
        userDevice.setTimestamp(new Timestamp(System.currentTimeMillis()));
        userDeviceFacadeLocal.save(userDevice);
        return userDevice;
    }

    /**
     * @param uuid
     * @return
     * @throws AnaliaException
     */
    public Device getDeviceByUUID(String uuid, BigInteger vendorId) throws AnaliaException {
        Device device = deviceFacadeLocal.getDeviceByUuid(uuid, vendorId);
        return device;
    }

    /**
     * @param device
     * @return
     * @throws AnaliaException
     */
    @Transactional
    public Device saveDevice(Device device) throws AnaliaException {
        deviceFacadeLocal.save(device);
        deviceFacadeLocal.flush();
        return device;
    }

    /**
     *
     */
    public UserProfile getValueOnUserProfileByKeyAndUserId(String key, BigInteger userId) throws AnaliaException {
        return userProfileFacadeLocal.getUserProfileByKeyAndUserId(key, userId);
    }

    /**
     *
     */

    public UserProfile saveUserProfile(String key, DataType type, Object value, BigInteger userId) throws AnaliaException {
        if (key == null) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, " Key must be not null");
        }
        if (value == null) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, " Value must be not null");
        }
        UserProfile profile = userProfileFacadeLocal.getUserProfileByKeyAndUserId(key, userId);
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(userId);
        }
        profile.setKey(key);
        profile.setUserId(userId);
        profile.setValue(value.toString());
        profile.setType(new BigInteger(Integer.toString(type.getDataType())));
        userProfileFacadeLocal.save(profile);
        userProfileFacadeLocal.flush();
        return profile;
    }

    public User getUserByUserEmail(String email) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        return userFacadeLocal.getUserByIdentifier(email, vendorId, User.USER_TYPE_EMAIL_ID);
    }

    /**
     *
     */
    public boolean isValidNewEmail(String email) throws AnaliaException {
        return (EmailUtil.validate(email) && getUserByUserEmail(email) == null);
    }

    /**
     *
     */
    public UserTrending saveUserTrending(BigInteger tradeId, BigInteger voucherId, BigInteger storyId, boolean favourited, BigInteger userId) throws AnaliaException {
        if (tradeId == null && voucherId == null && storyId == null) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "tradeId or voucherId or storyId are not must be null!");
        }
        UserTrending userTrending = null;
        if (tradeId != null) {
            userTrending = userTrendingLocal.getUserTrendingByUserAndTradeId(userId, tradeId);
        }
        if (voucherId != null) {
            userTrending = userTrendingLocal.getUserTrendingByUserAndVoucherId(userId, voucherId);
        }
        if (storyId != null) {
            userTrending = userTrendingLocal.getUserTrendingByUserAndStoryId(userId, storyId);
        }
        if (userTrending == null) {
            userTrending = new UserTrending();
            userTrending.setUserId(userId);
            userTrending.setFavourited(favourited);
            userTrending.setTradeId(tradeId);
            userTrending.setVoucherId(voucherId);
            userTrending.setCreatedDateTime(new Date());
        }
        userTrending.setFavourited(favourited);
        userTrendingLocal.save(userTrending);
        return userTrending;
    }

    public UserTrending getUserTrending(BigInteger tradeId, BigInteger voucherId, BigInteger userId) throws AnaliaException {
        UserTrending userTrending = null;
        if (tradeId != null) {
            userTrending = userTrendingLocal.getUserTrendingByUserAndTradeId(userId, tradeId);
        }
        if (voucherId != null) {
            userTrending = userTrendingLocal.getUserTrendingByUserAndVoucherId(userId, voucherId);
        }
        return userTrending;
    }

    public void applyUserTrending(BigInteger userId, Trending... list) throws AnaliaException {
        List<UserTrending> userTrendings = userTrendingLocal.getUserTrending(userId);
        for (UserTrending userTrending : userTrendings) {
            for (Trending trending : list) {
                if (userTrending.getTradeId() != null && userTrending.getTradeId().compareTo(trending.getTrendingId()) == 0 || userTrending.getVoucherId() != null && userTrending.getVoucherId().intValue() == trending.getTrendingId().intValue()) {
                    trending.setFavourited(userTrending.isFavourited());
                }
            }
        }

    }
    @Transactional
    public UserLocation setUserLocation(double latitude, double longitude, BigInteger zoneId, BigInteger userId) throws AnaliaException {
        try {
            userLocationFacade.resetUserLocations(userId);
            UserLocation userLocation = getUserLocation(zoneId, userId);

            if (zoneId.intValue() == -1) {
                userLocationFacade.resetUserLocations(userId);
                return new UserLocation();
            }
            if (userLocation == null) {
                userLocation = new UserLocation();
                userLocation.setUserId(userId);
            }
            userLocation.setZoneId(zoneId);
            userLocation.setLatitude(latitude);
            userLocation.setLongitude(longitude);
            userLocation.setDefaultLocation(true);
            userLocationFacade.save(userLocation);
            return userLocation;
        } catch (AnaliaException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    public UserLocation getUserLocation(BigInteger zoneId, BigInteger userId) throws AnaliaException {
        return userLocationFacade.getUserLocation(zoneId, userId);
    }

    public UserLocation getDefaultUserLocation(BigInteger userId) throws AnaliaException {
        return userLocationFacade.getDefaultUserLocation(userId);
    }

    public List<UserTrending> getUsersTrending(BigInteger userId) throws AnaliaException {
        return userTrendingLocal.getUserTrending(userId);
    }


    public User getUserByConfirmationCode(String emailCode) throws AnaliaException {
        return userFacadeLocal.getUserByConfirmationCode(emailCode);
    }

    public User getUserById(BigInteger userId) throws AnaliaException {
        return userFacadeLocal.find(userId);
    }

    public List<UserProfile> getUserProfile(BigInteger userId) throws AnaliaException {
        return userProfileFacadeLocal.getUserProfile(userId);
    }

    public List<UserProfile> getUserProfile(BigInteger userId, String prefix) throws AnaliaException {
        return userProfileFacadeLocal.getUserProfile(userId, prefix);
    }

    public List<User> getUserProfileMatchingTags(BigInteger userId, String gender) throws AnaliaException {
        return userFacadeLocal.getUserProfileMatchingTags(userId, gender);
    }

    public List<UserProfile> getUserProfileByTags(String[] tags) throws AnaliaException {
        return userProfileFacadeLocal.getUserProfileByTags(tags);
    }

    public void saveUserProfile(UserProfile userProfile) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        UserProfile oldProfile = userProfileFacadeLocal.getUserProfileByKeyAndUserId(userProfile.getKey(), user.getId());
        if (oldProfile != null) {
            oldProfile.setValue(userProfile.getValue());
            userProfileFacadeLocal.save(oldProfile);
        } else {
            userProfileFacadeLocal.save(userProfile);
        }
    }

    @Transactional
    public void deleteUserProfile(BigInteger userProfileId) throws AnaliaException {
        userProfileFacadeLocal.deleteUserProfile(userProfileId);
    }

    public UserWallet getWalletForUserId(BigInteger userId) throws AnaliaException {
        // TODO Auto-generated method stub
        return new UserWallet();
    }


    public User saveUserAndLogout(BigInteger userId, BigInteger userTypeId, BigInteger roleId, boolean unconfirmedEmail, String fullName, String emailAddress, String userIdentifier, String newPassword, String title, String firstName, String lastName, String phoneNumber, Date dateOfBirth, BigInteger createdBy, boolean disabled) throws AnaliaException {
        User user = null;
        Persona persona = null;
        try {
            if ((user = userFacadeLocal.find(userId)) == null) {
                user = new User();
                persona = new Persona();
                persona.setTitle(title);
                persona.setName(fullName);
                persona.setFirstName(firstName);
                persona.setLastName(lastName);
                persona.setEmail(emailAddress);
                persona.setDateOfBirth(dateOfBirth);
                persona.setPhoneNumber(phoneNumber);
                user.setId(userId);
                user.setCreatedDatetime(new Date());
                user.setUserTypeId(userTypeId);
                persona.setDateOfBirth(dateOfBirth);
                user.setCreatedBy((createdBy == null) ? BigInteger.ONE: createdBy);
                user.setConfirmationCode(PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_LARGE));
                user.setPersona(persona);
            }
            user.setUserIdentifier(userIdentifier);
            user.setUnconfirmedEmail(unconfirmedEmail);

            user.setUserTypeId(userTypeId);
            user.setLoginToken(newPassword);
            user.generateSessionToken(newPassword);
            this.save(user);
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
        return user;

    }

    @Transactional
    public UserDirectory saveUserDirectory(BigInteger directoryId, BigInteger userId, String name, BigInteger ownerId, boolean disabled) throws AnaliaException {
        UserDirectory userDirectory = null;
        try {
            if ((userDirectory = userDirectoryFacadeLocal.find(userId)) == null) {
                userDirectory = new UserDirectory();
                userDirectory.setDirectoryId(directoryId);
                userDirectory.setUserId(userId);
                userDirectory.setCreatedDatetime(new Date());
            }
            userDirectory.setName(name);
            userDirectory.setDisabled(disabled);
            userDirectory.setTimestamp(new Timestamp(System.currentTimeMillis()));
            userDirectoryFacadeLocal.save(userDirectory);

        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
        return userDirectory;
    }

    public UserDirectory getUserDirectory(BigInteger userId, String name) throws AnaliaException {
        return userDirectoryFacadeLocal.getUserDirectory(userId, name);
    }


    public UserDirectory getUserDirectory(BigInteger userId, BigInteger directoryId) throws AnaliaException {
        return userDirectoryFacadeLocal.getUserDirectory(userId, directoryId);
    }

    /**
     *
     */

    @Transactional
    public ShoppingCart manageCart(BigInteger voucherId, BigInteger amount, BigInteger userId) throws AnaliaException {
        if (amount.intValue() == -2) {
            shoppingCartFacadeLocal.deleteAllShoppingCart(userId);
            return null;
        }
        ShoppingCart shoppingCart = shoppingCartFacadeLocal.getShoppingCart(voucherId, userId);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setVoucherId(voucherId);
            shoppingCart.setQuantity(BigInteger.ZERO);
        }
        shoppingCart.setQuantity((new BigInteger(shoppingCart.getQuantity().add(amount).toString())));
        shoppingCartFacadeLocal.save(shoppingCart);
        return shoppingCart;
    }

    /**
     *
     */

    public List<ShoppingCartResultSet> getShoppingCarts(BigInteger userId) throws AnaliaException {
        return shoppingCartFacadeLocal.getShoppingCarts(userId);
    }


    public int[] getRemainingAllowanceVoucherByUser(BigInteger[] vourchersId, BigInteger userId) throws AnaliaException {
        // TODO Auto-generated method stub
        return null;
    }


    public List<User> getUserByIds(BigInteger[] ids) throws AnaliaException {
        return userFacadeLocal.getUserByIds(ids);
    }


    public List<Tag> getUserTags(BigInteger userId) throws AnaliaException {
        return tagFacadeLocal.getUserTags(userId);
    }


    public void setUserTags(BigInteger userId, List<String> tags) throws AnaliaException {
        for (String stringTag : tags) {
            Tag tag = tagFacade.getTagByName(stringTag);
            if (tag == null) {
                tag = new Tag();
                tag.setName(stringTag);
                tag.setDescription(stringTag);
                tag.setDisabled(false);
                tagFacade.save(tag);
                tagFacade.flush();
            }
            UserTag userTag = userTagFacade.getUserTag(userId, tag.getId());
            if (userTag == null) {
                userTag = new UserTag();
            }
            userTag.setUserId(userId);
            userTag.setTagId(tag.getId());
            userTagFacade.save(userTag);
        }
    }

    public List<UserProfile> getUserProfileForPossibleMatchingUsers(List<BigInteger> userIds) throws AnaliaException {
        return userProfileFacadeLocal.getUserProfileForPossibleMatchingUsers(userIds);
    }

    public UserRole getUserRole(BigInteger vendorId, BigInteger userId) throws AnaliaException {
        UserRole userRole = null;
        if ((userRole = userRoleFacadeLocal.getUserRole(vendorId, userId)) == null) {
            userRole = new UserRole();
            userRole.setVendorId(vendorId);
            userRole.setUserId(userId);
            userRole.setRoleId(UserRole.ROLE_GUEST);
            userRole.setDisabled(false);
            userRoleFacadeLocal.save(userRole);
            userRoleFacadeLocal.flush();
        }
        return userRole;
    }

    public UserRole saveUserRole(UserRole userRole) throws AnaliaException {
        userRoleFacadeLocal.save(userRole);
        userRoleFacadeLocal.flush();
        return userRole;
    }

    public UserToken saveUserToken(UserToken userToken) throws AnaliaException {
        userTokenFacade.save(userToken);
        userTokenFacade.flush();
        return userToken;
    }

    public UserToken getUserToken(String userToken) throws AnaliaException {
        return userTokenFacade.getUserByToken(userToken);
    }
}
