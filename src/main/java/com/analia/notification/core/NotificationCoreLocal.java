package com.analia.notification.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Inbox;
import com.analia.common.model.Notification;
import com.analia.common.model.NotificationType;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.common.model.resultset.view.NotificationView;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface NotificationCoreLocal {

    List<NotificationView> getNotificationsToBeDelivered(BigInteger userId, Date date) throws AnaliaException;

    int unreadNotificationCount(BigInteger userId) throws AnaliaException;

    List<NotificationResultSet> getUserNotificationsByyUserGropingId(BigInteger userGroupingId, int page, int size) throws AnaliaException;

    List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException;

    List<NotificationType> getNotificationTypes() throws AnaliaException;

    Notification saveNotification(BigInteger notificationId, BigInteger approvedBy, Date approvedDatetime, Date deliversOn, Date expiresOn, String message, BigInteger notificationtypeId, BigInteger groupingId, BigInteger voucherId, BigInteger promoId, BigInteger tradecategoryId, BigInteger userId) throws AnaliaException;

    Notification saveNotification(Notification notification) throws AnaliaException;

    Inbox saveInbox(BigInteger inboxId, Date deletedDatetime, BigInteger notificationId, Date readDatetime, BigInteger userGroupingId) throws AnaliaException;

    Inbox getInbox(BigInteger inboxId, BigInteger userId) throws AnaliaException;

    Inbox saveInbox(Inbox inbox) throws AnaliaException;
}
