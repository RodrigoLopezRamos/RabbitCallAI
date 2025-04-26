package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.NotificationView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
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
 * Session Bean implementation class VwNotificationListFacade
 */
@ApplicationScoped
public class NotificationViewFacade extends JPAPersistenceFacade<NotificationView> implements NotificationViewFacadeLocal {
    /**
     *
     */

    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_DATE = "date";
    public static final String QUERY_GET_PENDING_NOTIFICATIONS = "getPendingNotificationsForUser";
    public static final String QUERY_GET_COUNT_UNREAD_NOTIFICATIONS = "unReadNotificationCount";
    public static final String QUERY_GET_USER_NOTIFICATIONS = "getUserNotifications";

    @Inject
    private EntityManager entityManager;

    /**
     *
     */
    public NotificationViewFacade() {
        super(NotificationView.class);
    }

    /**
     *
     */



    /**
     *
     */

    public int unreadNotificationCount(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_COUNT_UNREAD_NOTIFICATIONS);
        query.setParameter(PARAM_USER_ID, userId);
        return (int) (long) query.getSingleResult();
    }

    /**
     *
     */

    public List<NotificationView> getNotificationsToBeDelivered(BigInteger userId, Date date) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_PENDING_NOTIFICATIONS, new JpqlParameter(PARAM_USER_ID, userId), new JpqlParameter(PARAM_DATE, date));
    }


}
