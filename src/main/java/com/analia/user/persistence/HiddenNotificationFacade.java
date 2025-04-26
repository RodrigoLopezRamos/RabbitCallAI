package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.HiddenNotification;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.math.BigInteger;
import java.util.List;

/**
 * Session Bean implementation class HiddenNotificationFacade
 */
@ApplicationScoped
public class HiddenNotificationFacade extends JPAPersistenceFacade<HiddenNotification>  {
    public static final String QUERY_GET_USER_HIDDEN_NOTIFICATIONS = "getUserHiddenNotifications";
    public static final String QUERY_GET_USER_HIDDEN_NOTIFICATION = "getUserHiddenNotification";
    public static final String QUERY_DELETE_USER_HIDDEN_NOTIFICATIONS = "deleteUserHiddenNotifications";

    public static final String PARAM_NOTIFICATION_TYPE_ID = "notificationTypeId";
    public static final String PARAM_USER_ID = "userId";

    @Inject
    private EntityManager entityManager;


    public HiddenNotificationFacade() {
        super(HiddenNotification.class);
    }

    /**
     *
     */



    /**
     *
     */

    public List<HiddenNotification> getHiddenNotificationByUserId(BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_HIDDEN_NOTIFICATIONS, new JpqlParameter(PARAM_USER_ID, userId));
    }

    /**
     *
     */

    public void deleteAllHiddenNotification(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_DELETE_USER_HIDDEN_NOTIFICATIONS);
        query.setParameter(PARAM_USER_ID, userId);
        query.executeUpdate();
    }

    /**
     *
     */

    public HiddenNotification getHiddenNotification(BigInteger notificationTypeId, BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_USER_HIDDEN_NOTIFICATION);
        query.setParameter(PARAM_USER_ID, userId);
        query.setParameter(PARAM_NOTIFICATION_TYPE_ID, notificationTypeId);
        List<?> list = query.getResultList(); //
        return (HiddenNotification) (list.isEmpty() ? null : list.get(0));
    }
}
