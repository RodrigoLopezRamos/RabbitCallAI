package com.analia.notification.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Notification;
import com.analia.notification.core.NotificationCoreLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.Date;
import java.util.List;

@ApplicationScoped
public class NotificationService implements NotificationServiceLocal {
    @Inject
    private NotificationCoreLocal notificationCoreLocal;

    /**
     *
     */

    public List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException {
        return notificationCoreLocal.getNotificationsToBePushed(date);
    }

    /**
     *
     */

    public Notification saveNotification(Notification notification) throws AnaliaException {
        return notificationCoreLocal.saveNotification(notification);
    }
}
