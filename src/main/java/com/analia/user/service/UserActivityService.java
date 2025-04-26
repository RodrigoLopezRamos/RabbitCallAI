package com.analia.user.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.notification.core.NotificationCoreLocal;
import com.analia.user.core.UserActivityCore;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class
UserActivityService {
    @Inject
    private UserActivityCore userActivityCoreLocal;

    @Inject
    private NotificationCoreLocal notificationCoreLocal;


    public Sharing processSharing(BigInteger shareId) throws AnaliaException {
        // TODO Auto-generated method stub

        return null;
    }


    public void chat(BigInteger userId, String phrase) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Grouping grouping = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_FRIENDS, user.getId());
        if (grouping == null) {
            return;
        }
        UserGrouping userGrouping = userActivityCoreLocal.getUserGropingByGroupIdAndUserId(grouping.getId(), userId);

        if (userGrouping == null) {
            return;
        }

        Notification notification = new Notification();
        notification.setNotificationtypeId(new BigInteger("1"));
        notification.setCreatedBy(new BigInteger("1"));
        notification.setCreatedDatetime(new Date());
        notification.setMessage(phrase);
        notification.setApprovedBy(new BigInteger("1"));
        notification.setApprovedDatetime(new Date());
        notification.setDeliversOn(new Date());
        notification.setGroupingId(grouping.getId());

        notificationCoreLocal.saveNotification(notification);
        Inbox inbox = new Inbox();
        inbox.setUsergroupingId(userGrouping.getId());
        inbox.setNotificationId(notification.getId());
        notificationCoreLocal.saveInbox(inbox);

    }


    public List<NotificationResultSet> getChatNotificationsByUserId(BigInteger userId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Grouping grouping = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_FRIENDS, user.getId());
        UserGrouping userGrouping = userActivityCoreLocal.getUserGropingByGroupIdAndUserIdNull(grouping.getId(), userId);

        if (userGrouping == null) {
            return new ArrayList<>();
        }
        List<NotificationResultSet> myMesssages = notificationCoreLocal.getUserNotificationsByyUserGropingId(userGrouping.getId(), 0, 10000);
        for (NotificationResultSet myMesssage : myMesssages) {
            myMesssage.mime = true;
        }

        Grouping groupingOtherUser = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_FRIENDS, userId);
        UserGrouping userGroupingOtherUser = userActivityCoreLocal.getUserGropingByGroupIdAndUserIdNull(groupingOtherUser.getId(), user.getId());
        List<NotificationResultSet> myMesssagesFromOtherUser = notificationCoreLocal.getUserNotificationsByyUserGropingId(userGroupingOtherUser.getId(), 0, 10000);
        myMesssagesFromOtherUser.addAll(myMesssages);
        Collections.sort(myMesssagesFromOtherUser, (o1, o2) -> o1.createdDateTime.compareTo(o2.createdDateTime));
        return myMesssagesFromOtherUser;
    }

}
