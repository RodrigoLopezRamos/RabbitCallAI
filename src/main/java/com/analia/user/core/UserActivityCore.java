package com.analia.user.core;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.BaseException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.location.GeoLocation;
import com.analia.common.model.*;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.user.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class UserActivityCore {
    /**
     *
     */
    public static final int CHECKIN_INTERVAL_QUERY = 180; //

    @Inject
    private UserCheckinFacade userCheckinFacadeLocal;

    @Inject
    private UserWalletFacade walletFacadeLocal;

    @Inject
    private UserTrendingFacade userTrendingFacadeLocal;

    @Inject
    private HiddenNotificationFacade hiddenNotificationFacadeLocal;

    @Inject
    private GroupingFacade groupingFacadeLocal;

    @Inject
    private UserGroupingFacade userGroupingFacadeLocal;

    @Inject
    private SettingsCore settingCoreLocal;

    /**
     *
     */

    public UserWallet saveWallet(BigInteger walletId, BigInteger credits, BigInteger userId) throws AnaliaException {
        UserWallet wallet = null;
        try {
            if ((wallet = walletFacadeLocal.find(walletId)) == null) {
                wallet = new UserWallet();
                wallet.setUserId(userId);
            }
            wallet.setCredits(credits);
            walletFacadeLocal.save(wallet);
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
        return wallet;
    }

    /**
     *
     */

    public UserWallet getWalletForUserId(BigInteger userId) throws AnaliaException {
        UserWallet wallet = walletFacadeLocal.getUserWalletForUserId(userId);
        if (wallet == null) {
            wallet = new UserWallet();
            wallet.setUserId(userId);
            walletFacadeLocal.save(wallet);
        }
        return wallet;
    }

    /**
     *
     */

    public UserCheckin userCheckIn(BigInteger tradeLocationId, BigInteger voucherVendorLocationId, double latitude, double longitude) throws AnaliaException {
        UserLocation userLocation = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Date currentTime = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_DATE_TIME, Date.class);
        if (!userLocation.isGpsIsEnable()) {
            throw new AnaliaException(ExceptionCode.GPS_IS_NOT_ENABLED, "The GPS must be enabled");
        }
        UserCheckin userCheckin = userCheckinFacadeLocal.getUserCheckinAtLocation(user.getId(), tradeLocationId, voucherVendorLocationId);
        if (userCheckin != null) {
            int minutes = (int) (((currentTime.getTime() / (60000)) - (userCheckin.getCreatedDatetime().getTime() / 60000)));
            int checkinInterval = settingCoreLocal.getIntValueForSettingKey(SettingsCore.SYSTEM_CHECKIN_INTERVAL);
            if (minutes < checkinInterval) {
                userCheckinFacadeLocal.save(userCheckin);
                return userCheckin;
            }
        }
        double distance = GeoLocation.computeDistanceBetweenTwoPoints(userLocation.getLatitude(), userLocation.getLongitude(), latitude, longitude);
        double distanceAllowed = settingCoreLocal.getDoubleValueForSettingKey(SettingsCore.SYSTEM_CHECKIN_DISTANCE) / 1000d;
        if (distance > distanceAllowed) {
            throw new AnaliaException(ExceptionCode.USER_IS_OUT_OF_RANGE, "The user location is out of range");
        }
        try {
            userCheckin = new UserCheckin();
            userCheckin.setCreatedDatetime(new Date());
            userCheckin.setUserId(user.getId());
            userCheckin.setLatitude(latitude);
            userCheckin.setLongitude(longitude);
            userCheckin.setTradelocationId(tradeLocationId);
            userCheckin.setVoucherVendorLocationId(voucherVendorLocationId);
            userCheckinFacadeLocal.save(userCheckin);
            return userCheckin;
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    /**
     *
     */

    public List<HiddenNotification> getHiddenNotifications(BigInteger userId) throws AnaliaException {
        return hiddenNotificationFacadeLocal.getHiddenNotificationByUserId(userId);
    }

    /**
     *
     */

    public HiddenNotification saveHiddenNotification(BigInteger hiddenNotificationId, BigInteger notificationTypeId, BigInteger userId) throws AnaliaException {
        HiddenNotification hiddenNotification = null;
        try {
            if ((hiddenNotification = hiddenNotificationFacadeLocal.find(hiddenNotificationId)) == null) {
                hiddenNotification = new HiddenNotification();
                hiddenNotification.setUserId(userId);
                hiddenNotification.setCreatedDatetime(new Date());
            }
            hiddenNotification.setId(hiddenNotificationId);
            hiddenNotification.setNotificationtypeId(notificationTypeId);
            hiddenNotificationFacadeLocal.save(hiddenNotification);
            return hiddenNotification;
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    /**
     *
     */

    public Grouping saveGrouping(BigInteger groupingId, String name, String description, boolean disabled, BigInteger userId, BigInteger vendorId) throws AnaliaException {
        Grouping grouping = null;
        try {
            if ((grouping = groupingFacadeLocal.find(groupingId)) == null) {
                grouping = new Grouping();
                grouping.setCreatedBy(userId);
                grouping.setVendorId(vendorId);
            }
            grouping.setName(name);
            grouping.setDescription(description);
            groupingFacadeLocal.save(grouping);
            groupingFacadeLocal.flush();
            return grouping;
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }


    /**
     *
     */

    public UserGrouping saveUserGrouping(BigInteger userGroupingId, BigInteger groupingId, BigInteger userId, boolean disabled) throws AnaliaException {
        UserGrouping userGrouping = null;
        try {
            if ((userGrouping = userGroupingFacadeLocal.find(userGroupingId)) == null) {
                userGrouping = new UserGrouping();
            }
            userGrouping.setGroupingId(groupingId);
            userGrouping.setUserId(userId);
            userGrouping.setDisabled(disabled);
            userGroupingFacadeLocal.save(userGrouping);
            userGroupingFacadeLocal.flush();
            return userGrouping;
        } catch (BaseException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    /**
     *
     */

    public void deleteHiddenNotification(HiddenNotification hiddenNotification) throws AnaliaException {
        hiddenNotificationFacadeLocal.remove(hiddenNotification);
    }

    /**
     *
     */

    public HiddenNotification getHiddenNotification(BigInteger notificationTypeId, BigInteger userId) throws AnaliaException {
        return hiddenNotificationFacadeLocal.getHiddenNotification(notificationTypeId, userId);
    }


    public UserGrouping getUserGropingByGroupIdAndUserId(BigInteger groupingId, BigInteger userId) throws AnaliaException {
        UserGrouping userGrouping = userGroupingFacadeLocal.getUserGropingByGroupIdAndUserId(groupingId, userId);
        if (userGrouping == null) {
            userGrouping = new UserGrouping();
            userGrouping.setGroupingId(groupingId);
            userGrouping.setUserId(userId);
        }
        userGroupingFacadeLocal.save(userGrouping);
        return userGrouping;
    }


    public UserGrouping getUserGropingByGroupIdAndUserIdNull(BigInteger groupingId, BigInteger userId) throws AnaliaException {
        return userGroupingFacadeLocal.getUserGropingByGroupIdAndUserId(groupingId, userId);
    }

    /**
     *
     */

    public Grouping getGroupingById(BigInteger groupingId) throws AnaliaException {
        return groupingFacadeLocal.find(groupingId);
    }


    public Grouping getGroupingByNameWithUserId(String name, BigInteger userId) throws AnaliaException {
        return groupingFacadeLocal.getGroupingByNameWithUserId(name, userId);
    }

    /**
     *
     */

    public Grouping getGroupingByName(String name) throws AnaliaException {
        if (name == null) {
            throw new AnaliaException(ExceptionCode.INVALID_INPUT_VALUES, "name is invalid!");
        }
        return groupingFacadeLocal.getGroupingByName(name);
    }

    /***
     *
     */

    public void removeUserFromGrouping(BigInteger groupingId, BigInteger userId) throws AnaliaException {
        userGroupingFacadeLocal.removeUserFromGrouping(groupingId, userId);
    }


    public UserCheckin getUserCheckinById(BigInteger checkinId) throws AnaliaException {
        UserCheckin userCheckin = userCheckinFacadeLocal.find(checkinId);
        if (userCheckin == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND);
        }
        return userCheckin;
    }

    /**
     *
     */

    public UserCheckin getUserCheckinAtLocation(BigInteger userId, BigInteger tradeLocationId, BigInteger voucherVendorLocationId) throws AnaliaException {
        return userCheckinFacadeLocal.getUserCheckinAtLocation(userId, tradeLocationId, voucherVendorLocationId);
    }

    /**
     *
     */

    public List<User> getUserPendingsForApprovalInGroup(BigInteger userId, String groupName) throws AnaliaException {
        return userGroupingFacadeLocal.getUserPendingsForApprovalInGroup(userId, groupName);
    }


    public void saveUserGrouping(UserGrouping userGrouping) throws AnaliaException {
        userGroupingFacadeLocal.save(userGrouping);
        userCheckinFacadeLocal.flush();
    }
}
