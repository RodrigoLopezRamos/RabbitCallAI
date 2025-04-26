package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.NotificationType;
import com.analia.common.persistence.JPAPersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Session Bean implementation class NotificationTypeFacacade
 */
@ApplicationScoped
public class NotificationTypeFacade extends JPAPersistenceFacade<NotificationType> implements NotificationTypeFacacadeLocal {

    public static final String QUERY_GET_ALL_NOTIFICATION_TYPE = "getNotificationTypes";
    @Inject
    private EntityManager entityManager;

    public NotificationTypeFacade() {
        super(NotificationType.class);
    }





    public List<NotificationType> getNotificationTypes() throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_ALL_NOTIFICATION_TYPE);
    }

}
