package com.analia.notification.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationServiceLocal {
    List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException;

    Notification saveNotification(Notification notification) throws AnaliaException;
}
