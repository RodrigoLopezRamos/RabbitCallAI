package com.analia.notification.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Inbox;
import com.analia.common.model.Notification;
import com.analia.common.model.NotificationType;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.common.model.resultset.view.NotificationView;
import com.analia.notification.persistence.InboxFacadeLocal;
import com.analia.notification.persistence.NotificationFacadeLocal;
import com.analia.notification.persistence.NotificationTypeFacacadeLocal;
import com.analia.notification.persistence.NotificationViewFacadeLocal;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * Session Bean implementation class NotificationLogic
 */
@ApplicationScoped
public class NotificationCore implements NotificationCoreLocal {
    @Inject
    private NotificationViewFacadeLocal notificationViewFacadeLocal;

    @Inject
    private NotificationTypeFacacadeLocal notificationTypeFacacadeLocal;

    @Inject
    private NotificationFacadeLocal notificationFacadeLocal;

    @Inject
    private InboxFacadeLocal inboxFacadeLocal;

    /**
     *
     */

    public int unreadNotificationCount(BigInteger userId) throws AnaliaException {
        return notificationViewFacadeLocal.unreadNotificationCount(userId);
    }

    /**
     *
     */

    public List<NotificationType> getNotificationTypes() throws AnaliaException {
        return notificationTypeFacacadeLocal.getNotificationTypes();
    }

    /**
     *
     */

    public Notification saveNotification(BigInteger notificationId, BigInteger approvedBy, Date approvedDatetime, Date deliversOn, Date expiresOn, String message, BigInteger notificationtypeId, BigInteger groupingId, BigInteger voucherId, BigInteger promoId, BigInteger tradecategoryId, BigInteger userId) throws AnaliaException {
        Notification notification = null;
        try {
            if ((notification = notificationFacadeLocal.find(notificationId)) == null) {
                notification = new Notification();
                notification.setCreatedBy(userId);
                notification.setCreatedDatetime(new Date());
            }
            notification.setApprovedBy(approvedBy);
            notification.setApprovedDatetime(approvedDatetime);
            notification.setDeliversOn(deliversOn);
            notification.setExpiresOn(expiresOn);
            notification.setGroupingId(groupingId);
            notification.setId(notificationId);
            notification.setMessage(message);
            notification.setNotificationtypeId(notificationtypeId);
            notification.setVoucherId(voucherId);
            notification.setPromoId(promoId);
            notification.setTradecategoryId(tradecategoryId);
            notificationFacadeLocal.save(notification);
            return notification;
        } catch (AnaliaException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    /**
     *
     */

    public Inbox saveInbox(BigInteger inboxId, Date deletedDatetime, BigInteger notificationId, Date readDatetime, BigInteger usergroupingId) throws AnaliaException {
        Inbox inbox = null;
        try {
            if ((inbox = inboxFacadeLocal.find(inboxId)) == null) {
                inbox = new Inbox();
                inbox.setUsergroupingId(usergroupingId);
            }
            inbox.setDeletedDatetime(deletedDatetime);
            inbox.setId(inboxId);
            inbox.setNotificationId(notificationId);
            inbox.setReadDatetime(readDatetime);
            inboxFacadeLocal.save(inbox);
            inboxFacadeLocal.flush();
            return inbox;
        } catch (AnaliaException e) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, e.getMessage(), e);
        }
    }

    /**
     *
     */

    public Inbox getInbox(BigInteger inboxId, BigInteger userId) throws AnaliaException {
        return inboxFacadeLocal.getInboxForUser(userId, inboxId);
    }

    /**
     *
     */

    public Inbox saveInbox(Inbox inbox) throws AnaliaException {
        inboxFacadeLocal.save(inbox);
        return inbox;
    }

    /**
     *
     */

    public List<NotificationView> getNotificationsToBeDelivered(BigInteger userId, Date date) throws AnaliaException {
        return notificationViewFacadeLocal.getNotificationsToBeDelivered(userId, date);
    }

    /**
     * @throws AnaliaException
     */

    public List<NotificationResultSet> getUserNotificationsByyUserGropingId(BigInteger userGroupingId, int page, int size) throws AnaliaException {
        return notificationFacadeLocal.getNotificationList(userGroupingId, page, size);
    }

    /**
     *
     */

    public Notification saveNotification(Notification notification) throws AnaliaException {
        notificationFacadeLocal.save(notification);
        return notification;
    }

    /**
     *
     */

    public List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException {
        return notificationFacadeLocal.getNotificationsToBePushed(date);
    }
}
